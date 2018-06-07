package com.jingyu.app.middle.okhttp.handler;

import com.jingyu.app.middle.okhttp.control.MyHttpCallBack;
import com.jingyu.java.myokhttp.req.ReqInfo;
import com.jingyu.java.myokhttp.resp.RespInfo;
import com.jingyu.java.mytool.util.IoUtil;

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
    public boolean onMatchAppCode(ReqInfo reqInfo, RespInfo respInfo, File resultBean) {
        return true;
    }

    @Override
    public File onParse(ReqInfo reqInfo, RespInfo respInfo, InputStream inputStream, long totalSize) {
        if (IoUtil.inputStream2File(inputStream, file)) {
            return file;
        } else {
            throw new RuntimeException("parse()::下载文件异常file = " + file + MyHttpCallBack.LINE + reqInfo.getUrl());
        }
    }
}
