package com.jingyu.app.middle.okhttp.handler;

import android.app.Activity;

import com.jingyu.app.tool.GsonUtil;
import com.jingyu.java.myokhttp.req.MyReqInfo;
import com.jingyu.java.myokhttp.resp.MyRespInfo;

import java.io.InputStream;


/**
 * @author fengjingyu@foxmail.com
 * @description gson解析的http回调类
 */
public class GsonHttpHandler<T> extends BaseHttpHandler<T> {

    private Class<T> clazz;

    public GsonHttpHandler(Class<T> clazz) {
        this.clazz = clazz;
    }

    public GsonHttpHandler(Activity activity, Class<T> clazz) {
        super(activity);
        this.clazz = clazz;
    }

    public GsonHttpHandler(Activity activityContext, boolean isShowDialog, Class<T> clazz) {
        super(activityContext, isShowDialog);
        this.clazz = clazz;
    }

    @Override
    public T onParse(MyReqInfo myReqInfo, MyRespInfo myRespInfo, InputStream inputStream, long totalSize) {
        return GsonUtil.parseJson(parse(myReqInfo, myRespInfo, inputStream, totalSize), clazz);
    }
}
