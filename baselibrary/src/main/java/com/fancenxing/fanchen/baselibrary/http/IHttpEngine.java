package com.fancenxing.fanchen.baselibrary.http;

import java.util.Map;

/**
 * 功能描述：引擎的规范
 * Created by 孙中宛 on 2018/5/8.
 */

public interface IHttpEngine {

    //get请求
    void get(boolean cache,String url, Map<String,Object> params,EngineCallback callback);

    //post请求
    void post(boolean cache,String url, Map<String,Object> params,EngineCallback callback);

    //下载文件

    //上传文件

    //https添加证书
}
