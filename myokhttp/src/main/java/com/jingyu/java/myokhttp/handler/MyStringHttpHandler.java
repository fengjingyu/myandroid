package com.jingyu.java.myokhttp.handler;

import com.jingyu.java.myokhttp.req.MyReqInfo;
import com.jingyu.java.myokhttp.resp.MyRespInfo;

/**
 * @author fengjingyu@foxmail.com
 * @description gson解析的http回调类
 */
public class MyStringHttpHandler extends MyBaseHttpHandler<String> {

    public MyStringHttpHandler() {
    }

    /**
     * 该方法是在子线程中的，解析失败返回null
     */
    @Override
    public String onParse(MyReqInfo myReqInfo, MyRespInfo myRespInfo) {
        return myRespInfo.getDataString();
    }
}
