package com.fancenxing.fanchen.baselibrary.navigationBar;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 功能描述：
 * Created by 孙中宛 on 2018/5/8.
 */
public abstract class AbsNavigationBar<T extends AbsNavigationBar.Builder.AbsNavigationBarParams> implements NavigationBar {

    private T mParams;
    private View mNavigationView;

    public AbsNavigationBar(T params) {
        this.mParams = params;
        createAndBindView();
    }

    /**
     * 绑定和创建View
     */
    private void createAndBindView() {
        if (mParams.parent == null) {
            //获取activity的根布局
            if (mParams.context instanceof Activity) {
                ViewGroup viewGroup = (ViewGroup) ((Activity) mParams.context).getWindow().getDecorView();
                mParams.parent = (ViewGroup) viewGroup.getChildAt(0);
            }
        }
        if (mParams.parent == null) {
            throw new RuntimeException("navigationView only use in activity");
        }
        mNavigationView = LayoutInflater.from(mParams.context)
                .inflate(bindLayoutId(), mParams.parent, false);
        mParams.parent.addView(mNavigationView, 0);
        applyView();
    }

    public T getParams() {
        return mParams;
    }

    public void setText(int viewId, String value) {
        if (TextUtils.isEmpty(value)) {
            return;
        }
        TextView tv = findViewById(viewId);
        if (tv != null) {
            tv.setText(value);
        }
    }

    public void setOnClickListener(int viewId, View.OnClickListener clickListener) {
        if (clickListener == null) {
            return;
        }
        findViewById(viewId).setOnClickListener(clickListener);
    }

    private <T extends View> T findViewById(int viewId) {
        return mNavigationView.findViewById(viewId);
    }

    public abstract static class Builder {

        AbsNavigationBarParams params;

        public Builder(Context context, ViewGroup parent) {
            params = new AbsNavigationBarParams(context, parent);
        }

        public Builder(Context context) {
            params = new AbsNavigationBarParams(context, null);
        }

        public abstract AbsNavigationBar builder();

        public static class AbsNavigationBarParams {
            public Context context;
            public ViewGroup parent;

            public AbsNavigationBarParams(Context context, ViewGroup parent) {
                this.context = context;
                this.parent = parent;
            }
        }
    }
}