package com.skyzone.androidservice.Net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;

import com.skyzone.androidservice.App;
import com.skyzone.androidservice.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Skyzone on 2/21/2017.
 */

public enum BitmapCache {

    Instance;

    private LruCache<String, Bitmap> mBitmapLruCache;
    public static final String CACHE_IMG = "/cache_img";
    public static final String CACHE_FILE_TYPE = ".cache";

    BitmapCache() {
        //create a image cache,max size is maxMemory/8(this unit must same as sizeof's unit)
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 8);
        mBitmapLruCache = new LruCache<String, Bitmap>(maxMemory) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    public boolean hasCache(String url) {
        return null != mBitmapLruCache.get(url);
    }

    public boolean hasCacheDisk(String url) {
        File file = new File(App.mContext.getFilesDir().getPath() + CACHE_IMG + "/" + convertUrl2Name(url));
        if (null == file || !file.exists())
            return false;
        else
            return true;
    }

    public Bitmap getCache(String url) {
        return mBitmapLruCache.get(url);
    }

    public Bitmap getCacheDisk(String url) {
        return BitmapFactory.decodeFile(App.mContext.getFilesDir().getPath() + CACHE_IMG + "/" + convertUrl2Name(url));
    }

    public void putCache(String url, Bitmap bitmap) {
        if (!hasCache(url))
            mBitmapLruCache.put(url, bitmap);
    }

    public void puDiskCache(String url, Bitmap bitmap) {
        if (hasCacheDisk(url))
            return;
        File file = FileUtils.createFile(App.mContext.getFilesDir().getPath() + CACHE_IMG, convertUrl2Name(url));
        if (null == file)
            System.out.println("create file is failed.");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("put disk is successful.now disk cache size is: " + FileUtils.getFolderSize(App.mContext.getFilesDir().getPath() + CACHE_IMG));
    }

    public String convertUrl2Name(String url) {
        return url.hashCode() + CACHE_FILE_TYPE;
    }
}
