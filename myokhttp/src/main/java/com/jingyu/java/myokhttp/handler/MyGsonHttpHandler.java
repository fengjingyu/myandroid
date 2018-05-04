package com.jingyu.java.myokhttp.handler;

import com.jingyu.java.myokhttp.req.MyReqInfo;
import com.jingyu.java.myokhttp.resp.MyRespInfo;
import com.jingyu.java.mytool.lib.gson.GsonUtil;

import java.io.InputStream;

/**
 * @author fengjingyu@foxmail.com
 * @description gson解析的http回调类
 */
public class MyGsonHttpHandler<T> extends MyBaseHttpHandler<T> {

    private Class<T> clazz;

    public MyGsonHttpHandler(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T onParse(MyReqInfo myReqInfo, MyRespInfo myRespInfo, InputStream inputStream, long totalSize) {
        return GsonUtil.parseJson(parse(myReqInfo, myRespInfo, inputStream, totalSize), clazz);
    }
}
