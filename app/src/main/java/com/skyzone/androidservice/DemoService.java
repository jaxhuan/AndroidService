package com.skyzone.androidservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Skyzone on 2/20/2017.
 */

public class DemoService extends Service {

    public static final String TAG = DemoService.class.getSimpleName();

    private MyBinder mBinder = new MyBinder();

    @Override
    public void onCreate() {
        System.out.println("onCreate:" + TAG);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("onStartCommand:" + TAG);
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("onBind:" + TAG);
        return mBinder;
    }

    @Override
    public void onDestroy() {
        System.out.println("onDestroy:" + TAG);
        super.onDestroy();
    }

    class MyBinder extends Binder {

        public void startDownload() {
            System.out.println("service start download in Thread:" + Thread
                    .currentThread());
        }
    }
}
