package com.fancenxing.fanchen.essayjoke.pickImage;

import com.fancenxing.fanchen.essayjoke.SelectImageActivity;

import java.util.ArrayList;

/**
 * 功能描述：
 * Created by 孙中宛 on 2018/5/29.
 */

public class ImageSelector {

    private int mMaxCount = 9;
    private int mMode = SelectImageActivity.MODE_MULTI;
    private boolean mShowCamera = true;
    private ArrayList<String> mOriginData;

    public ImageSelector() {
    }

    public static ImageSelector create() {
        return new ImageSelector();
    }
}
