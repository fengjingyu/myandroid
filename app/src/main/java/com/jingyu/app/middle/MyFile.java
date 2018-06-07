package com.jingyu.app.middle;

import android.content.Context;

import com.jingyu.android.common.util.AndroidFileUtil;
import com.jingyu.java.mytool.util.FileUtil;
import java.io.File;

/**
 * @author fengjingyu@foxmail.com
 *         所有文件在这里管理
 */
public class MyFile {

    // photo目录名
    private final static String PHOTO_DIR_NAME = "photo";
    // log目录名
    private final static String LOG_DIR_NAME = "log";
    // crash目录名
    private final static String CRASH_DIR_NAME = "crash";

    /**
     * app目录
     */
    public static File getAppDir(Context context) {
        return AndroidFileUtil.getAndroidDir(context, MyEnv.getAppDirName());
    }

    /**
     * app目录内创建文件
     */
    public static File getFileInAppDir(Context context, String filename) {
        return FileUtil.createFile(getAppDir(context), filename);
    }

    /**
     * 图片目录
     */
    public static File getPhotoDir(Context context) {
        return AndroidFileUtil.getAndroidDir(context, MyEnv.getAppDirName() + File.separator + PHOTO_DIR_NAME);
    }

    /**
     * photo目录内创建文件
     */
    public static File getFileInPhotoDir(Context context, String filename) {
        return FileUtil.createFile(getPhotoDir(context), filename);
    }

    /**
     * 日志目录
     */
    public static File getLogDir(Context context) {
        return AndroidFileUtil.getAndroidDir(context, MyEnv.getAppDirName() + File.separator + LOG_DIR_NAME);
    }

    /**
     * crash目录
     */
    public static File getCrashDir(Context context) {
        return AndroidFileUtil.getAndroidDir(context, MyEnv.getAppDirName() + File.separator + CRASH_DIR_NAME);
    }

}
