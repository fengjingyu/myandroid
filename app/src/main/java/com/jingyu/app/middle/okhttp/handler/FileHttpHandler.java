package com.jingyu.app.middle.okhttp.handler;

import com.jingyu.java.myokhttp.MyHttpCallBack;
import com.jingyu.java.myokhttp.req.MyReqInfo;
import com.jingyu.java.myokhttp.resp.MyRespInfo;
import com.jingyu.java.mytool.util.IOUtil;

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
    public boolean onMatchAppCode(MyReqInfo myReqInfo, MyRespInfo myRespInfo, File resultBean) {
        return true;
    }

    @Override
    public File onParse(MyReqInfo myReqInfo, MyRespInfo myRespInfo, InputStream inputStream, long totalSize) {
        if (IOUtil.inputStream2File(inputStream, file)) {
            return file;
        } else {
            throw new RuntimeException("parse()::下载文件异常file = " + file + MyHttpCallBack.LINE + myReqInfo.getUrl());
        }
    }
}
