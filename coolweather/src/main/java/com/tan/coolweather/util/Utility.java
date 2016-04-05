package com.tan.coolweather.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.tan.coolweather.db.CoolWeatherDB;
import com.tan.coolweather.model.City;
import com.tan.coolweather.model.County;
import com.tan.coolweather.model.Province;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by think on 2016/4/3.
 */
public class Utility {

    public synchronized static boolean handleProvincesResponse(CoolWeatherDB coolWeatherDB, String response){
        if(! TextUtils.isEmpty(response)){
            String[] allProvinces = response.split(",");
            if(allProvinces != null & allProvinces.length > 0){
                for(String p : allProvinces){
                    String[] array = p.split("\\|");
                    Province province = new Province();
                    province.setProvinceName(array[1]);
                    province.setProvinceCode(array[0]);
                    coolWeatherDB.saveProvince(province);
                }

                return true;
            }

        }

        return false;
    }


    public synchronized static boolean handleCitiesResponse(CoolWeatherDB coolWeatherDB, String response, int provinceId){

        if(! TextUtils.isEmpty(response)){
            String[] allCities = response.split(",");
            if(allCities != null & allCities.length > 0){
                for(String c : allCities){
                    String[] array = c.split("\\|");
                    City city = new City();
                    city.setCityName(array[1]);
                    city.setCityCode(array[0]);
                    city.setProvinceId(provinceId);
                    coolWeatherDB.saveCity(city);
                }
            }
            return true;
        }

        return false;

    }


    public synchronized static boolean handleCountiesResponse(CoolWeatherDB coolWeatherDB, String response, int cityId){
        if(! TextUtils.isEmpty(response)){
            String[] allCounties = response.split(",");
            if(allCounties != null & allCounties.length > 0){
                for(String c : allCounties){
                    String[] array = c.split("\\|");
                    County county = new County();
                    county.setCountyName(array[1]);
                    county.setCountyCode(array[0]);
                    county.setCityId(cityId);
                    coolWeatherDB.saveCounty(county);
                }

                return true;
            }
        }

        return false;
    }

    public static void handleWeatherResponse(Context context, String response){

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject weatherInfo = jsonObject.getJSONObject("weatherinfo");

            String cityName = weatherInfo.getString("city");
            String temp1 = weatherInfo.getString("temp1");
            String temp2 = weatherInfo.getString("temp2");
            String weatherDesp = weatherInfo.getString("weather");
            String publishTime = weatherInfo.getString("ptime");
            String weatherCode = weatherInfo.getString("cityid");
            saveWeatherInfo(context, cityName, temp1, temp2, weatherDesp, publishTime, weatherCode);




        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    public static void saveWeatherInfo(Context context, String cityName, String temp1, String temp2, String weatherDesp, String publishTime, String weatherCode){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日", Locale.CHINA);

        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();

        editor.putBoolean("city_selected", true);
        editor.putString("city_name", cityName);
        editor.putString("temp1", temp1);
        editor.putString("temp2", temp2);
        editor.putString("weather_desp", weatherDesp);
        editor.putString("publish_time", publishTime);
        editor.putString("current_date", sdf.format(new Date()));
        editor.putString("weather_code",weatherCode);
        editor.commit();
    }










}
