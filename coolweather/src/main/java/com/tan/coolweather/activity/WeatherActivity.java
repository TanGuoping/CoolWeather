package com.tan.coolweather.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tan.coolweather.R;
import com.tan.coolweather.util.HttpCallbackListener;
import com.tan.coolweather.util.HttpUtil;
import com.tan.coolweather.util.Utility;

public class WeatherActivity extends AppCompatActivity {

    private TextView city;
    private LinearLayout weatherInfo;
    private TextView publishTime;
    private TextView currentDate;
    private TextView weatherDesp;
    private TextView temp;
    private ImageView home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);


        city = (TextView) findViewById(R.id.city);
        weatherInfo = (LinearLayout) findViewById(R.id.weatherInfoId);
        publishTime = (TextView) findViewById(R.id.publicTimeId);
        currentDate = (TextView) findViewById(R.id.currentDateId);
        weatherDesp = (TextView) findViewById(R.id.weatherDespId);
        temp = (TextView) findViewById(R.id.tempId);

        String countyCode = getIntent().getStringExtra("county_code");
        if(! TextUtils.isEmpty(countyCode)){
            publishTime.setText("同步中...");
            weatherInfo.setVisibility(View.INVISIBLE);
            city.setVisibility(View.INVISIBLE);
            queryWeatherCode(countyCode);
        }

        else{
            showWeather();
        }


        home = (ImageView) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WeatherActivity.this, ChooseAreaActivity.class);
                intent.putExtra("isFromHome",true);
                startActivity(intent);
                finish();
            }
        });





        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                publishTime.setText("同步中...");
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this);
                String weatherCode = sharedPreferences.getString("weather_code","");
                if(! TextUtils.isEmpty(weatherCode)){
                    queryWeatherInfo(weatherCode);
                }

            }
        });

    }


    private void queryWeatherCode(String countyCode){
        String address = "http://www.weather.com.cn/data/list3/city" + countyCode + ".xml";
        queryFromServer(address, "countyCode");
    }

    private void queryWeatherInfo(String weatherCode){
        String address = "http://www.weather.com.cn/data/cityinfo/" + weatherCode + ".html";
        queryFromServer(address, "weatherCode");
    }

    private void queryFromServer(final String address, final String type){
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                if ("countyCode".equals(type)) {
                    if (!TextUtils.isEmpty(response)) {
                        String[] array = response.split("\\|");
                        if (array != null && array.length == 2) {
                            String weatherCode = array[1];
                            queryWeatherInfo(weatherCode);
                        }
                    }
                } else if ("weatherCode".equals(type)) {
                    Utility.handleWeatherResponse(WeatherActivity.this, response);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showWeather();
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        publishTime.setText("同步失败");
                    }
                });

            }
        });
    }


    private  void showWeather(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        city.setText(sharedPreferences.getString("city_name",""));
        publishTime.setText("今天" + sharedPreferences.getString("publish_time", "") + "发布");
        currentDate.setText(sharedPreferences.getString("current_date", ""));
        weatherDesp.setText(sharedPreferences.getString("weather_desp", ""));
        temp.setText(sharedPreferences.getString("temp2","") + "-" + sharedPreferences.getString("temp1", ""));
        weatherInfo.setVisibility(View.VISIBLE);
        city.setVisibility(View.VISIBLE);
    }

}
