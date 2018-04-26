package com.jingyu.java.myokhttp;

import com.jingyu.java.myokhttp.handler.IMyHttpHandler;
import com.jingyu.java.myokhttp.req.MyReqInfo;
import com.jingyu.java.myokhttp.req.MyReqType;
import com.jingyu.java.mytool.util.StringUtil;

import java.util.List;
import java.util.Map;

/**
 * @author fengjingyu@foxmail.com
 */
public class MyHttpUtil {

    public static final String JSON_CONTENT_TYPE = "application/json; charset=utf-8";

    public static final String TEXT_CONTENT_TYPE = "text/plain; charset=utf-8";

    private MyHttpUtil() {
    }

    private static MyHttpClient myHttpClient = new MyHttpClient();

    public static MyHttpClient getMyHttpClient() {
        return myHttpClient;
    }

    public static void setMyHttpClient(MyHttpClient myHttpClient) {
        MyHttpUtil.myHttpClient = myHttpClient;
    }

    public static class Async {

        public static void get(String url, Map<String, Object> paramsMap, IMyHttpHandler myHttpHandler) {
            getMyHttpClient().httpAsync(createReqInfo(MyReqType.GET, url, paramsMap, null, null, false), myHttpHandler);
        }

        public static void get(String url, Map<String, Object> paramsMap, IMyHttpHandler myHttpHandler, Map<String, List<String>> headers) {
            getMyHttpClient().httpAsync(createReqInfo(MyReqType.GET, url, paramsMap, headers, null, false), myHttpHandler);
        }

        public static void get(String url, Map<String, Object> paramsMap, IMyHttpHandler myHttpHandler, Map<String, List<String>> headers, String tag) {
            getMyHttpClient().httpAsync(createReqInfo(MyReqType.GET, url, paramsMap, headers, tag, false), myHttpHandler);
        }

        public static void post(String url, Map<String, Object> paramsMap, IMyHttpHandler myHttpHandler) {
            getMyHttpClient().httpAsync(createReqInfo(MyReqType.POST, url, paramsMap, null, null, false), myHttpHandler);
        }

        public static void post(String url, Map<String, Object> paramsMap, IMyHttpHandler myHttpHandler, Map<String, List<String>> headers) {
            getMyHttpClient().httpAsync(createReqInfo(MyReqType.POST, url, paramsMap, headers, null, false), myHttpHandler);
        }

        public static void post(String url, Map<String, Object> paramsMap, IMyHttpHandler myHttpHandler, Map<String, List<String>> headers, String tag) {
            getMyHttpClient().httpAsync(createReqInfo(MyReqType.POST, url, paramsMap, headers, tag, false), myHttpHandler);
        }

        public static void post(String url, String json, IMyHttpHandler myHttpHandler) {
            post(url, json, JSON_CONTENT_TYPE, myHttpHandler);
        }

        public static void post(String url, String json, IMyHttpHandler myHttpHandler, Map<String, List<String>> headers) {
            post(url, json, JSON_CONTENT_TYPE, myHttpHandler, headers);
        }

        public static void post(String url, String json, IMyHttpHandler myHttpHandler, Map<String, List<String>> headers, String tag) {
            post(url, json, JSON_CONTENT_TYPE, myHttpHandler, headers, tag);
        }

        public static void post(String url, String content, String contentType, IMyHttpHandler myHttpHandler) {
            getMyHttpClient().httpAsync(createReqInfo(MyReqType.POST, url, content, contentType, null, null, false), myHttpHandler);
        }

        public static void post(String url, String content, String contentType, IMyHttpHandler myHttpHandler, Map<String, List<String>> headers) {
            getMyHttpClient().httpAsync(createReqInfo(MyReqType.POST, url, content, contentType, headers, null, false), myHttpHandler);
        }

        public static void post(String url, String content, String contentType, IMyHttpHandler myHttpHandler, Map<String, List<String>> headers, String tag) {
            getMyHttpClient().httpAsync(createReqInfo(MyReqType.POST, url, content, contentType, headers, tag, false), myHttpHandler);
        }

        public static void download(String url, Map<String, Object> params, IMyHttpHandler myHttpHandler) {
            getMyHttpClient().httpAsync(createReqInfo(MyReqType.POST, url, params, null, null, true), myHttpHandler);
        }

        public static void download(String url, Map<String, Object> params, IMyHttpHandler myHttpHandler, Map<String, List<String>> headers) {
            getMyHttpClient().httpAsync(createReqInfo(MyReqType.POST, url, params, headers, null, true), myHttpHandler);
        }
    }

    public static class Sync {

        public static void get(String url, Map<String, Object> paramsMap, IMyHttpHandler myHttpHandler) {
            getMyHttpClient().httpSync(createReqInfo(MyReqType.GET, url, paramsMap, null, null, false), myHttpHandler);
        }

        public static void get(String url, Map<String, Object> paramsMap, IMyHttpHandler myHttpHandler, Map<String, List<String>> headers) {
            getMyHttpClient().httpSync(createReqInfo(MyReqType.GET, url, paramsMap, headers, null, false), myHttpHandler);
        }

        public static void get(String url, Map<String, Object> paramsMap, IMyHttpHandler myHttpHandler, Map<String, List<String>> headers, String tag) {
            getMyHttpClient().httpSync(createReqInfo(MyReqType.GET, url, paramsMap, headers, tag, false), myHttpHandler);
        }

        public static void post(String url, Map<String, Object> paramsMap, IMyHttpHandler myHttpHandler) {
            getMyHttpClient().httpSync(createReqInfo(MyReqType.POST, url, paramsMap, null, null, false), myHttpHandler);
        }

        public static void post(String url, Map<String, Object> paramsMap, IMyHttpHandler myHttpHandler, Map<String, List<String>> headers) {
            getMyHttpClient().httpSync(createReqInfo(MyReqType.POST, url, paramsMap, headers, null, false), myHttpHandler);
        }

        public static void post(String url, Map<String, Object> paramsMap, IMyHttpHandler myHttpHandler, Map<String, List<String>> headers, String tag) {
            getMyHttpClient().httpSync(createReqInfo(MyReqType.POST, url, paramsMap, headers, tag, false), myHttpHandler);
        }

        public static void post(String url, String json, IMyHttpHandler myHttpHandler) {
            post(url, json, JSON_CONTENT_TYPE, myHttpHandler);
        }

        public static void post(String url, String json, IMyHttpHandler myHttpHandler, Map<String, List<String>> headers) {
            post(url, json, JSON_CONTENT_TYPE, myHttpHandler, headers);
        }

        public static void post(String url, String json, IMyHttpHandler myHttpHandler, Map<String, List<String>> headers, String tag) {
            post(url, json, JSON_CONTENT_TYPE, myHttpHandler, headers, tag);
        }

        public static void post(String url, String content, String contentType, IMyHttpHandler myHttpHandler) {
            getMyHttpClient().httpSync(createReqInfo(MyReqType.POST, url, content, contentType, null, null, false), myHttpHandler);
        }

        public static void post(String url, String content, String contentType, IMyHttpHandler myHttpHandler, Map<String, List<String>> headers) {
            getMyHttpClient().httpSync(createReqInfo(MyReqType.POST, url, content, contentType, headers, null, false), myHttpHandler);
        }

        public static void post(String url, String content, String contentType, IMyHttpHandler myHttpHandler, Map<String, List<String>> headers, String tag) {
            getMyHttpClient().httpSync(createReqInfo(MyReqType.POST, url, content, contentType, headers, tag, false), myHttpHandler);
        }

        public static void download(String url, Map<String, Object> params, IMyHttpHandler myHttpHandler) {
            getMyHttpClient().httpSync(createReqInfo(MyReqType.POST, url, params, null, null, true), myHttpHandler);
        }

        public static void download(String url, Map<String, Object> params, IMyHttpHandler myHttpHandler, Map<String, List<String>> headers) {
            getMyHttpClient().httpSync(createReqInfo(MyReqType.POST, url, params, headers, null, true), myHttpHandler);
        }
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

    private static MyReqInfo createReqInfo(MyReqType type, String url, String content, String contentType, Map<String, List<String>> headers, String tag, boolean isDownload) {
        if (StringUtil.isBlank(contentType)) {
            throw new RuntimeException(url + "--请求contentType为空");
        }

        return new MyReqInfo.Builder()
                .myReqType(type)
                .url(url)
                .postString(content)
                .postStringContentType(contentType)
                .headersMap(headers)
                .tag(tag)
                .isDownload(isDownload)
                .builder();
    }
}


