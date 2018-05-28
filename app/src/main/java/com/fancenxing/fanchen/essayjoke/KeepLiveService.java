package com.fancenxing.fanchen.essayjoke;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * 功能描述：
 * Created by 孙中宛 on 2018/5/22.
 */

public class KeepLiveService extends Service {


    private int messageId = 1000;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("service", "keep live connected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("service", "keep live disconnected");
            startService(new Intent(KeepLiveService.this, GuardService.class));
            bindService(new Intent(KeepLiveService.this, GuardService.class), mServiceConnection, Context.BIND_AUTO_CREATE);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Log.e("service", "等待接收消息");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //提高进程优先级
        startForeground(messageId, new Notification());
        //绑定服务
        bindService(new Intent(this, GuardService.class), mServiceConnection, Context.BIND_AUTO_CREATE);

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new ProcessConnection.Stub() {

        };
    }


}
