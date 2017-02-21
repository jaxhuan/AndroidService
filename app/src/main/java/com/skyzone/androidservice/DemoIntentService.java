package com.skyzone.androidservice;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Skyzone on 2/20/2017.
 */

public class DemoIntentService extends IntentService {

    public static final String TAG = DemoIntentService.class.getSimpleName();

    public DemoIntentService() {
        super(TAG);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("DemoIntentService is started");
        //这里可以做一些想做的事
        return super.onStartCommand(intent, flags, startId);  //必须返回这个
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        System.out.println("we are running sth paste time.in thread:" + Thread.currentThread());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void onDestroy() {
        System.out.println("paste time task is over");
        super.onDestroy();
    }
}
