package com.fancenxing.fanchen.essayjoke.hook;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


/**
 * 功能描述：
 * Created by 孙中宛 on 2018/6/1.
 */

public class HookStartActivityUtil {

    private Context mContext;
    private static final String EXTRA_ORIGIN = "orgin";
    private Class<?> mProxyClass;

    public HookStartActivityUtil(Context mContext, Class clazz) {
        this.mContext = mContext;
        this.mProxyClass = clazz;
    }

    //版本23可调用此方法
    public void hookStartActivity() throws Exception {
        Class<?> activityManagerClass = Class.forName("android.app.ActivityManager");
        Field field = activityManagerClass.getDeclaredField("IActivityManagerSingleton");
        field.setAccessible(true);
        Object singleton = field.get(null);

        Class<?> singletonClass = Class.forName("android.util.Singleton");
        Field mInstanceField = singletonClass.getDeclaredField("mInstance");
        mInstanceField.setAccessible(true);
        Object mInstance = mInstanceField.get(singleton);

        Class<?> clazz = Class.forName("android.app.IActivityManager");
        Object value = Proxy.newProxyInstance(HookStartActivityUtil.class.getClassLoader(),
                new Class[]{clazz}, new HookStartActivityUtilHanlder(mInstance));
        mInstanceField.set(singleton, value);
    }

    private class HookStartActivityUtilHanlder implements InvocationHandler {
        private Object mObj;

        public HookStartActivityUtilHanlder(Object obj) {
            this.mObj = obj;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String methodName = method.getName();
            if ("startActivity".equals(methodName)) {
                Intent origin = (Intent) args[2];
                String className = origin.getComponent().getClassName();
                if ("com.fancenxing.fanchen.essayjoke.TestActivity".equals(className)) {
                    Intent newIntent = new Intent(mContext, mProxyClass);
                    newIntent.putExtra(EXTRA_ORIGIN, origin);
                    args[2] = newIntent;
                }
            }
            return method.invoke(mObj, args);
        }
    }


    public void hookLaunchActivity() throws Exception {
        Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
        Field field = activityThreadClass.getDeclaredField("sCurrentActivityThread");
        field.setAccessible(true);

        Object activityThread = field.get(null);

        Field mhField = activityThreadClass.getDeclaredField("mH");
        mhField.setAccessible(true);
        Handler handler = (Handler) mhField.get(activityThread);

        Field callbackField = Handler.class.getDeclaredField("mCallback");
        callbackField.setAccessible(true);
        Log.e("sun", "---handler1 ---" + callbackField.getName());

        callbackField.set(handler, new CallbackHandler());
    }

    private class CallbackHandler implements Handler.Callback {

        @Override
        public boolean handleMessage(Message msg) {
            Log.e("sun", "---handleMessage ---" + msg.what);
            if (msg.what == 100) {
                handlerLauncherActivity(msg);
            }
            return false;
        }

        private void handlerLauncherActivity(Message msg) {
            Object record = msg.obj;
            try {
                Field field = record.getClass().getDeclaredField("intent");
                field.setAccessible(true);
                Intent currentIntent = (Intent) field.get(record);
                Intent originIntent = currentIntent.getParcelableExtra(EXTRA_ORIGIN);
                Log.e("sun", "----activity name---" + originIntent.getComponent().getClassName());
                if (originIntent != null) {
                    field.set(record, originIntent);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void hookAppcompatActivity() {
        try {
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Field currentActivityThreadField = activityThreadClass.getDeclaredField("sCurrentActivityThread");
            currentActivityThreadField.setAccessible(true);

            Object currentActivityThread = currentActivityThreadField.get(null);

            Method packageManagerMethod = activityThreadClass.getDeclaredMethod("getPackageManager");
            Object packageManager = packageManagerMethod.invoke(currentActivityThread, null);

            Class<?> iPackageMangerClass = Class.forName("android.content.pm.IPackageManager");
            Object proxyPackageManger = Proxy.newProxyInstance(HookStartActivityUtil.class.getClassLoader(), new Class[]{iPackageMangerClass},
                    new PackageMangeInvocationHandler(packageManager));

            Field sPackageManagerField = activityThreadClass.getDeclaredField("sPackageManager");
            sPackageManagerField.setAccessible(true);
            sPackageManagerField.set(currentActivityThread, proxyPackageManger);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class PackageMangeInvocationHandler implements InvocationHandler {

        private Object mObj;

        public PackageMangeInvocationHandler(Object mObj) {
            this.mObj = mObj;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if ("getActivityInfo".equals(method.getName())) {
                ComponentName newComponentName = new ComponentName(mContext, mProxyClass);
                args[0] = newComponentName;
            }
            return method.invoke(mObj, args);
        }
    }
}
