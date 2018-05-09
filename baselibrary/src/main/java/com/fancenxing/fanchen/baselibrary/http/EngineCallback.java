package com.fancenxing.fanchen.baselibrary.http;

import android.content.Context;

import java.util.Map;

/**
 * 功能描述：
 * Created by 孙中宛 on 2018/5/8.
 */

public interface EngineCallback {

    void onPreExecute(Context context, Map<String, Object> params);

    //错误
    void onError(Exception e);

    //成功
    void onSuccess(String result);

    //默认的
    EngineCallback DEFAULT_CALLBACK = new EngineCallback() {
        @Override
        public void onPreExecute(Context context, Map<String, Object> params) {

        }

        @Override
        public void onError(Exception e) {

        }

        @Override
        public void onSuccess(String result) {

        }
    };
}
