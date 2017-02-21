package com.skyzone.androidservice;

import java.io.File;

/**
 * Created by Skyzone on 1/19/2017.
 */

public class FileUtils {

    /**
     * delete file or directory
     * <ul>
     * <li>if path is null or empty, return true</li>
     * <li>if path not exist, return true</li>
     * <li>if path exist, delete recursion. return true</li>
     * <ul>
     *
     * @param path
     * @return
     */
    public static boolean deleteFile(String path) {
        try {
            if (StringUtils.isBlank(path)) {
                return true;
            }

            File file = new File(path);
            if (!file.exists()) {
                return true;
            }
            if (file.isFile()) {
                final File to = new File(file.getAbsolutePath() + System.currentTimeMillis());  //safer
                file.renameTo(to);
                return to.delete();
            }
            if (!file.isDirectory()) {
                return false;
            }
            for (File f : file.listFiles()) {
                if (f.isFile()) {
                    final File to = new File(f.getAbsolutePath() + System.currentTimeMillis());  //safer
                    f.renameTo(to);
                    to.delete();
                } else if (f.isDirectory()) {
                    deleteFile(f.getAbsolutePath());
                }
            }
            final File to = new File(file.getAbsolutePath() + System.currentTimeMillis());  //safer
            file.renameTo(to);
            return to.delete();
        } catch (Exception e) {
            return false;
        }
    }

    public static long getFolderSize(String path) {
        long size = 0;
        File file = new File(path);
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory())
                size = size + getFolderSize(files[i].getPath());
            else
                size = size + files[i].length();
        }
        return size;
    }

    /**
     * 创建文件夹
     */
    public static void createFolder(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        } else {
            if (!file.isDirectory()) {
                file.mkdirs();
            }
        }
    }

    public static File createFile(String path, String name) {
        createFolder(path);
        return new File(path + "/" + name);
    }
}
