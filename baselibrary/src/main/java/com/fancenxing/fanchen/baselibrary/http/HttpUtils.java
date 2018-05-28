package com.fancenxing.fanchen.baselibrary.http;

import android.content.Context;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述：
 * Created by 孙中宛 on 2018/5/8.
 */

public class HttpUtils {

    //直接带参数 链式调用
    private String mUrl;

    private int mType = GET_TYPE;

    private static final int POST_TYPE = 0x0011;

    private static final int GET_TYPE = 0x0011;

    private Context mContext;

    private Map<String, Object> mParams;

    private boolean mCache = false;

    private HttpUtils(Context context) {
        mContext = context;
        mParams = new HashMap<>();
    }

    public static HttpUtils with(Context context) {
        return new HttpUtils(context);
    }

    public HttpUtils url(String url) {
        mUrl = url;
        return this;
    }

    public HttpUtils post() {
        mType = POST_TYPE;
        return this;
    }

    public HttpUtils get() {
        mType = GET_TYPE;
        return this;
    }

    public HttpUtils cache(boolean cache) {
        this.mCache = cache;
        return this;
    }

    //添加参数
    public HttpUtils addParams(String key, Object value) {
        mParams.put(key, value);
        return this;
    }

    public HttpUtils addParams(Map<String, Object> params) {
        if (params != null) {
            mParams.putAll(params);
        }
        return this;
    }

    //添加回调 执行
    public void execute(EngineCallback callback) {
        if (callback == null) {
            callback = EngineCallback.DEFAULT_CALLBACK;
        }
        callback.onPreExecute(mContext, mParams);
        //判断执行方法
        if (mType == POST_TYPE) {
            post(mCache, mUrl, mParams, callback);
        }
        if (mType == GET_TYPE) {
            get(mCache, mUrl, mParams, callback);
        }
    }

    public void execute() {
        execute(null);
    }

    //默认OkHttpEngine
    private static IHttpEngine mHttpEngine;

    //初始化引擎
    public static void init(IHttpEngine httpEngine) {
        mHttpEngine = httpEngine;
    }

    /**
     * 更换引擎
     *
     * @param httpEngine
     */
    public void exchangeEngine(IHttpEngine httpEngine) {
        mHttpEngine = httpEngine;
    }

    private void get(boolean cache, String url, Map<String, Object> params, EngineCallback callback) {
        mHttpEngine.get(mCache, url, params, callback);
    }

    private void post(boolean cache, String url, Map<String, Object> params, EngineCallback callback) {
        mHttpEngine.post(cache, url, params, callback);
    }


    public static String joinParams(Map<String, Object> params) {
        return null;
    }

    public static Class<?> analysisClazzInfo(Object object) {
        Type genType = object.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        return (Class<?>) params[0];

    }
}
