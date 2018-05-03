package com.jingyu.java.myokhttp;

import com.jingyu.java.myokhttp.handler.IMyHttpHandler;
import com.jingyu.java.myokhttp.req.MyReqInfo;
import com.jingyu.java.myokhttp.req.MyReqType;

import java.util.List;
import java.util.Map;

/**
 * @author fengjingyu@foxmail.com
 */
public class MyHttpUtil {

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
        // get
        public static void get(String url, Map<String, Object> queryMap, IMyHttpHandler myHttpHandler) {
            getMyHttpClient().httpAsync(createReqInfo(MyReqType.GET, url, null, queryMap, null, null, null, null), myHttpHandler);
        }

        public static void get(String url, Map<String, List<String>> headers, Map<String, Object> queryMap, String tag, IMyHttpHandler myHttpHandler) {
            getMyHttpClient().httpAsync(createReqInfo(MyReqType.GET, url, headers, queryMap, null, null, null, tag), myHttpHandler);
        }

        // post表单(含file)
        public static void post(String url, Map<String, Object> bodyMap, IMyHttpHandler myHttpHandler) {
            getMyHttpClient().httpAsync(createReqInfo(MyReqType.POST, url, null, null, bodyMap, null, null, null), myHttpHandler);
        }

        public static void post(String url, Map<String, List<String>> headers, Map<String, Object> queryMap, Map<String, Object> bodyMap, String tag, IMyHttpHandler myHttpHandler) {
            getMyHttpClient().httpAsync(createReqInfo(MyReqType.POST, url, headers, queryMap, bodyMap, null, null, tag), myHttpHandler);
        }

        // postString
        public static void post(String url, String bodyContent, String contentType, IMyHttpHandler myHttpHandler) {
            getMyHttpClient().httpAsync(createReqInfo(MyReqType.POST, url, null, null, null, bodyContent, contentType, null), myHttpHandler);
        }

        public static void post(String url, Map<String, List<String>> headers, Map<String, Object> queryMap, String bodyContent, String contentType, String tag, IMyHttpHandler myHttpHandler) {
            getMyHttpClient().httpAsync(createReqInfo(MyReqType.POST, url, headers, queryMap, null, bodyContent, contentType, tag), myHttpHandler);
        }

    }

    public static class Sync {
        // get
        public static void get(String url, Map<String, Object> queryMap, IMyHttpHandler myHttpHandler) {
            getMyHttpClient().httpSync(createReqInfo(MyReqType.GET, url, null, queryMap, null, null, null, null), myHttpHandler);
        }

        public static void get(String url, Map<String, List<String>> headers, Map<String, Object> queryMap, String tag, IMyHttpHandler myHttpHandler) {
            getMyHttpClient().httpSync(createReqInfo(MyReqType.GET, url, headers, queryMap, null, null, null, tag), myHttpHandler);
        }

        // post表单(含file)
        public static void post(String url, Map<String, Object> bodyMap, IMyHttpHandler myHttpHandler) {
            getMyHttpClient().httpSync(createReqInfo(MyReqType.POST, url, null, null, bodyMap, null, null, null), myHttpHandler);
        }

        public static void post(String url, Map<String, List<String>> headers, Map<String, Object> queryMap, Map<String, Object> bodyMap, String tag, IMyHttpHandler myHttpHandler) {
            getMyHttpClient().httpSync(createReqInfo(MyReqType.POST, url, headers, queryMap, bodyMap, null, null, tag), myHttpHandler);
        }

        // postString
        public static void post(String url, String bodyContent, String contentType, IMyHttpHandler myHttpHandler) {
            getMyHttpClient().httpSync(createReqInfo(MyReqType.POST, url, null, null, null, bodyContent, contentType, null), myHttpHandler);
        }

        public static void post(String url, Map<String, List<String>> headers, Map<String, Object> queryMap, String bodyContent, String contentType, String tag, IMyHttpHandler myHttpHandler) {
            getMyHttpClient().httpSync(createReqInfo(MyReqType.POST, url, headers, queryMap, null, bodyContent, contentType, tag), myHttpHandler);
        }

    }

    private static MyReqInfo createReqInfo(MyReqType type, String url, Map<String, List<String>> headers,
                                           Map<String, Object> queryMap, Map<String, Object> bodyMap,
                                           String bodyContent, String contentType,
                                           String tag) {
        return new MyReqInfo.Builder()
                .myReqType(type)
                .url(url)
                .headersMap(headers)
                .queryMap(queryMap)
                .bodyMap(bodyMap)
                .bodyContent(bodyContent)
                .contentType(contentType)
                .tag(tag)
                .builder();
    }
}


