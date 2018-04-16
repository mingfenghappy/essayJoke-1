package com.fancenxing.fanchen.baselibrary.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.fancenxing.fanchen.baselibrary.ioc.ViewUtils;

/**
 * 功能描述：整个应用的BaseActivity
 * Created by 孙中宛 on 2018/4/16.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置布局Layout
        setContentView();

        //一些特定的算法，子类都会使用的
        ViewUtils.inject(this);

        //初始化头部
        initTitle();

        //初始化界面
        initView();

        //初始化数据
        initData();
    }

    //设置布局Layout
    protected abstract void setContentView();

    //初始化头部
    protected abstract void initTitle();

    //初始化界面
    protected abstract void initView();

    //初始化数据
    protected abstract void initData();

    /**
     * 启动activity
     *
     * @param clazz
     */
    protected void startActivity(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

}
