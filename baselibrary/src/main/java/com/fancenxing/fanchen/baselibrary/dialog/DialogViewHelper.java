package com.fancenxing.fanchen.baselibrary.dialog;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * 功能描述：dialog view的辅助处理类
 * Created by 孙中宛 on 2018/4/27.
 */

class DialogViewHelper {

    private View mContentView;
    private SparseArray<WeakReference<View>> mViews;

    public DialogViewHelper() {
        mViews = new SparseArray<>();
    }

    public DialogViewHelper(Context context, int contentViewResId) {
        this();
        mContentView = LayoutInflater.from(context).inflate(contentViewResId, null);
    }

    public void setContentView(View contentView) {
        mContentView = contentView;
    }

    //设置文本
    public void setText(int viewId, CharSequence charSequence) {
        TextView view = getView(viewId);
        if (view != null) {
            view.setText(charSequence);
        }

    }

    //设置点击事件
    public void setOnClickListener(int viewId, WeakReference<View.OnClickListener> onClickListenerWeakReference) {
        View view = getView(viewId);
        if (view != null && onClickListenerWeakReference != null) {
            view.setOnClickListener(onClickListenerWeakReference.get());
        }
    }

    public View getContentView() {
        return mContentView;
    }

    public <T extends View> T getView(int viewId) {
        WeakReference<View> reference = mViews.get(viewId);
        View view = null;
        if (reference != null) {
            view = reference.get();
        }
        if (view == null) {
            view = mContentView.findViewById(viewId);
            if (view == null) {
                return null;
            } else {
                mViews.put(viewId, new WeakReference<>(view));
            }
        }
        return (T) view;
    }
}
