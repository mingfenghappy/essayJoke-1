package com.fancenxing.fanchen.baselibrary.ioc;

import android.app.Activity;
import android.view.View;

/**
 * 功能描述：View的findViewById的辅助类
 * Created by 孙中宛 on 2018/4/11.
 */

public class ViewFinder {

    private Activity mActivity;
    private View mView;

    public ViewFinder(Activity activity) {
        mActivity = activity;
    }

    public ViewFinder(View view) {
        mView = view;
    }

    public View findViewById(int viewId) {
        return mActivity != null ? mActivity.findViewById(viewId) : mView.findViewById(viewId);
    }
}
