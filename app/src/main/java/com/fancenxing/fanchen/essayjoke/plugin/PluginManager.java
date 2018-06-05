package com.fancenxing.fanchen.essayjoke.plugin;

import android.content.Context;

import com.fancenxing.fanchen.baselibrary.fixBug.FixDexManager;

/**
 * 功能描述：
 * Created by 孙中宛 on 2018/6/5.
 */

public class PluginManager {

    public static final void install(Context context, String apkPath) {
        try {
            FixDexManager dexManager = new FixDexManager(context);
            dexManager.fixDex(apkPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
