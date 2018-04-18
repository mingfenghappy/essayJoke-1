package com.fancenxing.fanchen.baselibrary.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述：单例的设计模式的异常捕捉
 * Created by 孙中宛 on 2018/4/18.
 */

public class ExceptionCrashHandler implements Thread.UncaughtExceptionHandler {

    private static ExceptionCrashHandler mInstance;

    private static final String TAG = "crash";

    //获取应用的一些信息
    private Context mContext;

    //获取系统默认的
    private Thread.UncaughtExceptionHandler mDefaultExceptionHandler;

    public static ExceptionCrashHandler getInstance() {
        if (mInstance == null) {
            synchronized (ExceptionCrashHandler.class) {
                if (mInstance == null) {
                    mInstance = new ExceptionCrashHandler();
                }
            }
        }
        return mInstance;
    }

    public void init(Context context) {
        this.mContext = context;

        //设置全局的异常类为本类
        Thread.currentThread().setUncaughtExceptionHandler(this);

        //
        mDefaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        //全局异常
        Log.e(TAG, "出现未知异常");

        //写入到本地文件：手机信息，当前版本

        //4、保存当前文件，等应用再次启动再上传（上传文件不在这里处理）
        String crashFileName = saveInfoToSD(e);
        crashCrashFile(crashFileName);
        //系统默认处理
        mDefaultExceptionHandler.uncaughtException(t, e);
    }

    /**
     * 将错误信息保存到本地文件
     *
     * @param ex
     * @return
     */
    private String saveInfoToSD(Throwable ex) {
        String fileName = null;
        StringBuffer sb = new StringBuffer();

        //1、应用信息  包名 版本号

        for (Map.Entry<String, String> entry : obtainSimpleInfo(mContext).entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key)
                    .append(" = ")
                    .append(value)
                    .append("\n");
        }
        //3、崩溃的详细信息
        sb.append(obtainExceptionInfo(ex));
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File dir = new File(mContext.getFilesDir() + File.separator + "crash" + File.separator);

            //先删除之前的异常信息
            if (dir.exists()) {
                deleteDir(dir);
            }
            //再重新创建文件夹
            if (!dir.exists()) {
                dir.mkdir();
            }
            fileName = dir.toString()
                    + File.separator
                    + getAssignTime("yyyy_MM_dd_HH_mm") + ".txt";
            try {
                FileOutputStream fos = new FileOutputStream(fileName);
                fos.write(sb.toString().getBytes());
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileName;
    }

    /**
     * 获取系统未捕获的错误信息
     *
     * @param throwable
     * @return
     */
    private String obtainExceptionInfo(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        throwable.printStackTrace(printWriter);
        printWriter.close();
        return stringWriter.toString();
    }

    /**
     * 获取一些简单的信息，软件版本，手机版本，型号等信息
     *
     * @param context
     * @return
     */
    private HashMap<String, String> obtainSimpleInfo(Context context) {
        HashMap<String, String> map = new HashMap<>();
        PackageManager packManager = context.getPackageManager();
        PackageInfo info = null;
        try {
            info = packManager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (info != null) {
            map.put("versionName", info.versionName);
            map.put("versionCode", "" + info.versionCode);
        }
        map.put("model", Build.MODEL);
        map.put("SDK_INT", "" + Build.VERSION.SDK_INT);
        map.put("PRODUCT", Build.PRODUCT);
        //2、手机信息
        map.put("MOBILE_INFO", getMobileInfo());
        return map;
    }

    /**
     * 删除文件
     *
     * @param file
     */
    private void deleteDir(File file) {
        if (file == null) {
            return;
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                deleteDir(f);
            }
        } else {
            file.delete();
        }
    }

    /**
     * 格式化时间，按dateFormat的格式返回
     *
     * @param dateFormat
     * @return
     */
    private String getAssignTime(String dateFormat) {
        DateFormat format = new SimpleDateFormat(dateFormat);
        long currentTime = System.currentTimeMillis();
        return format.format(currentTime);
    }

    /**
     * 获取手机相关的一些信息
     *
     * @return
     */
    private String getMobileInfo() {
        StringBuffer buffer = new StringBuffer();
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            buffer.append(name);
            String value = null;
            try {
                value = field.get(null).toString();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            buffer.append(" = " + value);
            buffer.append("\n");
        }

        return buffer.toString();
    }

    private void crashCrashFile(String crashFileName) {
        SharedPreferences sp = mContext.getSharedPreferences("crash", Context.MODE_PRIVATE);
        sp.edit()
                .putString("CRASH_FILE_NAME", crashFileName)
                .commit();
    }

    public File getCrashFile(){
        String crashFileName = mContext.getSharedPreferences("crash",Context.MODE_PRIVATE)
                .getString("CRASH_FILE_NAME","");
        if (TextUtils.isEmpty(crashFileName)){
            return null;
        }else {
            return new File(crashFileName);
        }
    }
}
