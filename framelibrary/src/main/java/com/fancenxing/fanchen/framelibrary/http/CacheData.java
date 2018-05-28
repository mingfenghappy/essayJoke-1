package com.fancenxing.fanchen.framelibrary.http;

/**
 * 功能描述：缓存的实体类
 * Created by 孙中宛 on 2018/5/15.
 */

public class CacheData {

    private String mUrlKey;

    private String json;

    public CacheData() {
    }

    public String getmUrlKey() {
        return mUrlKey;
    }

    public void setmUrlKey(String mUrlKey) {
        this.mUrlKey = mUrlKey;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
