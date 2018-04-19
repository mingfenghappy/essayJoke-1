package com.fancenxing.fanchen.essayjoke;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.alipay.euler.andfix.patch.PatchManager;
import com.fancenxing.fanchen.baselibrary.base.ExceptionCrashHandler;

/**
 * 功能描述：
 * Created by 孙中宛 on 2018/4/18.
 */

public class BaseApplication extends Application {

    public static PatchManager patchManager;

    @Override
    public void onCreate() {
        super.onCreate();

        //设置全局异常捕捉类
        ExceptionCrashHandler.getInstance()
                .init(this);

        //初始化阿里的热修复
        patchManager = new PatchManager(this);
        patchManager.init(getVersion());

        //加载之前的patch
        patchManager.loadPatch();
    }


    public String getVersion() {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
