package com.fancenxing.fanchen.framelibrary.db;

/**
 * 功能描述：
 * Created by 孙中宛 on 2018/5/10.
 */

public class DaoUtils {

    public static String getColumnType(String type) {
        String value = null;
        if (type.contains("String")) {
            value = "varchar";
        } else if (type.contains("int")) {
            value = "integer";
        }
        return value;
    }

    public static String getTableName(Class mClazz) {
        return mClazz.getSimpleName();
    }
}
