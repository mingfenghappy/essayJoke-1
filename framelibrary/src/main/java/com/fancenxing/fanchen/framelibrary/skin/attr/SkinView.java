package com.fancenxing.fanchen.framelibrary.skin.attr;

import android.view.View;

import java.util.List;

/**
 * 功能描述：
 * Created by 孙中宛 on 2018/5/18.
 */

public class SkinView {

    private View mView;

    private List<SkinAttr> mAttrs;

    public SkinView(View view, List<SkinAttr> skinAttrs) {
        this.mView = view;
        this.mAttrs = skinAttrs;
    }

    public void skin() {
        for (SkinAttr attr : mAttrs) {
            attr.skin(mView);
        }
    }

    public View getView() {
        return mView;
    }

    public List<SkinAttr> getAttrs() {
        return mAttrs;
    }
}
