package com.jingyu.java.myokhttp.handler;

import com.jingyu.java.myokhttp.req.MyReqInfo;
import com.jingyu.java.myokhttp.resp.MyRespInfo;

import java.io.InputStream;

/**
 * @author fengjingyu@foxmail.com
 * http回调
 */
public interface IMyHttpHandler<T> {
    /**
     * 发送请求之前
     * <p>
     * 可以showdialog
     * 可以修改MyReqInfo（如添加请求头 加密等)
     */
    MyReqInfo onReadySendRequest(MyReqInfo myReqInfo);

    /**
     * 文件上传进度
     */
    void onUploadProgress(long bytesWritten, long totalSize);

    /**
     * 下载的回调,如果ReqInfo.isDownload是true,则回调 onSuccessForDownload/onFailure , onFinally
     * 如果是异步请求：则在异步的线程里回调
     */
    void onSuccessForDownload(MyReqInfo myReqInfo, MyRespInfo myRespInfo, InputStream inputStream, long totalSize);

    /**
     * 如果解析失败：一定得返回null,回调onParseException()
     * 如果解析成功: 继续回调onMatchAppCode()
     * 如果是异步请求：则在异步的线程里回调
     */
    T onParse(MyReqInfo myReqInfo, MyRespInfo myRespInfo);

    /**
     * 对返回状态码的一个判断，每个项目的认定操作成功的状态码或结构可能不同，在这里统一判断
     * <p>
     * 如果调用到这个方法，表示解析一定是成功的；如果解析失败，是不会调用这个方法的
     * false:回调onAppCodeException()
     * true: 回调onSuccess()
     * runOnSpecifiedThread(): 该方法里指定回调的线程
     */
    boolean onMatchAppCode(MyReqInfo myReqInfo, MyRespInfo myRespInfo, T resultBean);

    /**
     * http 请求成功，解析失败
     * runOnSpecifiedThread(): 该方法里指定回调的线程
     */
    void onParseException(MyReqInfo myReqInfo, MyRespInfo myRespInfo);

    /**
     * http 请求成功，解析成功，项目业务逻辑的状态码有误
     * runOnSpecifiedThread(): 该方法里指定回调的线程
     */
    void onAppCodeException(MyReqInfo myReqInfo, MyRespInfo myRespInfo, T resultBean);

    /**
     * http请求成功，解析成功，状态码成功，回调该方法
     * runOnSpecifiedThread(): 该方法里指定回调的线程
     */
    void onSuccess(MyReqInfo myReqInfo, MyRespInfo myRespInfo, T resultBean);

    /**
     * http请求失败，回调该方法
     * runOnSpecifiedThread(): 该方法里指定回调的线程
     */
    void onFailure(MyReqInfo myReqInfo, MyRespInfo myRespInfo);

    /**
     * onSuccess  或 onFailure 的逻辑调用完后都会回调该方法
     * <p>
     * 可以关闭dialog
     */
    void onFinally(MyReqInfo myReqInfo, MyRespInfo myRespInfo);

}
