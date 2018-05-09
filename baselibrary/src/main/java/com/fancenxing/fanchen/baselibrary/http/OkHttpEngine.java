package com.fancenxing.fanchen.baselibrary.http;

import java.util.Map;

/**
 * 功能描述：OkHttp默认引擎
 * Created by 孙中宛 on 2018/5/8.
 */

public class OkHttpEngine implements IHttpEngine {

    @Override
    public void get(String url, Map<String, Object> params, EngineCallback callback) {
        callback.onSuccess("{data:'234'}");
    }

    @Override
    public void post(String url, Map<String, Object> params, EngineCallback callback) {

    }
}
