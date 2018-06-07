package com.jingyu.java.mytool.tool;

import java.io.File;

/**
 * @author fengjingyu@foxmail.com
 */
public class DirDeleter {

    public interface DeleteListener {

        /**
         * 即将要删除的文件
         * <p>
         * 是否删除这个文件，true删除(即removing()返回true后，该类执行file.delete的代码)，false不删除
         */
        boolean onDeleting(File file);

        void onFinish();
    }

    private DeleteListener deleteListener;

    private boolean isDeleteRootDir;

    public DirDeleter(boolean isDeleteRootDir, DeleteListener deleteListener) {
        this.isDeleteRootDir = isDeleteRootDir;
        this.deleteListener = deleteListener;
    }

    private void deleteDir(File dir) {
        if (dir != null && dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                // 如果目录是系统级文件夹，java没有访问权限，那么会返回null数组
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDir(file);
                    } else {
                        if (deleteListener == null || deleteListener.onDeleting(file)) {
                            file.delete();
                        }
                    }
                }
            }
            if (isDeleteRootDir) {
                dir.delete();
            }
        }
    }

    public void start(File file) {
        // 如果文件不存在
        if (!file.exists()) {
            return;
        }

        if (file.isDirectory()) {
            deleteDir(file);
        } else {
            if (deleteListener == null || deleteListener.onDeleting(file)) {
                file.delete();
            }
        }
        if (deleteListener != null) {
            deleteListener.onFinish();
        }
    }
}
