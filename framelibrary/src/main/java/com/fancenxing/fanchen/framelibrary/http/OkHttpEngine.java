package com.fancenxing.fanchen.framelibrary.http;

import com.fancenxing.fanchen.baselibrary.http.EngineCallback;
import com.fancenxing.fanchen.baselibrary.http.IHttpEngine;
import com.fancenxing.fanchen.framelibrary.db.DaoSupportFactory;
import com.fancenxing.fanchen.framelibrary.db.IDaoSupport;

import java.util.Map;

/**
 * 功能描述：OkHttp默认引擎
 * Created by 孙中宛 on 2018/5/8.
 */

public class OkHttpEngine implements IHttpEngine {

    @Override
    public void get(boolean cache, String url, Map<String, Object> params, EngineCallback callback) {

        final String resultJson;
        if (cache) {
            IDaoSupport<CacheData> daoSupport = DaoSupportFactory.getFactory()
                    .getDao(CacheData.class);
//            daoSupport.queryAll();
            resultJson = "";
        } else {
            resultJson = null;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                if ("".equals(resultJson)) {

                }
            }
        }).start();
        callback.onSuccess("{data:'234'}");
    }

    @Override
    public void post(boolean cache, String url, Map<String, Object> params, EngineCallback callback) {

    }
}
