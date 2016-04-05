package com.tan.myapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.ContentHandler;
import java.net.URLConnection;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

public class MainActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequestWithHttpClient();

            }
        });
    }

    private void sendRequestWithHttpClient(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream in = null;
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet httpGet = new HttpGet("http://192.168.1.5:8080/get_data.json");

                    HttpResponse httpResponse = httpClient.execute(httpGet);

                    if(httpResponse.getStatusLine().getStatusCode() == 200){
                        HttpEntity entity = httpResponse.getEntity();
                        in = entity.getContent();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                        StringBuffer sb = new StringBuffer();
                        String line;
                        while((line = bufferedReader.readLine()) != null){
                            sb.append(line+"\n");
                        }
                        parseJson(sb.toString());



                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(in != null){
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }


            }
        }).start();
    }

    private void parseXML(String data){
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            XMLReader xmlReader = factory.newSAXParser().getXMLReader();
            MyHandler handler = new MyHandler();
            xmlReader.setContentHandler(handler);
            xmlReader.parse(new InputSource(new StringReader(data)));
            handler.getList();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseJson(String data){
        try {
            //JSONArray jsonArray = new JSONArray(data);
            //for(int i=0; i<jsonArray.length(); i++){
                //JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONObject jsonObject = new JSONObject(data);
                String value = jsonObject.getString("value");
                Log.d("value",value);
                JSONObject jsonObject1 = jsonObject.getJSONObject("app");
                String app = jsonObject1.getString("app1");
                String result = jsonObject1.getString("result");
                Log.d("app",app);
                Log.d("result",result);

            /*
                JSONArray jsonArray1 = jsonObject.getJSONArray("app");
                for(int j=0;j<jsonArray1.length();j++){

                    JSONObject jsonObject1 = jsonArray1.getJSONObject(j);
                    String id = jsonObject1.getString("id");
                    String name = jsonObject1.getString("name");
                    String version = jsonObject1.getString("version");
                    Log.d("id",id);
                    Log.d("name",name);
                    Log.d("version",version);

                }
                */


           // }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
