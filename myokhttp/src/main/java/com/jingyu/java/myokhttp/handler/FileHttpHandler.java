package com.jingyu.java.myokhttp.handler;

import com.jingyu.java.myokhttp.HttpCallBack;
import com.jingyu.java.myokhttp.log.LogUtil;
import com.jingyu.java.myokhttp.req.ReqInfo;
import com.jingyu.java.myokhttp.resp.RespInfo;
import com.jingyu.java.mytool.util.IoUtil;

import java.io.File;
import java.io.InputStream;

/**
 * @author fengjingyu@foxmail.com
 */
public class FileHttpHandler extends BaseHttpHandler<File> {

    private File file;

    public FileHttpHandler(File file) {
        this.file = file;
    }

    @Override
    public File onParse(ReqInfo reqInfo, RespInfo respInfo, InputStream inputStream, long totalSize) {
        if (IoUtil.inputStream2File(inputStream, file)) {
            LogUtil.i(HttpCallBack.TAG_HTTP, "parse()::下载文件成功file = " + file.getAbsolutePath());
            return file;
        } else {
            throw new RuntimeException("parse()::下载文件异常file = " + file + HttpCallBack.LINE + reqInfo.getUrl());
        }
    }
}
