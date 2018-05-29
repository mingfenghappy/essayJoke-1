package com.fancenxing.fanchen.essayjoke.pickImage;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.fancenxing.fanchen.essayjoke.R;
import com.fancenxing.fanchen.essayjoke.commonAdapter.ViewHolder;

/**
 * 功能描述：
 * Created by 孙中宛 on 2018/5/29.
 */

public class GlideImageLoader extends ViewHolder.HolderImageLoader {
    private Context mContext;

    public GlideImageLoader(Context context) {
        mContext = context;
    }

    @Override
    public void loadImage(ImageView view, String path) {
        Glide.with(mContext)
                .load(path)
                .placeholder(R.mipmap.ic_launcher)
                .into(view);
    }
}
