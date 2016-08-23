package com.lizhi.demo.ipc;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;

import com.lizhi.demo.utils.LogUtil;

/**
 * Created by Administrator on 2015/12/27.
 */
public class IpcService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }


    public Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            LogUtil.log("---------->" + msg.arg1);
        }
    };

    public class MyBinder extends Binder {


        public MyBinder() {
            LogUtil.log("----------MyBinder----------");


        }

    }


    public final Messenger messenger = new Messenger(myHandler);


    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.log("----------onCreate------------");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.log("------onStartCommand------------");
        return super.onStartCommand(intent, flags, startId);
    }
}
