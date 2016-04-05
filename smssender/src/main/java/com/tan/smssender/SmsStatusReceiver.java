package com.tan.smssender;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by think on 2016/3/19.
 */
public class SmsStatusReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(getResultCode() == Activity.RESULT_OK){
            Toast.makeText(context,"发送短信成功",Toast.LENGTH_SHORT).show();
        }

        else{
            Toast.makeText(context,"发送短信失败",Toast.LENGTH_SHORT).show();
        }
    }
}
