package com.fancenxing.fanchen.baselibrary.fixBug;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import dalvik.system.BaseDexClassLoader;

/**
 * 功能描述：
 * Created by 孙中宛 on 2018/5/4.
 */

public class FixDexManager {

    private Context mContext;
    private File mDexDir;

    public FixDexManager(Context context) {
        mContext = context;
        //获取应用可以访问的dex目录
        mDexDir = context.getDir("odex", Context.MODE_PRIVATE);
    }

    /**
     * 修复dex包
     *
     * @param fixDexPath
     */
    public void fixDex(String fixDexPath) throws Exception {
        //1、先获取已经运行的dexElements
//        ClassLoader classLoader = mContext.getClassLoader();

        //2、获取已经下载好的dexElement
        //2.1、移动到西东能够访问的dex目录下   classLoader
        File srcFile = new File(fixDexPath);
        if (!srcFile.exists()) {
            throw new FileNotFoundException();
        }

        File destFile = new File(mDexDir, srcFile.getName());
        if (destFile.exists()) {
            return;
        }
        copyFile(srcFile, destFile);

        //2.2、classLoader 读取fixDex路径
        List<File> fixDexFiles = new ArrayList<>();
        fixDexFiles.add(destFile);
        fixDex(fixDexFiles);

//        File optimizeFile = new File(mDexDir, "odex");
//        if (!optimizeFile.exists()) {
//            optimizeFile.mkdirs();
//        }
//
//        Object dexElements = getDexElementsByClassLoader(classLoader);
//
//        for (File file : fixDexFiles) {
//            //dexPath dex路径
//            //optimizedDirectory 解压文件夹
//            //libraryPath .so文件位置
//            //parent 父classLoader
//            ClassLoader cl = new BaseDexClassLoader(file.getAbsolutePath(), optimizeFile, null, classLoader);
//            Object fixDexElements = getDexElementsByClassLoader(cl);
//            obtainArray(fixDexElements, dexElements);
//        }
//        //3、把补丁的dexElement插到已经运行的dexElements的最前面
//        injectDexElements(classLoader, dexElements);
    }

    /***
     * 把dexElements注入到classLoader
     * @param classLoader
     * @param dexElements
     */
    private void injectDexElements(ClassLoader classLoader, Object dexElements) throws Exception {
        Field field = BaseDexClassLoader.class.getDeclaredField("pathList");
        field.setAccessible(true);
        Object pathList = field.get(classLoader);
        Field elementsField = pathList.getClass().getDeclaredField("exElements");
        elementsField.setAccessible(true);
        elementsField.set(classLoader, dexElements);
    }


    private Object obtainArray(Object arrayLhs, Object arrayRhs) {
        Class<?> localClass = arrayLhs.getClass().getComponentType();
        int i = Array.getLength(arrayLhs);
        int j = Array.getLength(arrayRhs);
        Object result = Array.newInstance(localClass, j);
        for (int k = 0; k < j; k++) {
            if (k < i) {
                Array.set(result, k, Array.get(arrayLhs, k));
            } else {
                Array.set(result, k, Array.get(arrayRhs, k - i));
            }
        }
        return result;
    }

    /**
     * 从classLoader中获取dexElements
     *
     * @param classLoader
     * @return
     */
    private Object getDexElementsByClassLoader(ClassLoader classLoader) throws Exception {
        BaseDexClassLoader dexClassLoader = (BaseDexClassLoader) classLoader;
        Field field = BaseDexClassLoader.class.getDeclaredField("pathList");
        field.setAccessible(true);
        Object pathList = field.get(dexClassLoader);
        Field elementsField = pathList.getClass().getDeclaredField("exElements");
        elementsField.setAccessible(true);
        return elementsField.get(pathList);
    }

    /**
     * copy file
     *
     * @param src  source file
     * @param dest target file
     * @throws IOException
     */
    public static void copyFile(File src, File dest) throws IOException {
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            if (!dest.exists()) {
                dest.createNewFile();
            }
            inChannel = new FileInputStream(src).getChannel();
            outChannel = new FileOutputStream(dest).getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } finally {
            if (inChannel != null) {
                inChannel.close();
            }
            if (outChannel != null) {
                outChannel.close();
            }
        }
    }

    public void loadFixDex() {
        File[] files = mDexDir.listFiles();
        List<File> list = new ArrayList<>();
        for (File file : files) {
            if (file.getName().endsWith(".dex")) {
                list.add(file);
            }
        }

        try {
            fixDex(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fixDex(List<File> list) throws Exception {
        //1、先获取已经运行的dexElements
        ClassLoader classLoader = mContext.getClassLoader();
        Object dexElements = getDexElementsByClassLoader(classLoader);
        File optimizeFile = new File(mDexDir, "odex");
        if (!optimizeFile.exists()) {
            optimizeFile.mkdirs();
        }

        for (File file : list) {
            //dexPath dex路径
            //optimizedDirectory 解压文件夹
            //libraryPath .so文件位置
            //parent 父classLoader
            ClassLoader cl = new BaseDexClassLoader(file.getAbsolutePath(), optimizeFile, null, classLoader);
            Object fixDexElements = getDexElementsByClassLoader(cl);
            obtainArray(fixDexElements, dexElements);
        }
        //3、把补丁的dexElement插到已经运行的dexElements的最前面
        injectDexElements(classLoader, dexElements);
    }
}
