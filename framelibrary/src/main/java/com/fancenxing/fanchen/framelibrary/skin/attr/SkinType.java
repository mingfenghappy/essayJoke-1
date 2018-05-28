package com.fancenxing.fanchen.framelibrary.skin.attr;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fancenxing.fanchen.framelibrary.skin.SkinManager;
import com.fancenxing.fanchen.framelibrary.skin.SkinResources;

/**
 * 功能描述：
 * Created by 孙中宛 on 2018/5/18.
 */

public enum SkinType {

    TEXT_COLOR("textColor") {
        @Override
        public void skin(View view, String resName) {
            ColorStateList color = getSkinResources().getColorByName(resName);
            if (color != null) {
                ((TextView) view).setTextColor(color);
            }
        }
    }, BACKGROUND("background") {
        @Override
        public void skin(View view, String resName) {
            SkinResources res = getSkinResources();
            Drawable drawable = res.getDrawableByName(resName);
            if (drawable != null) {
                view.setBackgroundDrawable(drawable);
            } else {
                ColorStateList color = res.getColorByName(resName);
                if (color != null) {
                    view.setBackgroundColor(color.getDefaultColor());
                }
            }
        }
    }, SRC("src") {
        @Override
        public void skin(View view, String resName) {
            Drawable drawable = getSkinResources().getDrawableByName(resName);
            if (drawable != null) {
                ((ImageView) view).setImageDrawable(drawable);
            }
        }
    };

    private String mResName;

    SkinType(String resName) {
        mResName = resName;
    }


    public abstract void skin(View view, String resName);

    public String getResName() {
        return mResName;
    }

    public SkinResources getSkinResources() {
        return SkinManager.getInstance()
                .getSkinResources();
    }
}
