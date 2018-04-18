package com.fancenxing.fanchen.essayjoke;

import android.app.Application;

import com.fancenxing.fanchen.baselibrary.base.ExceptionCrashHandler;

/**
 * 功能描述：
 * Created by 孙中宛 on 2018/4/18.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //设置全局异常捕捉类
        ExceptionCrashHandler.getInstance()
                .init(this);
    }
}
