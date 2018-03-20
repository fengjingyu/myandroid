package com.jingyu.java.mytool.util;

import java.io.Closeable;

/**
 * @author fengjingyu@foxmail.com
 *
 */
public class CloseUtil {

    private CloseUtil() {
    }

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
