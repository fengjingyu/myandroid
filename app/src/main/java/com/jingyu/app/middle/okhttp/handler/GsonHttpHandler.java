package com.jingyu.app.middle.okhttp.handler;

import android.app.Activity;

import com.jingyu.app.tool.GsonUtil;
import com.jingyu.java.myokhttp.req.MyReqInfo;
import com.jingyu.java.myokhttp.resp.MyRespInfo;


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

    /**
     * 该方法是在子线程中的，解析失败返回null
     */
    @Override
    public T onParse(MyReqInfo myReqInfo, MyRespInfo myRespInfo) {
        super.onParse(myReqInfo, myRespInfo);
        return GsonUtil.parseJson(myRespInfo.getDataString(), clazz);
    }
}
