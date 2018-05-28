package com.fancenxing.fanchen.essayjoke;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * 功能描述：
 * Created by 孙中宛 on 2018/5/21.
 */

public class MessageService extends Service {

    private final UserAidl.Stub mBinder = new UserAidl.Stub() {

        @Override
        public String getUserName() throws RemoteException {
            return "test";
        }

        @Override
        public String getUserPwd() throws RemoteException {
            return "123456";
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


}
