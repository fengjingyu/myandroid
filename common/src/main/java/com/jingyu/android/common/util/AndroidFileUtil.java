package com.jingyu.android.common.util;

import android.content.Context;
import android.os.Environment;

import com.jingyu.java.mytool.file.FileCreater;
import java.io.File;

/**
 * @author fengjingyu@foxmail.com
 */
public class AndroidFileUtil {

    public static boolean isSDcardExist() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static boolean isFileAvaliable(File file) {
        return file != null && file.exists();
    }

    private static boolean isStringAvaliable(String str) {
        return str != null && str.trim().length() > 0;
    }

    /**
     * 外部存储--之android文件夹,会随app的删除而删除
     * storage/emulated/0/Android/data/package/files or cache
     * storage/sdcard/Android/data/package/files or cache
     */
    public static class ExternalAndroid {

        /**
         * @return fileName 为 null /"" /" ",返回null
         * fileName不为空:
         * dirName 为 null /"" /"  " 则在Android/data/package/创建文件
         * dirName 为"aa" 则在Android/data/package/aa/创建文件
         * dirName 为"aa/bb" 则在Android/data/package/aa/bb创建文件
         */
        public static File getFile(Context context, String dirName, String fileName) {
            return FileCreater.createFile(getDir(context, dirName), fileName);
        }

        /**
         * Android/data/package/cache/fileName
         */
        public static File getCacheFile(Context context, String fileName) {
            return FileCreater.createFile(getCacheDir(context), fileName);
        }

        /**
         * Android/data/package/files/fileName
         */
        public static File getFilesFile(Context context, String fileName) {
            return FileCreater.createFile(getFilesDir(context), fileName);
        }

        /**
         * @return 如果sd卡可用
         * dirName=null / "" / "  ",返回Android/data/package/
         * dirName="cc" 返回Android/data/package/cc
         * dirName="aa/bb" 返回Android/data/package/aa/bb
         */
        public static File getDir(Context context, String dirName) {
            if (isSDcardExist()) {
                File externalCacheDir = context.getExternalCacheDir();
                if (externalCacheDir != null) {
                    File packageDir = externalCacheDir.getParentFile();
                    if (packageDir != null) {
                        if (isStringAvaliable(dirName)) {
                            return FileCreater.createDir(packageDir.getAbsolutePath() + File.separator + dirName);
                        } else {
                            return packageDir;
                        }
                    }
                }
            }
            return null;
        }

        /**
         * @return Android/data/package/cache
         */
        public static File getCacheDir(Context context) {
            if (isSDcardExist()) {
                return context.getExternalCacheDir();
            }
            return null;
        }

        /**
         * @return Android/data/package/files
         */
        public static File getFilesDir(Context context) {
            return getFilesDir(context, "");
        }

        /**
         * @param dirName "aa","aa/bb"
         * @return Android/data/package/files/aa/bb
         */
        public static File getFilesDir(Context context, String dirName) {
            if (isSDcardExist()) {
                return context.getExternalFilesDir(dirName);
            }
            return null;
        }

//        /**
//         * @return Android/data/package/files/DCIM
//         */
//        public static File getDCIMDir(Context context) {
//            if (isSDcardExist()) {
//                return context.getExternalFilesDir(Environment.DIRECTORY_DCIM);
//            }
//            return null;
//        }

    }

    /**
     * 外部存储--之公共目录,不会随app的删除而删除
     * /storage/emulated/0/
     * /storage/sdcard/
     */
    public static class ExternalPublic {
        /**
         * @return fileName 为 null /"" /" ",返回null
         * fileName不为空:
         * dirName 为 null /"" /"  " 则在/storage/sdcard/创建文件
         * dirName 为"aa" 则在/storage/sdcard/aa创建文件
         * dirName 为"aa/bb" 则在/storage/sdcard/aa/bb创建文件
         */
        public static File getFile(String dirName, String fileName) {
            return FileCreater.createFile(getDir(dirName), fileName);
        }

        /**
         * @return /storage/sdcard/
         */
        public static File getDir() {
            if (isSDcardExist()) {
                return Environment.getExternalStorageDirectory();
            }
            return null;
        }

        /**
         * @param dirName "aa" ,"aa/bb"
         * @return /storage/sdcard/aa/bb
         */
        public static File getDir(String dirName) {
            if (isSDcardExist()) {
                File dir = Environment.getExternalStoragePublicDirectory(dirName);
                // 不会自动创建的
                return FileCreater.createDir(dir);
            }
            return null;
        }
    }

    /**
     * 内部存储-会随app的删除而删除
     */
    public static class Internal {

        /**
         * @return fileName 为 null /"" /" ",返回null
         * fileName不为空:
         * dirName 为 null /"" /"  " 则在data/data/package/创建文件
         * dirName 为"aa" 则在data/data/package/aa创建文件
         * dirName 为"aa/bb" 则在data/data/package/aa/bb创建文件
         */
        public static File getFile(Context context, String dirName, String fileName) {
            return FileCreater.createFile(getDir(context, dirName), fileName);
        }

        /**
         * data/data/package/cache/fileName
         */
        public static File getCacheFile(Context context, String fileName) {
            return FileCreater.createFile(getCacheDir(context), fileName);
        }

        /**
         * data/data//package/files/fileName
         */
        public static File getFilesFile(Context context, String fileName) {
            return FileCreater.createFile(getFilesDir(context), fileName);
        }

        /**
         * @return dirName=null / "" / "  ",返回/data/data/package/
         * dirName="cc" 返回/data/data/package/cc
         * dirName="aa/bb" 返回/data/data/package/aa/bb
         */
        public static File getDir(Context context, String dirName) {
            File cacheDir = context.getCacheDir();
            if (cacheDir != null) {
                File packageDir = cacheDir.getParentFile();
                if (packageDir != null) {
                    if (isStringAvaliable(dirName)) {
                        return FileCreater.createDir(packageDir.getAbsolutePath() + File.separator + dirName);
                    } else {
                        return packageDir;
                    }
                }
            }
            return null;
        }

//        /**
//         * @param dirName 这个系统api只提供"aa" 或"bb"的目录
//         *                ,不可以是"aa/bb"带有分隔符的,否则非法参数异常
//         * @return dirName为"" ,则返回/data/data/package/app_ ,这个app_是系统创建目录的时候自带的
//         * dirName为null ,则返回/data/data/package/app_null,这个app_是系统创建目录的时候自带的
//         * dirName非空,则返回/data/data/package/app_+dirName,这个app_是系统创建目录的时候自带的
//         */
//        public static File getAppDir(Context context, String dirName) {
//            return context.getDir(dirName, Context.MODE_PRIVATE);
//        }

        /**
         * @return /data/data/package/cache
         */
        public static File getCacheDir(Context context) {
            return context.getCacheDir();
        }

        /**
         * @return /data/data/package/files
         */
        public static File getFilesDir(Context context) {
            return context.getFilesDir();
        }

//        /**
//         * @return /system
//         */
//        public static File getSystemDir() {
//            return Environment.getRootDirectory();
//        }

//        /**
//         * @return /data
//         */
//        public static File getDataDir() {
//            return Environment.getDataDirectory();
//        }

//        /**
//         * @return /cache
//         */
//        public static File getCacheDir() {
//            return Environment.getDownloadCacheDirectory();
//        }
    }

    /**
     * 先在ExternalAndroid取Android/data/package/dirName,如果sd卡不存在
     * 再到Internal取data/data/package/dirName
     *
     * @param dirName "aa" , "aa/bb"
     */
    public static File getAndroidDir(Context context, String dirName) {
        File dir = ExternalAndroid.getDir(context, dirName);
        if (dir != null) {
            return dir;
        }
        return Internal.getDir(context, dirName);
    }

    public static File getAndroidFile(Context context, String dirName, String fileName) {
        if (isStringAvaliable(fileName)) {
            File file = ExternalAndroid.getFile(context, dirName, fileName);
            if (file != null) {
                return file;
            }
            return Internal.getFile(context, dirName, fileName);
        }
        return null;
    }

}
