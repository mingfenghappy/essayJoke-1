package com.fancenxing.fanchen.baselibrary.ioc;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 功能描述：
 * Created by 孙中宛 on 2018/4/11.
 */

public class ViewUtils {

    public static void inject(Activity activity) {
        inject(new ViewFinder(activity), activity);

    }

    public static void inject(View view, Object obj) {
        inject(new ViewFinder(view), obj);
    }

    public static void inject(View view) {
        inject(new ViewFinder(view), view);
    }

    //兼容上面的方法 object反射需要执行的类
    private static void inject(ViewFinder finder, Object obj) {
        injectField(finder, obj);
        injectEvent(finder, obj);
    }

    /**
     * 注入事件
     *
     * @param finder
     * @param obj
     */
    private static void injectEvent(ViewFinder finder, Object obj) {
        //1、获取类里面的方法
        Class<?> clazz = obj.getClass();
        Method[] methods = clazz.getDeclaredMethods();

        //2、获取事件注解里的值
        for (Method method : methods) {
            OnClick onClick = method.getAnnotation(OnClick.class);
            if (onClick == null) {
                continue;
            }
            //3、获取View
            int[] values = onClick.value();
            CheckNet checkNet = method.getAnnotation(CheckNet.class);
            boolean isCheckNet = checkNet == null ? false : true;
            for (int value : values) {
                View view = finder.findViewById(value);
                if (view == null) {
                    continue;
                }
                //4、动态的为方法注入事件
                view.setOnClickListener(new DeclaredOnClickListener(obj, method, isCheckNet));
            }
        }
    }


    private static class DeclaredOnClickListener implements View.OnClickListener {

        private Object mObj;
        private Method mMethod;
        private boolean mIsCheckNet;

        public DeclaredOnClickListener(Object mObj, Method mMethod, boolean isCheckNet) {
            this.mObj = mObj;
            this.mMethod = mMethod;
            mIsCheckNet = isCheckNet;
        }

        @Override
        public void onClick(View v) {
            //是否需要检测网络
            if (mIsCheckNet && !networkAvailable(v.getContext())) {
                Toast.makeText(v.getContext(), "网络不可用", Toast.LENGTH_SHORT).show();
                return;
            }
            //点击调用该方法
            try {
                mMethod.setAccessible(true);
                mMethod.invoke(mObj, v);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                Log.e("testE", e.getCause().getMessage());
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                Log.e("testE", e.getCause().getMessage());
            }

        }
    }

    /**
     * 注入属性
     *
     * @param finder
     * @param obj
     */
    private static void injectField(ViewFinder finder, Object obj) {
        //1、获取类里面所有的属性
        Class<?> clazz = obj.getClass();
        //获取所有属性，包括公有和私有的
        Field[] fields = clazz.getDeclaredFields();

        //2、获取viewById里面的value值
        for (Field field : fields) {
            ViewById viewById = field.getAnnotation(ViewById.class);
            if (viewById != null) {
                int value = viewById.value();
                //3、获取View
                View view = finder.findViewById(value);
                if (view == null) {
                    continue;
                }
                try {
                    //4、动态的注入找到的View
                    field.setAccessible(true);
                    field.set(obj, view);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private static boolean networkAvailable(Context context) {
        boolean isAvailable = false;
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null) {
            isAvailable = info.isAvailable();
        }

        return isAvailable;
    }
}
