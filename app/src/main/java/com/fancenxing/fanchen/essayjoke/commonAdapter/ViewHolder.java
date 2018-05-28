package com.fancenxing.fanchen.essayjoke.commonAdapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 功能描述：
 * Created by 孙中宛 on 2018/5/24.
 */

public class ViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mView;

    public ViewHolder(View itemView) {
        super(itemView);
        mView = new SparseArray<>();
    }

    /**
     * 获取View
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mView.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
        }
        return (T) view;
    }

    /**
     * 设置文本
     *
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setText(int viewId, CharSequence text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    /**
     * 设置图片
     *
     * @param viewId
     * @param resId  图片的资源id
     * @return
     */
    public ViewHolder setImageResource(int viewId, int resId) {
        ImageView iv = getView(viewId);
        iv.setImageResource(resId);
        return this;
    }

    public ViewHolder setImageLoader(int viewId, HolderImageLoader imageLoader, String imgUrl) {
        ImageView iv = getView(viewId);
        imageLoader.loadImage(iv, imgUrl);
        return this;
    }


    public static abstract class HolderImageLoader {

        public HolderImageLoader() {
        }

        public abstract void loadImage(ImageView view, String path);

    }
}
