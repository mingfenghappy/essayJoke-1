package com.fancenxing.fanchen.framelibrary.skin.attr;

import android.view.View;

/**
 * 功能描述：
 * Created by 孙中宛 on 2018/5/18.
 */

public class SkinAttr {

    private String mResName;
    private SkinType mType;

    public SkinAttr(String mResName, SkinType mType) {
        this.mResName = mResName;
        this.mType = mType;
    }

    public void skin(View view) {
        mType.skin(view, mResName);
    }

    public String getResName() {
        return mResName;
    }

    public SkinType getType(){
        return mType;
    }

    public void setResName(String mResName) {
        this.mResName = mResName;
    }
}
