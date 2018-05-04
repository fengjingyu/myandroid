package com.jingyu.java.myokhttp.handler;

import com.jingyu.java.myokhttp.req.MyReqInfo;
import com.jingyu.java.myokhttp.resp.MyRespInfo;

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
        return parse(myReqInfo, myRespInfo, inputStream, totalSize, file);
    }
}
