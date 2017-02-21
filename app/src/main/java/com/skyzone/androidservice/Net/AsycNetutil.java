package com.skyzone.androidservice.Net;

import android.graphics.Bitmap;
import android.os.Handler;

/**
 * Created by Skyzone on 2/20/2017.
 */

public class AsycNetutil {

    public interface CallBack {
        void onResponse(String response);

        void onImageLoad(Bitmap bitmap);
    }

    public static void post(final String url, final String content, final CallBack callBack) {
        final Handler handler = new Handler();
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        final String result = NetUtil.post(url, content);
                        handler.post(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        callBack.onResponse(result);
                                    }
                                }
                        );
                    }
                }
        ).start();
    }

    public static void get(final String url, final CallBack callBack) {
        final Handler handler = new Handler();   //handler在哪个线程创建，则post到哪个线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String response = NetUtil.get(url);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onResponse(response);
                    }
                });
            }
        }).start();
    }

    public static void loadImage(final String url, final CallBack callBack) {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = NetUtil.loadImage(url);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onImageLoad(bitmap);
                    }
                });
            }
        }).start();
    }
}
