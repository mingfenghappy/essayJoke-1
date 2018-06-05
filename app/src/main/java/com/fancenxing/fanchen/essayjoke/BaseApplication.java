package com.fancenxing.fanchen.essayjoke;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.fancenxing.fanchen.baselibrary.base.ExceptionCrashHandler;
import com.fancenxing.fanchen.baselibrary.fixBug.FixDexManager;
import com.fancenxing.fanchen.baselibrary.http.HttpUtils;
import com.fancenxing.fanchen.essayjoke.hook.HookStartActivityUtil;
import com.fancenxing.fanchen.framelibrary.http.OkHttpEngine;

/**
 * 功能描述：
 * Created by 孙中宛 on 2018/4/18.
 */

public class BaseApplication extends Application {

//    public static PatchManager patchManager;

    @Override
    public void onCreate() {
        super.onCreate();

        //设置全局异常捕捉类
        ExceptionCrashHandler.getInstance()
                .init(this);

//        //初始化阿里的热修复
//        patchManager = new PatchManager(this);
//        patchManager.init(getVersion());
//
//        //加载之前的patch
//        patchManager.loadPatch();
        HttpUtils.init(new OkHttpEngine());
//        SkinManager.getInstance()
//                .init(this);
        try {
            FixDexManager dexManager = new FixDexManager(this);
            dexManager.fixDex("/storage/emulated/0/plugin.apk");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            HookStartActivityUtil activityUtil = new HookStartActivityUtil(this, ProxyActivity.class);
            activityUtil.hookStartActivity();
            activityUtil.hookLaunchActivity();
            activityUtil.hookAppcompatActivity();

        } catch (Exception e) {
            e.printStackTrace();
        }
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
