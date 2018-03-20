package com.jingyu.android.common.util;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;
import com.jingyu.java.mytool.util.IOUtil;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @author fengjingyu@foxmail.com
 */
public class AndroidIOUtil {

    @Nullable
    public static InputStream getRawInputStream(Context context, int rawId) {
        try {
            if (context == null || rawId < 0) {
                return null;
            }
            return context.getResources().openRawResource(rawId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    public static InputStream getAssertInputStream(Context context, String fileName) {
        try {
            if (context == null || fileName == null) {
                return null;
            }
            return context.getAssets().open(fileName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    public static InputStream getUriInputStream(Context context, Uri uri) {
        try {
            if (context == null || uri == null) {
                return null;
            }
            return context.getContentResolver().openInputStream(uri);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 从内部存的files文件夹里读取文件 即 /data/data/"PACKAGE_NAME"/files/filename
     *
     * @param fileName 文件名 如 "aa.txt" 不可以是"aa/bb.txt",这个openFileInput方法只提供了"aa.txt"方式,即不含文件分隔符
     */
    @Nullable
    public static String getInternalFilesString(Context context, String fileName) {
        try {
            FileInputStream fileInputStream = context.openFileInput(fileName);
            return IOUtil.getString(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }



}
