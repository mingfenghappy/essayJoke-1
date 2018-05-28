package com.fancenxing.fanchen.framelibrary.skin;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.fancenxing.fanchen.framelibrary.skin.attr.SkinAttr;
import com.fancenxing.fanchen.framelibrary.skin.attr.SkinView;
import com.fancenxing.fanchen.framelibrary.skin.callback.ISkinChangeListener;
import com.fancenxing.fanchen.framelibrary.skin.config.SkinConfig;
import com.fancenxing.fanchen.framelibrary.skin.config.SkinPreUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 功能描述：皮肤的管理类
 * Created by 孙中宛 on 2018/5/18.
 */

public class SkinManager {

    private Context mContext;

    private static Map<ISkinChangeListener, List<SkinView>> mSkinViews;

    private static SkinManager mInstance;

    private SkinResources mSkinResources;

    static {
        mInstance = new SkinManager();
        mSkinViews = new HashMap<>();
    }

    public static SkinManager getInstance() {
        return mInstance;
    }

    public void init(Context context) {
        mContext = context.getApplicationContext();

        //防止皮肤被任意删除
        String currentSkinPath = SkinPreUtils.getInstance(mContext)
                .getSkinPath();
        File file = new File(currentSkinPath);
        if (!file.exists()) {
            //不存在
            SkinPreUtils.getInstance(mContext)
                    .clearSkinInfo();
            return;
        }
        //是否能获取包名
        String packageName = context.getPackageManager()
                .getPackageArchiveInfo(currentSkinPath, PackageManager.GET_ACTIVITIES)
                .packageName;
        Log.e("sun", "skinPath----->" + currentSkinPath);
        if (TextUtils.isEmpty(packageName)) {
            SkinPreUtils.getInstance(mContext)
                    .clearSkinInfo();
            return;
        }
        //做一些初始化的工作
        mSkinResources = new SkinResources(mContext, currentSkinPath);
    }


    /**
     * 恢复默认
     *
     * @return
     */
    public int restoreDefault() {
        //判断当前是否有皮肤，若有再处理
        String currentSkinPath = SkinPreUtils.getInstance(mContext)
                .getSkinPath();
        if (TextUtils.isEmpty(currentSkinPath)) {
            return SkinConfig.SKIN_NO_CHANGE;
        }

        //当前运行app的路径
        String skinPath = mContext.getPackageResourcePath();
        loadSkin(skinPath);
        SkinPreUtils.getInstance(mContext)
                .clearSkinInfo();
        return SkinConfig.SKIN_CHANGE_SUCCESS;
    }

    /**
     * 加载皮肤
     *
     * @param skinPath
     * @return
     */
    public int loadSkin(String skinPath) {
        //当前皮肤是否已更换
        String currentSkinPath = SkinPreUtils.getInstance(mContext)
                .getSkinPath();
        if (skinPath.equals(currentSkinPath)) {
            return SkinConfig.SKIN_NO_CHANGE;
        }
        File file = new File(skinPath);
        if (!file.exists()) {
            return SkinConfig.SKIN_NO_FILE;
        }
        String packageName = mContext.getPackageManager()
                .getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES)
                .packageName;
        if (TextUtils.isEmpty(packageName)) {
            return SkinConfig.SKIN_FILE_ERROR;
        }
        //校验签名
        //初始化资源管理
        mSkinResources = new SkinResources(mContext, skinPath);

        //改变皮肤
        changeSkin();
        //保存皮肤的状态
        saveSkinStatus(skinPath);
        return SkinConfig.SKIN_CHANGE_SUCCESS;
    }

    /**
     * 改变皮肤
     */
    private void changeSkin() {
        Set<ISkinChangeListener> keys = mSkinViews.keySet();
        for (ISkinChangeListener key : keys) {
            List<SkinView> skinViews = mSkinViews.get(key);
            for (SkinView skinView : skinViews) {
                skinView.skin();
            }
            key.changeSkin(mSkinResources);
        }
    }

    private void saveSkinStatus(String skinPath) {
        SkinPreUtils.getInstance(mContext)
                .saveSkinPath(skinPath);
    }

    /**
     * 通过Activity获取skinView
     *
     * @param activity
     * @return
     */
    public List<SkinView> getSkinViews(Activity activity) {
        return mSkinViews.get(activity);
    }

    public void register(ISkinChangeListener skinChangeListener, List<SkinView> skinViews) {
        mSkinViews.put(skinChangeListener, skinViews);
    }

    public void updateSkinView(Activity activity, View view, List<SkinAttr> newAttrs) {
        List<SkinView> skinViews = mSkinViews.get(activity);
        for (SkinView skinView : skinViews) {
            if (skinView.getView() == view) {
                List<SkinAttr> attrs = skinView.getAttrs();
                for (SkinAttr attr : attrs) {
                    for (SkinAttr newAttr : newAttrs) {
                        if (attr.getType().equals(newAttr.getType()) && !attr.getResName().equals(newAttr.getResName())) {
                            attr.setResName(newAttr.getResName());
                            attr.skin(view);
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取当前的皮肤资源管理
     *
     * @return
     */
    public SkinResources getSkinResources() {
        return mSkinResources;
    }

    /**
     * 检测是否换肤
     *
     * @param skinView
     */
    public void checkChangeSkin(SkinView skinView) {
        String skinPath = SkinPreUtils.getInstance(mContext)
                .getSkinPath();
        if (!TextUtils.isEmpty(skinPath)) {
            skinView.skin();
        }
    }

    public void unregister(ISkinChangeListener skinChangeListener) {
        if (mSkinViews != null) {
            mSkinViews.remove(skinChangeListener);
        }
    }
}
