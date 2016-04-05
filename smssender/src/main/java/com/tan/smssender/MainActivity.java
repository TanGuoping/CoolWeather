package com.tan.smssender;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView to;
    private TextView content;
    private Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("发送短信");
        setSupportActionBar(toolbar);

        to = (TextView) findViewById(R.id.to);
        content = (TextView) findViewById(R.id.content);
        send = (Button) findViewById(R.id.send);
        send.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        String address = to.getText().toString();
        String message = content.getText().toString();

        if(!address.equals("") && !message.equals("")){
            SmsManager manager = SmsManager.getDefault();
            Intent intent = new Intent("send_sms");
            PendingIntent pi = PendingIntent.getBroadcast(this,0,intent,0);
            manager.sendTextMessage(address, null, message, pi, null);

            content.setText("");

            BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);

        }

        else {
            Toast.makeText(this,"收件人或内容为空",Toast.LENGTH_SHORT).show();
        }

    }
}
