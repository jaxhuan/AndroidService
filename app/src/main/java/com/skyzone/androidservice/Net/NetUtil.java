package com.skyzone.androidservice.Net;

import android.accounts.NetworkErrorException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Skyzone on 2/20/2017.
 */

public class NetUtil {


    public static String post(String url, String content) {
        HttpURLConnection conn = null;
        try {
            URL mUrl = new URL(url);
            conn = (HttpURLConnection) mUrl.openConnection();
            conn.setRequestMethod("POST");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);   //允许向服务器输出内容

            String data = content;
            //获取输出流，向服务器写数据
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(data.getBytes());
            outputStream.flush();
            outputStream.close();

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                InputStream inputStream = conn.getInputStream();
                String response = getStringFromInputStream(inputStream);
                return response;
            } else {
                throw new NetworkErrorException("response code is: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != conn)
                conn.disconnect();
        }
        return null;
    }

    public static String get(String url) {
        HttpURLConnection conn = null;
        try {
            URL mUrl = new URL(url);
            conn = (HttpURLConnection) mUrl.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                InputStream inputStream = conn.getInputStream();
                return getStringFromInputStream(inputStream);
            } else {
                throw new NetworkErrorException("response code is: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != conn)
                conn.disconnect();
        }
        return null;
    }

    public static Bitmap loadImage(String url) {
        HttpURLConnection conn = null;
        try {
            if (BitmapCache.Instance.hasCache(url)) {
                System.out.println("get image from cache");
                return BitmapCache.Instance.getCache(url);
            }
            if (BitmapCache.Instance.hasCacheDisk(url)) {
                System.out.println("get image from disk");
                final Bitmap bitmap = BitmapCache.Instance.getCacheDisk(url);
                BitmapCache.Instance.putCache(url, bitmap);
                return bitmap;
            }
            System.out.println("load image:" + url);
            URL mUrl = new URL(url);
            conn = (HttpURLConnection) mUrl.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                InputStream inputStream = conn.getInputStream();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);
                if (null == bitmap)
                    throw new NetworkErrorException("load image failed:" + url);
                BitmapCache.Instance.putCache(url, bitmap);
                BitmapCache.Instance.puDiskCache(url, bitmap);
                return bitmap;
            } else {
                throw new NetworkErrorException("code is: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != conn)
                conn.disconnect();
        }
        return null;
    }

    private static String getStringFromInputStream(InputStream is)
            throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        // 模板代码 必须熟练
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = is.read(buffer)) != -1) {
            os.write(buffer, 0, len);
        }
        is.close();
        String state = os.toString();// 把流中的数据转换成字符串,采用的编码是utf-8(模拟器默认编码)
        os.close();
        return state;
    }
}
