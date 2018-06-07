package com.jingyu.java.mytool.util;

import java.io.File;

/**
 * @author fengjingyu@foxmail.com
 */
public class FileUtil {

    public static final String LINE_SEPARATOR = System.getProperty("line.separator");// （windows:"\r\n"；unix:"\n"）； System.out.println()
    //public static final String PATH_SEPARATOR = System.getProperty("path.separator");// （windows:";"；unix:":"）；File.pathSeparator 环境变量
    //public static final String FILE_SEPARATOR = System.getProperty("file.separator");// （windows:"\"；unix:"/"）；File.separator  盘符

    public static boolean isFileExist(File file) {
        return file != null && file.exists();
    }

    /**
     * @return fail 返回null
     * success 返回 file
     */
    public static File createDir(File dir) {
        if (dir != null) {
            if (dir.exists() || dir.mkdirs()) {
                return dir;
            }
        }
        return null;
    }

    /**
     * "e:/haha/enen.o/hexx...we/android.txt" 这创建出来的是文件夹
     *
     * @return fail 返回null
     * success 返回 file
     */
    public static File createDir(String dirAbsolutePath) {
        if (StringUtil.isNotBlank(dirAbsolutePath)) {
            return createDir(new File(dirAbsolutePath));
        }
        return null;
    }

    public static File createFile(String path) {
        return createFile(new File(path));
    }

    /**
     * @return fail 返回null
     * success 返回 file
     */
    public static File createFile(File file) {
        try {
            if (file != null) {
                if (file.exists() || file.createNewFile()) {
                    return file;
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @return fail 返回null
     * success 返回 file
     */
    public static File createFile(String dirAbsolutePath, String fileName) {
        if (StringUtil.isNotBlank(dirAbsolutePath) && StringUtil.isNotBlank(fileName)) {
            return createFile(new File(dirAbsolutePath + File.separator + fileName));
        }
        return null;
    }

    /**
     * @return fail 返回 null
     * success 返回 file
     */
    public static File createFile(File dir, String fileName) {
        if (dir != null && StringUtil.isNotBlank(fileName)) {
            return createFile(new File(dir, fileName));
        }
        return null;
    }

    /**
     * @return fail 返回null
     * success 返回 file
     */
    public static File deleteAndCreateFile(File file) {
        try {
            if (file != null) {
                if (!file.exists() || file.delete()) {
                    // 文件不存在 或 文件存在且删除成功
                    if (file.createNewFile()) {
                        //重新创建成功
                        return file;
                    }
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static File deleteAndCreateFile(File dir, String fileName) {
        if (dir != null && StringUtil.isNotBlank(fileName)) {
            return deleteAndCreateFile(new File(dir, fileName));
        }
        return null;
    }

    public static long getFileSize(String filePath) {
        long size = 0;
        File file = new File(filePath);
        if (file.exists()) {
            size = file.length();
        }
        return size;
    }
}
