package com.jingyu.java.myokhttp.handler;

import com.jingyu.java.myokhttp.req.MyReqInfo;
import com.jingyu.java.myokhttp.resp.MyRespInfo;

import java.io.InputStream;

/**
 * @author fengjingyu@foxmail.com
 */
public class MyStringHttpHandler extends MyBaseHttpHandler<String> {

    @Override
    public String onParse(MyReqInfo myReqInfo, MyRespInfo myRespInfo, InputStream inputStream, long totalSize) {
        return parse(myReqInfo, myRespInfo, inputStream, totalSize);
    }
}
