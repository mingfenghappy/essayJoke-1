package com.fancenxing.fanchen.essayjoke;

import android.content.Intent;
import android.view.View;

import com.fancenxing.fanchen.baselibrary.base.BaseActivity;

import java.util.ArrayList;

public class TestImageActivity extends BaseActivity {

    private ArrayList<String> mResults=new ArrayList<>();

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_test_image);
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

    public void selectImage(View view) {
        Intent intent = new Intent(this, SelectImageActivity.class);
        intent.putExtra(SelectImageActivity.EXTRA_MODE, SelectImageActivity.MODE_MULTI);
        intent.putExtra(SelectImageActivity.EXTRA_MAX_COUNT, 9);
        intent.putExtra(SelectImageActivity.EXTRA_SHOW_CAMERA, true);
        intent.putStringArrayListExtra(SelectImageActivity.EXTRA_RESULT_LIST, mResults);
        startActivity(intent);
    }
}
