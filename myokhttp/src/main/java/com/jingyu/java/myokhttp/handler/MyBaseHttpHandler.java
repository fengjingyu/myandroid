package com.jingyu.java.myokhttp.handler;

import com.jingyu.java.myokhttp.req.MyReqInfo;
import com.jingyu.java.myokhttp.resp.MyRespInfo;

/**
 * @author fengjingyu@foxmail.com
 */
public abstract class MyBaseHttpHandler<T> implements IMyHttpHandler<T> {

    public MyBaseHttpHandler() {
    }

    //TODO 可以统一添加请求头、对参数加密
    @Override
    public MyReqInfo onReadySendRequest(MyReqInfo myReqInfo) {
        return myReqInfo;
    }

    @Override
    public void onUploadProgress(long bytesWritten, long totalSize) {

    }

    @Override
    public void onParseException(MyReqInfo myReqInfo, MyRespInfo myRespInfo) {

    }

    //TODO 实际项目里需要比对接口业务的状态码; 如果请求一般的浏览网页就无需匹配状态码，默认返回true
    @Override
    public boolean onMatchAppCode(MyReqInfo myReqInfo, MyRespInfo myRespInfo, T resultBean) {
        return true;
    }

    @Override
    public void onAppCodeException(MyReqInfo myReqInfo, MyRespInfo myRespInfo, T resultBean) {

    }

    @Override
    public void onSuccess(MyReqInfo myReqInfo, MyRespInfo myRespInfo, T resultBean) {

    }

    @Override
    public void onFailure(MyReqInfo myReqInfo, MyRespInfo myRespInfo) {

    }

    @Override
    public void onFinally(MyReqInfo myReqInfo, MyRespInfo myRespInfo) {

    }
}
