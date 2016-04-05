package com.tan.handler;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private Button button;
    private TextView textView;
    private static final int UPDATE_TEXT = 1;

    /*
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPDATE_TEXT:
                    textView.setText("Nice to meet you !");
                    break;
            }
        }
    };
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        button = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView);


        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                /*
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message msg = new Message();
                        msg.what = UPDATE_TEXT;
                        handler.sendMessage(msg);
                    }
                }).start();
                */
                new MyTask().execute();
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    class MyTask extends AsyncTask<Void,Integer,Boolean>{

        @Override
        protected void onPreExecute() {
            textView.setText("Start");
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            textView.setText("Over");
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

            textView.setText(values[0].toString());
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            for(int i = 0 ; i < 101 ; i++){

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                publishProgress(i);

            }
            return true;
        }
    }


}
