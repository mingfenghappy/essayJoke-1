package com.fancenxing.fanchen.framelibrary;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.fancenxing.fanchen.baselibrary.navigationBar.AbsNavigationBar;

/**
 * 功能描述：
 * Created by 孙中宛 on 2018/5/8.
 */

public class DefaultNavigationBar extends AbsNavigationBar<DefaultNavigationBar.Builder.DefaultNavigationParams> {

    public DefaultNavigationBar(Builder.DefaultNavigationParams params) {
        super(params);
    }

    @Override
    public int bindLayoutId() {
        return R.layout.title_bar;
    }

    @Override
    public void applyView() {
        //绑定效果
        setText(R.id.title, getParams().title);
        setText(R.id.right_text, getParams().rightText);
        setOnClickListener(R.id.right_text, getParams().rightClickListener);
        setOnClickListener(R.id.back, getParams().leftClickListener);
    }


    public static class Builder extends AbsNavigationBar.Builder {

        DefaultNavigationParams params;

        public Builder(Context context, ViewGroup parent) {
            super(context, parent);
            params = new DefaultNavigationParams(context, parent);
        }

        public Builder(Context context) {
            this(context, null);
        }


        @Override
        public AbsNavigationBar builder() {
            DefaultNavigationBar navigationBar = new DefaultNavigationBar(params);
            return navigationBar;
        }

        //1、设置所有效果
        public DefaultNavigationBar.Builder setTitle(String title) {
            params.title = title;
            return this;
        }


        public DefaultNavigationBar.Builder setRightTitle(String rightTitle) {
            params.rightText = rightTitle;
            return this;
        }

        public DefaultNavigationBar.Builder setRightIcon(int rightIconRes) {
            params.rightIconRes = rightIconRes;
            return this;
        }

        public DefaultNavigationBar.Builder setRightClickListener(View.OnClickListener rightClickListener) {
            params.rightClickListener = rightClickListener;
            return this;
        }

        public DefaultNavigationBar.Builder setLeftClickListener(View.OnClickListener leftClickListener) {
            params.leftClickListener = leftClickListener;
            return this;
        }

        public static class DefaultNavigationParams extends AbsNavigationBarParams {

            String title;
            String rightText;
            View.OnClickListener rightClickListener;
            int rightIconRes;
            View.OnClickListener leftClickListener;

            public DefaultNavigationParams(Context con, ViewGroup parent) {
                super(con, parent);
                leftClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((Activity) context).finish();
                    }
                };
            }
        }
    }
}
