package com.fancenxing.fanchen.framelibrary.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * 功能描述：
 * Created by 孙中宛 on 2018/5/10.
 */

public class DaoSupport<T> implements IDaoSupport<T> {

    private SQLiteDatabase mSQLiteDatabase;
    private Class<T> mClazz;

    private static final Object[] mParams = new Object[2];

    private static final Map<String, Method> mPutMethods = new ArrayMap<>();

    @Override
    public void init(SQLiteDatabase sqLiteDatabase, Class<T> clazz) {
        mSQLiteDatabase = sqLiteDatabase;
        mClazz = clazz;
        StringBuffer sb = new StringBuffer();
        sb.append("create table if not exists " + DaoUtils.getTableName(clazz) + " (id integer primary key autoincrement, ");
        Field[] fields = mClazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isSynthetic()) {
                continue;
            }
            String name = field.getName();
            if (name.equals("serialVersionUID")) {
                continue;
            }
            Log.e("sun", "field name is " + name);
            String type = field.getType().getSimpleName();
            //type需要进行转换
            sb.append(name)
                    .append(" ")
                    .append(DaoUtils.getColumnType(type))
                    .append(", ");
        }
        if (fields.length > 0) {
            sb.replace(sb.length() - 2, sb.length(), ")");
        } else {
            sb.append(")");
        }
        Log.e("sun", sb.toString());
        mSQLiteDatabase.execSQL(sb.toString());
    }

    //插入数据库
    @Override
    public long insert(T object) {
        ContentValues values = contentValuesByObj(object);
        return mSQLiteDatabase.insert(DaoUtils.getTableName(mClazz), null, values);
    }

    @Override
    public void insert(List<T> data) {
        mSQLiteDatabase.beginTransaction();
        for (T t : data) {
            insert(t);
        }
        mSQLiteDatabase.setTransactionSuccessful();
        mSQLiteDatabase.endTransaction();
    }

    //object转成ContentValues;
    private ContentValues contentValuesByObj(T object) {
        ContentValues values = new ContentValues();
        Field[] fields = mClazz.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                String key = field.getName();
                if (field.isSynthetic() || "serialVersionUID".equals(key)) {
                    continue;
                }
                Object value = field.get(object);
                mParams[0] = key;
                mParams[1] = value;
                String fieldTypeName = field.getType().getName();
                Method method = mPutMethods.get(fieldTypeName);
                if (method == null) {
                    method = ContentValues.class.getDeclaredMethod("put", String.class, value.getClass());
                    mPutMethods.put(fieldTypeName, method);
                }
                method.invoke(values, mParams);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mParams[0] = null;
                mParams[1] = null;
            }
        }
        return values;
    }
}
