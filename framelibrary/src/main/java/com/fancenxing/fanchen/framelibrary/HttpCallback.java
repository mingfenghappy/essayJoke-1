package com.fancenxing.fanchen.framelibrary;

import android.content.Context;

import com.fancenxing.fanchen.baselibrary.http.EngineCallback;
import com.fancenxing.fanchen.baselibrary.http.HttpUtils;
import com.google.gson.Gson;

import java.util.Map;

/**
 * 功能描述：
 * Created by 孙中宛 on 2018/5/9.
 */

public abstract class HttpCallback<T extends Object> implements EngineCallback {

    @Override
    public void onPreExecute(Context context, Map<String, Object> params) {

    }

    @Override
    public void onSuccess(String result) {
        Class clazz = HttpUtils.analysisClazzInfo(this);
        T bean = (T) new Gson().fromJson(result, clazz);
        onSuccess(bean);
    }

    public abstract void onSuccess(T result);
}
