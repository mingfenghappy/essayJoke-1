package com.fancenxing.fanchen.framelibrary.db;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;

/**
 * 功能描述：
 * Created by 孙中宛 on 2018/5/10.
 */

public class DaoSupportFactory {

    private SQLiteDatabase mSQLiteDatabase;

    private static DaoSupportFactory mFactory;

    //持有外部数据库的引用
    private DaoSupportFactory() {
        //把数据库放到内存卡里面  判断是否有内存卡  6.0动态申请权限
        File dbRoot = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "nhdz" + File.separator + "database");
        if (!dbRoot.exists()) {
            dbRoot.mkdirs();
        }
        File dbFile = new File(dbRoot, "nhdz.db");

        mSQLiteDatabase = SQLiteDatabase.openOrCreateDatabase(dbFile, null);
    }

    public static DaoSupportFactory getFactory() {
        if (mFactory == null) {
            synchronized (DaoSupportFactory.class) {
                if (mFactory == null) {
                    mFactory = new DaoSupportFactory();
                }
            }
        }
        return mFactory;
    }

    public <T> IDaoSupport<T> getDao(Class<T> clazz) {
        IDaoSupport<T> daoSupport = new DaoSupport<>();
        daoSupport.init(mSQLiteDatabase, clazz);
        return daoSupport;
    }
}
