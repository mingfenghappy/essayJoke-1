package com.fancenxing.fanchen.framelibrary.skin;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.lang.reflect.Method;

/**
 * 功能描述：皮肤的资源管理
 * Created by 孙中宛 on 2018/5/18.
 */

public class SkinResources {

    private Resources mSkinResource;

    private String mPackageName;

    public SkinResources(Context context, String skinPath) {
        try {
            Resources supRes = context.getResources();

            AssetManager assetManger = AssetManager.class.newInstance();

            //添加本地下载的皮肤资源
            Method method = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
            method.invoke(assetManger, skinPath);

            mSkinResource = new Resources(assetManger, supRes.getDisplayMetrics(), supRes.getConfiguration());

            //获取skinPath包名
            mPackageName = context.getPackageManager().getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES)
                    .packageName;
            Log.e("Sun", "create skinRes success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过名字获取图片
     *
     * @param resName
     * @return
     */
    public Drawable getDrawableByName(String resName) {
        Log.e("Sun", "drawable name " + resName);
        try {
            int resId = mSkinResource.getIdentifier(resName, "mipmap", mPackageName);
            Log.e("Sun", "drawable resId " + resId);
            return mSkinResource.getDrawable(resId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 通过名字获取颜色
     *
     * @param resName
     * @return
     */
    public ColorStateList getColorByName(String resName) {
        try {
            int resId = mSkinResource.getIdentifier(resName, "color", mPackageName);
            return mSkinResource.getColorStateList(resId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
