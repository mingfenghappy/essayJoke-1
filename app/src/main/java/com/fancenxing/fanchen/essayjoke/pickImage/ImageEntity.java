package com.fancenxing.fanchen.essayjoke.pickImage;

import android.text.TextUtils;

/**
 * 功能描述：
 * Created by 孙中宛 on 2018/5/28.
 */

public class ImageEntity {

    public String path;
    public String name;
    public String time;

    public ImageEntity(String path, String name, String time) {
        this.path = path;
        this.name = name;
        this.time = time;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ImageEntity) {
            ImageEntity compare = (ImageEntity) obj;
            return TextUtils.equals(this.path, compare.path);
        }
        return false;
    }
}
