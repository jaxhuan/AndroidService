package com.skyzone.androidservice;

import android.app.Application;
import android.content.Context;

/**
 * Created by Skyzone on 2/21/2017.
 */

public class App extends Application {

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }
}
