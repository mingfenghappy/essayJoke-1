package com.fancenxing.fanchen.essayjoke;

import android.content.Intent;
import android.view.View;

import com.fancenxing.fanchen.baselibrary.base.BaseActivity;
import com.fancenxing.fanchen.baselibrary.ioc.OnClick;

/**
 * 功能描述：
 * Created by 孙中宛 on 2018/5/23.
 */

public class RecyclerViewActivity extends BaseActivity {
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_recycler_view);
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_basic})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.tv_basic:
                intent.setClass(this, BasicActivity.class);
                break;
        }
        startActivity(intent);
    }
}
