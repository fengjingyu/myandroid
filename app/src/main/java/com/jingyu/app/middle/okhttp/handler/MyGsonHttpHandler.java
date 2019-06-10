package com.jingyu.app.middle.okhttp.handler;

import android.app.Activity;

import com.jingyu.app.tool.GsonUtil;
import com.jingyu.java.myhttp.req.ReqInfo;
import com.jingyu.java.myhttp.resp.RespInfo;

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

    public MyGsonHttpHandler(Activity activity, Class<T> clazz) {
        super(activity);
        this.clazz = clazz;
    }

    public MyGsonHttpHandler(Activity activityContext, boolean isShowDialog, Class<T> clazz) {
        super(activityContext, isShowDialog);
        this.clazz = clazz;
    }

    @Override
    public T onParse(ReqInfo myReqInfo, RespInfo myRespInfo, InputStream inputStream, long totalSize) {
        return GsonUtil.parseJson(parse(myReqInfo, myRespInfo, inputStream, totalSize), clazz);
    }
}
