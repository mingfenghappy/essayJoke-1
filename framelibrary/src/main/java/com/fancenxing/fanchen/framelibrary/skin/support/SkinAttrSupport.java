package com.fancenxing.fanchen.framelibrary.skin.support;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;

import com.fancenxing.fanchen.framelibrary.skin.attr.SkinAttr;
import com.fancenxing.fanchen.framelibrary.skin.attr.SkinType;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：皮肤属性解析的支持类
 * Created by 孙中宛 on 2018/5/18.
 */

public class SkinAttrSupport {

    /**
     * 获取skinAttr的属性
     *
     * @param context
     * @param attrs
     * @return
     */
    public static List<SkinAttr> getSkinView(Context context, AttributeSet attrs) {
        List<SkinAttr> skinAttrs = new ArrayList<>();
        int length = attrs.getAttributeCount();
        for (int i = 0; i < length; i++) {
            //获取名称
            String attrName = attrs.getAttributeName(i);
            String attrValue = attrs.getAttributeValue(i);
            Log.e("attr", "----attr name -->" + attrName + "--->" + attrValue);
            SkinType skinType = getSkinType(attrName);
            if (skinType != null) {
                //资源名称  目前只能有attrValue 是一个@int 类型
                String resName = getResName(context, attrValue);
                if (TextUtils.isEmpty(resName)) {
                    continue;
                }
                SkinAttr attr = new SkinAttr(resName, skinType);
                skinAttrs.add(attr);
            }
        }
        return skinAttrs;
    }

    /**
     * 获取资源名称
     *
     * @param attrValue
     * @return
     */
    private static String getResName(Context context, String attrValue) {
        if (attrValue.startsWith("@")) {
            attrValue = attrValue.substring(1);
            int resId = Integer.parseInt(attrValue);
            return context.getResources().getResourceEntryName(resId);
        }
        return null;
    }

    /**
     * 通过名称获取SkinType
     *
     * @param attrName
     * @return
     */
    private static SkinType getSkinType(String attrName) {
        SkinType[] skinTypes = SkinType.values();
        for (SkinType skinType : skinTypes) {
            if (skinType.getResName().equals(attrName)) {
                return skinType;
            }
        }
        return null;
    }


}
