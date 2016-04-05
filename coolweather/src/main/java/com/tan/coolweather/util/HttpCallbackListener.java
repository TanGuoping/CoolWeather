package com.tan.coolweather.util;

/**
 * Created by think on 2016/4/3.
 */
public interface HttpCallbackListener {
    void onFinish(String response);

    void onError(Exception e);
}
