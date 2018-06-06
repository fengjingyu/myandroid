package com.jingyu.java.myokhttp.handler;

import com.jingyu.java.myokhttp.MyHttpCallBack;
import com.jingyu.java.myokhttp.log.LogUtil;
import com.jingyu.java.myokhttp.req.MyReqInfo;
import com.jingyu.java.myokhttp.resp.MyRespInfo;
import com.jingyu.java.mytool.util.IOUtil;

import java.io.File;
import java.io.InputStream;

/**
 * @author fengjingyu@foxmail.com
 */
public class MyFileHttpHandler extends MyBaseHttpHandler<File> {

    private File file;

    public MyFileHttpHandler(File file) {
        this.file = file;
    }

    @Override
    public File onParse(MyReqInfo myReqInfo, MyRespInfo myRespInfo, InputStream inputStream, long totalSize) {
        if (IOUtil.inputStream2File(inputStream, file)) {
            LogUtil.i(MyHttpCallBack.TAG_HTTP, "parse()::下载文件成功file = " + file.getAbsolutePath());
            return file;
        } else {
            throw new RuntimeException("parse()::下载文件异常file = " + file + MyHttpCallBack.LINE + myReqInfo.getUrl());
        }
    }
}
