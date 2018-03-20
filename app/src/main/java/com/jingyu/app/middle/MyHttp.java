package com.jingyu.app.middle;

import com.jingyu.java.myokhttp.MyHttpClient;
import com.jingyu.java.myokhttp.MyHttpUtil;
import com.jingyu.java.myokhttp.handler.IMyHttpHandler;
import com.jingyu.java.myokhttp.req.MyReqInfo;
import com.jingyu.java.myokhttp.req.MyReqType;

import java.util.List;
import java.util.Map;

/**
 * @author fengjingyu@foxmail.com
 */
public class MyHttp {

    private MyHttp() {
    }

    private static MyHttpClient myHttpClient = new MyHttpClient();

    public static MyHttpClient getMyHttpClient() {
        return myHttpClient;
    }

    public static void setMyHttpClient(MyHttpClient myHttpClient) {
        MyHttp.myHttpClient = myHttpClient;
    }

    private static MyReqInfo createReqInfo(MyReqType type, String url, Map<String, Object> paramsMap, Map<String, List<String>> headers, String tag, boolean isDownload) {
        return new MyReqInfo.Builder()
                .myReqType(type)
                .url(url)
                .paramsMap(paramsMap)
                .headersMap(headers)
                .tag(tag)
                .isDownload(isDownload)
                .builder();
    }

    public static void getAsync(String url) {
        getMyHttpClient().httpAsync(createReqInfo(MyReqType.GET, url, null, null, null, false), null);
    }

    public static void getAsync(String url, IMyHttpHandler myHttpHandler) {
        getMyHttpClient().httpAsync(createReqInfo(MyReqType.GET, url, null, null, null, false), myHttpHandler);
    }

    public static void getAsync(String url, Map<String, Object> paramsMap, IMyHttpHandler myHttpHandler) {
        getMyHttpClient().httpAsync(createReqInfo(MyReqType.GET, url, paramsMap, null, null, false), myHttpHandler);
    }

    public static void getAsync(String url, Map<String, Object> paramsMap, IMyHttpHandler myHttpHandler, Map<String, List<String>> headers) {
        getMyHttpClient().httpAsync(createReqInfo(MyReqType.GET, url, paramsMap, headers, null, false), myHttpHandler);
    }

    public static void getAsync(String url, Map<String, Object> paramsMap, IMyHttpHandler myHttpHandler, Map<String, List<String>> headers, String tag) {
        getMyHttpClient().httpAsync(createReqInfo(MyReqType.GET, url, paramsMap, headers, tag, false), myHttpHandler);
    }

    public static void postAsync(String url, IMyHttpHandler myHttpHandler) {
        getMyHttpClient().httpAsync(createReqInfo(MyReqType.POST, url, null, null, null, false), myHttpHandler);
    }

    public static void postAsync(String url, Map<String, Object> paramsMap, IMyHttpHandler myHttpHandler) {
        getMyHttpClient().httpAsync(createReqInfo(MyReqType.POST, url, paramsMap, null, null, false), myHttpHandler);
    }

    public static void postAsync(String url, Map<String, Object> paramsMap, IMyHttpHandler myHttpHandler, Map<String, List<String>> headers) {
        getMyHttpClient().httpAsync(createReqInfo(MyReqType.POST, url, paramsMap, headers, null, false), myHttpHandler);
    }

    public static void postAsync(String url, Map<String, Object> paramsMap, IMyHttpHandler myHttpHandler, Map<String, List<String>> headers, String tag) {
        getMyHttpClient().httpAsync(createReqInfo(MyReqType.POST, url, paramsMap, headers, tag, false), myHttpHandler);
    }

    public static void downloadAsync(String url, IMyHttpHandler myHttpHandler) {
        getMyHttpClient().httpAsync(createReqInfo(MyReqType.POST, url, null, null, null, true), myHttpHandler);
    }

    public static void downloadAsync(String url, Map<String, Object> params, IMyHttpHandler myHttpHandler) {
        getMyHttpClient().httpAsync(createReqInfo(MyReqType.POST, url, params, null, null, true), myHttpHandler);
    }

    public static void downloadAsync(String url, Map<String, Object> params, IMyHttpHandler myHttpHandler, Map<String, List<String>> headers) {
        getMyHttpClient().httpAsync(createReqInfo(MyReqType.POST, url, params, headers, null, true), myHttpHandler);
    }

    //--------------------------------------------------------------------sync-------------------------------------------------------------------------
    public static void getSync(String url) {
        getMyHttpClient().httpSync(createReqInfo(MyReqType.GET, url, null, null, null, false), null);
    }

    public static void getSync(String url, IMyHttpHandler myHttpHandler) {
        getMyHttpClient().httpSync(createReqInfo(MyReqType.GET, url, null, null, null, false), myHttpHandler);
    }

    public static void getSync(String url, Map<String, Object> paramsMap, IMyHttpHandler myHttpHandler) {
        getMyHttpClient().httpSync(createReqInfo(MyReqType.GET, url, paramsMap, null, null, false), myHttpHandler);
    }

    public static void getSync(String url, Map<String, Object> paramsMap, IMyHttpHandler myHttpHandler, Map<String, List<String>> headers) {
        getMyHttpClient().httpSync(createReqInfo(MyReqType.GET, url, paramsMap, headers, null, false), myHttpHandler);
    }

    public static void getSync(String url, Map<String, Object> paramsMap, IMyHttpHandler myHttpHandler, Map<String, List<String>> headers, String tag) {
        getMyHttpClient().httpSync(createReqInfo(MyReqType.GET, url, paramsMap, headers, tag, false), myHttpHandler);
    }

    public static void postSync(String url, IMyHttpHandler myHttpHandler) {
        getMyHttpClient().httpSync(createReqInfo(MyReqType.POST, url, null, null, null, false), myHttpHandler);
    }

    public static void postSync(String url, Map<String, Object> paramsMap, IMyHttpHandler myHttpHandler) {
        getMyHttpClient().httpSync(createReqInfo(MyReqType.POST, url, paramsMap, null, null, false), myHttpHandler);
    }

    public static void postSync(String url, Map<String, Object> paramsMap, IMyHttpHandler myHttpHandler, Map<String, List<String>> headers) {
        getMyHttpClient().httpSync(createReqInfo(MyReqType.POST, url, paramsMap, headers, null, false), myHttpHandler);
    }

    public static void postSync(String url, Map<String, Object> paramsMap, IMyHttpHandler myHttpHandler, Map<String, List<String>> headers, String tag) {
        getMyHttpClient().httpSync(createReqInfo(MyReqType.POST, url, paramsMap, headers, tag, false), myHttpHandler);
    }

    public static void downloadSync(String url, IMyHttpHandler myHttpHandler) {
        getMyHttpClient().httpSync(createReqInfo(MyReqType.POST, url, null, null, null, true), myHttpHandler);
    }

    public static void downloadSync(String url, Map<String, Object> params, IMyHttpHandler myHttpHandler) {
        getMyHttpClient().httpSync(createReqInfo(MyReqType.POST, url, params, null, null, true), myHttpHandler);
    }

    public static void downloadSync(String url, Map<String, Object> params, IMyHttpHandler myHttpHandler, Map<String, List<String>> headers) {
        getMyHttpClient().httpSync(createReqInfo(MyReqType.POST, url, params, headers, null, true), myHttpHandler);
    }
}


