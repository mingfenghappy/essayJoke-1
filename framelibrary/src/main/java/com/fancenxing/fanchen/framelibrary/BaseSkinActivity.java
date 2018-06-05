package com.fancenxing.fanchen.framelibrary;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;

import com.fancenxing.fanchen.baselibrary.base.BaseActivity;
import com.fancenxing.fanchen.framelibrary.skin.SkinManager;
import com.fancenxing.fanchen.framelibrary.skin.SkinResources;
import com.fancenxing.fanchen.framelibrary.skin.attr.SkinAttr;
import com.fancenxing.fanchen.framelibrary.skin.attr.SkinView;
import com.fancenxing.fanchen.framelibrary.skin.callback.ISkinChangeListener;
import com.fancenxing.fanchen.framelibrary.skin.support.SkinAppCompatViewInflater;
import com.fancenxing.fanchen.framelibrary.skin.support.SkinAttrSupport;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：插件换肤
 * Created by 孙中宛 on 2018/4/17.
 */

public abstract class BaseSkinActivity extends BaseActivity implements LayoutInflater.Factory2, ISkinChangeListener {

    SkinAppCompatViewInflater mAppCompatViewInflater;

    private static final boolean IS_PRE_LOLLIPOP = Build.VERSION.SDK_INT < 21;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(this);
        LayoutInflaterCompat.setFactory2(inflater, this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        //拦截到view的创建 获取view之后再去解析
        //1、创建view
        View view = createView(parent, name, context, attrs);
        Log.e("skin", view + "");
        //2、解析属性  src textColor background 自定义属性
        if (view != null) {
            List<SkinAttr> skinAttrs = SkinAttrSupport.getSkinView(context, attrs);
            SkinView skinView = new SkinView(view, skinAttrs);
            //3、统一交给SkinManager管理
            managerSkinView(skinView);
            //4、判断下要不要换肤
            SkinManager.getInstance()
                    .checkChangeSkin(skinView);
        }
        return view;
    }

    private void managerSkinView(SkinView skinView) {
        List<SkinView> skinViews = SkinManager.getInstance()
                .getSkinViews(this);
        if (skinViews == null) {
            skinViews = new ArrayList<>();
            SkinManager.getInstance()
                    .register(this, skinViews);
        }
        skinViews.add(skinView);

    }

    public View createView(View parent, final String name, @NonNull Context context,
                           @NonNull AttributeSet attrs) {
        if (mAppCompatViewInflater == null) {
            mAppCompatViewInflater = new SkinAppCompatViewInflater();
        }

        boolean inheritContext = false;
        if (IS_PRE_LOLLIPOP) {
            inheritContext = (attrs instanceof XmlPullParser)
                    // If we have a XmlPullParser, we can detect where we are in the layout
                    ? ((XmlPullParser) attrs).getDepth() > 1
                    // Otherwise we have to use the old heuristic
                    : shouldInheritContext((ViewParent) parent);
        }

        return mAppCompatViewInflater.createView(parent, name, context, attrs, inheritContext,
                IS_PRE_LOLLIPOP, /* Only read android:theme pre-L (L+ handles this anyway) */
                true, /* Read read app:theme as a fallback at all times for legacy reasons */
                shouldBeUsed() /* Only tint wrap the context if enabled */
        );
    }

    public static boolean shouldBeUsed() {
        return AppCompatDelegate.isCompatVectorFromResourcesEnabled()
                && Build.VERSION.SDK_INT <= MAX_SDK_WHERE_REQUIRED;
    }

    /**
     * The maximum API level where this class is needed.
     */
    public static final int MAX_SDK_WHERE_REQUIRED = 20;

    private boolean shouldInheritContext(ViewParent parent) {
        if (parent == null) {
            // The initial parent is null so just return false
            return false;
        }
        final View windowDecor = getWindow().getDecorView();
        while (true) {
            if (parent == null) {
                // Bingo. We've hit a view which has a null parent before being terminated from
                // the loop. This is (most probably) because it's the root view in an inflation
                // call, therefore we should inherit. This works as the inflated layout is only
                // added to the hierarchy at the end of the inflate() call.
                return true;
            } else if (parent == windowDecor || !(parent instanceof View)
                    || ViewCompat.isAttachedToWindow((View) parent)) {
                // We have either hit the window's decor view, a parent which isn't a View
                // (i.e. ViewRootImpl), or an attached view, so we know that the original parent
                // is currently added to the view hierarchy. This means that it has not be
                // inflated in the current inflate() call and we should not inherit the context.
                return false;
            }
            parent = parent.getParent();
        }
    }

    @Override
    public void changeSkin(SkinResources skinResources) {

    }

    @Override
    protected void onDestroy() {
        SkinManager.getInstance()
                .unregister(this);
        super.onDestroy();
    }
}
