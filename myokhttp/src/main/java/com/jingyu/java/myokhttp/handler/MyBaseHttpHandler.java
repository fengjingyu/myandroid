package com.jingyu.java.myokhttp.handler;

import com.jingyu.java.myokhttp.req.MyReqInfo;
import com.jingyu.java.myokhttp.resp.MyRespInfo;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fengjingyu@foxmail.com
 */
public abstract class MyBaseHttpHandler<T> implements IMyHttpHandler<T> {

    public MyBaseHttpHandler() {
    }

    //TODO 统一配置请求头
    public Map<String, List<String>> getCommonHeaders(String tag, Map<String, List<String>> headers) {
        if (headers == null) {
            headers = new HashMap<>();
        }
        //headers.put("_p", Arrays.asList("1"));
        return headers;
    }

    //TODO 统一配置加密参数
    public Map<String, Object> getCommonEncryptParams(String tag, Map<String, Object> paramsMap) {
        if (paramsMap == null) {
            paramsMap = new HashMap<>();
        }
        //paramsMap.put("testKey0", "java");
        //paramsMap.put("testKey3", "c");
        //paramsMap.put("testKey6", "c++");
        return paramsMap;
    }

    @Override
    public MyReqInfo onReadySendRequest(MyReqInfo myReqInfo) {
        Map<String, List<String>> newHeaders = getCommonHeaders(myReqInfo.getTag().toString(), myReqInfo.getHeadersMap());
        Map<String, Object> newParams = getCommonEncryptParams(myReqInfo.getTag().toString(), myReqInfo.getParamsMap());

        MyReqInfo newMyReqInfo = new MyReqInfo.Builder(myReqInfo).headersMap(newHeaders).paramsMap(newParams).builder();
        System.out.println("请求参数：" + newMyReqInfo);
        return newMyReqInfo;
    }

    //TODO 实际项目里需要比对接口业务的状态码; 如果请求一般的浏览网页就无需匹配状态码，默认返回true
    @Override
    public boolean onMatchAppCode(MyReqInfo myReqInfo, MyRespInfo myRespInfo, T resultBean) {
        return true;
    }

    @Override
    public void onUploadProgress(long bytesWritten, long totalSize) {

    }

    @Override
    public void onSuccessForDownload(MyReqInfo myReqInfo, MyRespInfo myRespInfo, InputStream inputStream, long totalSize) {

    }

    @Override
    public void onParseException(MyReqInfo myReqInfo, MyRespInfo myRespInfo) {

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
