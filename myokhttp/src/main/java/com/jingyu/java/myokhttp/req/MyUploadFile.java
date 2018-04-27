package com.jingyu.java.myokhttp.req;

import com.jingyu.java.myokhttp.HttpConstants;

import java.io.File;

import okhttp3.MediaType;

/**
 * @author fengjingyu@foxmail.com
 */
public class MyUploadFile {

    private File file;

    public MyUploadFile(File file) {
        this.file = file;
    }

    public String getName() {
        if (isExist()) {
            return file.getName();
        } else {
            return "file not exist " + System.currentTimeMillis();
        }
    }

    public File getFile() {
        return file;
    }

    public MediaType getMediaType() {
        if (isExist()) {
            return getFileContentType();
        } else {
            return null;
        }
    }

    private MediaType getFileContentType() {
        return MediaType.parse(HttpConstants.OCTET_STREAM);
    }

    public long length() {
        if (isExist()) {
            return file.length();
        } else {
            return 0;
        }
    }

    public boolean isExist() {
        return file != null && file.exists();
    }

}