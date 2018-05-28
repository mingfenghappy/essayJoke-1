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

public class GuardService extends Service {


    private int mGuardId = 1200;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("service", "guard connected");

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("service", "guard disconnected");
            //断开连接，重新启动，重新绑定
            startService(new Intent(GuardService.this, KeepLiveService.class));
            bindService(new Intent(GuardService.this, KeepLiveService.class), mServiceConnection, Context.BIND_AUTO_CREATE);
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new ProcessConnection.Stub() {

        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //提高进程的优先级
        startForeground(mGuardId, new Notification());

        //绑定建立连接
        Intent bindIntent = new Intent(this, KeepLiveService.class);
        bindService(bindIntent, mServiceConnection, Context.BIND_AUTO_CREATE);

        return START_STICKY;
    }
}
