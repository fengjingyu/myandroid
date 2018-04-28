package com.jingyu.java.myokhttp.handler;

import com.jingyu.java.myokhttp.MyHttpCallBack;
import com.jingyu.java.myokhttp.log.LogUtil;
import com.jingyu.java.myokhttp.req.MyReqInfo;
import com.jingyu.java.myokhttp.resp.MyRespInfo;

import java.io.InputStream;

/**
 * @author fengjingyu@foxmail.com
 */
public class MyStringHttpHandler extends MyBaseHttpHandler<String> {

    public MyStringHttpHandler() {
    }

    @Override
    public String onParse(MyReqInfo myReqInfo, MyRespInfo myRespInfo, InputStream inputStream, long totalSize) {
        String data = parse2String(myReqInfo, myRespInfo, inputStream);
        LogUtil.i(MyHttpCallBack.TAG_HTTP, data);
        return data;
    }
}
