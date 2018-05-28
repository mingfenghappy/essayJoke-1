package com.fancenxing.fanchen.essayjoke;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;

import com.fancenxing.fanchen.baselibrary.base.BaseActivity;

import java.util.ArrayList;

public class SelectImageActivity extends BaseActivity {

    public static final int MODE_MULTI = 1;
    public static final int MODE_SINGLE = 2;

    public static final String EXTRA_MODE = "mode";
    public static final String EXTRA_SHOW_CAMERA = "camera";
    public static final String EXTRA_MAX_COUNT = "maxCount";
    public static final String EXTRA_RESULT_LIST = "resultList";
    private static final int LOADER_TYPE = 100;

    //单选或多选
    private int mMode;
    //是否显示拍照按钮
    private boolean mShowCamera = true;
    //图片的张数
    private int mMaxCount = 0;
    //已选择好的照片
    private ArrayList<String> mResultList;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_select_image);
        Intent intent = getIntent();
        mMode = intent.getIntExtra(EXTRA_MODE, MODE_MULTI);
        mShowCamera = intent.getBooleanExtra(EXTRA_SHOW_CAMERA, true);
        mMaxCount = intent.getIntExtra(EXTRA_MAX_COUNT, 3);
        mResultList = intent.getStringArrayListExtra(EXTRA_RESULT_LIST);
        if (mResultList == null) {
            mResultList = new ArrayList<>();
        }
        //初始化本地图片数据
        initImageList();
    }

    //初始化本地图片数据
    private void initImageList() {
        getLoaderManager().initLoader(LOADER_TYPE, null, mLoaderCallback);
    }

    private LoaderManager.LoaderCallbacks<Cursor> mLoaderCallback = new LoaderManager.LoaderCallbacks<Cursor>() {

        private final String[] IMAGE_PROJECTION = {MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media.MIME_TYPE, MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media._ID};

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            //查询数据库
            CursorLoader loader = new CursorLoader(SelectImageActivity.this,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    IMAGE_PROJECTION, IMAGE_PROJECTION[4] + ">0 and " + IMAGE_PROJECTION[3]
                    + "=? or " + IMAGE_PROJECTION[3] + "=?", new String[]{"image/jpeg", "image/png"}, IMAGE_PROJECTION[2] + " desc");
            return loader;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            //解析，封装到集合
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    };

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

}
