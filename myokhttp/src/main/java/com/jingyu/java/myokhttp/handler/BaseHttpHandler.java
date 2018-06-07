package com.jingyu.java.myokhttp.handler;

import com.jingyu.java.myokhttp.req.ReqInfo;
import com.jingyu.java.myokhttp.resp.RespInfo;

/**
 * @author fengjingyu@foxmail.com
 */
public abstract class BaseHttpHandler<T> implements IHttpHandler<T> {

    public BaseHttpHandler() {
    }

    //TODO 可以统一添加请求头、对参数加密
    @Override
    public ReqInfo onReadySendRequest(ReqInfo reqInfo) {
        return reqInfo;
    }

    @Override
    public void onUploadProgress(long bytesWritten, long totalSize) {

    }

    @Override
    public void onParseException(ReqInfo reqInfo, RespInfo respInfo) {

    }

    //TODO 实际项目里需要比对接口业务的状态码; 如果请求一般的浏览网页就无需匹配状态码，默认返回true
    @Override
    public boolean onMatchAppCode(ReqInfo reqInfo, RespInfo respInfo, T resultBean) {
        return true;
    }

    @Override
    public void onAppCodeException(ReqInfo reqInfo, RespInfo respInfo, T resultBean) {

    }

    @Override
    public void onSuccess(ReqInfo reqInfo, RespInfo respInfo, T resultBean) {

    }

    @Override
    public void onFailure(ReqInfo reqInfo, RespInfo respInfo) {

    }

    @Override
    public void onFinally(ReqInfo reqInfo, RespInfo respInfo) {

    }
}
