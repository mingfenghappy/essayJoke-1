package com.fancenxing.fanchen.framelibrary.db;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * 功能描述：
 * Created by 孙中宛 on 2018/5/10.
 */

public interface IDaoSupport<T> {

    void init(SQLiteDatabase sqLiteDatabase, Class<T> clazz);

    //插入数据
    long insert(T t);

    //批量插入 检测性能
    void insert(List<T> data);

//    void queryAll();
}
