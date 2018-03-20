package com.jingyu.java.myokhttp.handler;

import com.jingyu.java.myokhttp.req.MyReqInfo;
import com.jingyu.java.myokhttp.resp.MyRespInfo;
import com.jingyu.java.mytool.lib.gson.GsonUtil;

/**
 * @author fengjingyu@foxmail.com
 * @description gson解析的http回调类
 */
public class MyGsonHttpHandler<T> extends MyBaseHttpHandler<T> {

    private Class<T> clazz;

    public MyGsonHttpHandler(Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     * 该方法是在子线程中的，解析失败返回null
     */
    @Override
    public T onParse(MyReqInfo myReqInfo, MyRespInfo myRespInfo) {
        return GsonUtil.parseJson(myRespInfo.getDataString(), clazz);
    }
}
