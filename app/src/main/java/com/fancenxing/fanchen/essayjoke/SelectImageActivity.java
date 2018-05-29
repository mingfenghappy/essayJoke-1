package com.fancenxing.fanchen.essayjoke;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fancenxing.fanchen.baselibrary.base.BaseActivity;
import com.fancenxing.fanchen.essayjoke.pickImage.SelectImageListAdapter;

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

    private SelectImageListAdapter mImageAdapter;
    private RecyclerView mRvImage;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_select_image);
        mRvImage = findViewById(R.id.rv_image);
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
            if (data == null || data.getCount() <= 0) {
                return;
            }
            if (mShowCamera && !mResultList.contains(EXTRA_SHOW_CAMERA)) {
                mResultList.add(EXTRA_SHOW_CAMERA);
            }
            //解析，封装到集合
            while (data.moveToNext()) {
                String path = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]));
                if (!mResultList.contains(path)) {
                    mResultList.add(path);
                }
            }
            showImageList();
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    };

    /**
     * 显示图片列表
     */
    private void showImageList() {
        if (mImageAdapter == null) {
            mImageAdapter = new SelectImageListAdapter(this, mResultList);
            mImageAdapter.setMaxCount(mMaxCount);
            mRvImage.setAdapter(mImageAdapter);
            mRvImage.setLayoutManager(new GridLayoutManager(this, 3));
        }
        mImageAdapter.notifyDataSetChanged();
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

    public void imageSelected(View view) {
        Intent intent = new Intent();
        intent.putStringArrayListExtra(SelectImageActivity.EXTRA_RESULT_LIST, mImageAdapter.getSelectList());
        setResult(RESULT_OK, intent);
        finish();
    }
}
