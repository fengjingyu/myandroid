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

    public static class Async {
        public static void get(String url) {
            getMyHttpClient().httpAsync(createReqInfo(MyReqType.GET, url, null, null, null, false), null);
        }

        public static void get(String url, IMyHttpHandler myHttpHandler) {
            getMyHttpClient().httpAsync(createReqInfo(MyReqType.GET, url, null, null, null, false), myHttpHandler);
        }

        public static void get(String url, Map<String, Object> paramsMap, IMyHttpHandler myHttpHandler) {
            getMyHttpClient().httpAsync(createReqInfo(MyReqType.GET, url, paramsMap, null, null, false), myHttpHandler);
        }

        public static void get(String url, Map<String, Object> paramsMap, IMyHttpHandler myHttpHandler, Map<String, List<String>> headers) {
            getMyHttpClient().httpAsync(createReqInfo(MyReqType.GET, url, paramsMap, headers, null, false), myHttpHandler);
        }

        public static void get(String url, Map<String, Object> paramsMap, IMyHttpHandler myHttpHandler, Map<String, List<String>> headers, String tag) {
            getMyHttpClient().httpAsync(createReqInfo(MyReqType.GET, url, paramsMap, headers, tag, false), myHttpHandler);
        }

        public static void post(String url, IMyHttpHandler myHttpHandler) {
            getMyHttpClient().httpAsync(createReqInfo(MyReqType.POST, url, null, null, null, false), myHttpHandler);
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

        public static void download(String url, IMyHttpHandler myHttpHandler) {
            getMyHttpClient().httpAsync(createReqInfo(MyReqType.POST, url, null, null, null, true), myHttpHandler);
        }

        public static void download(String url, Map<String, Object> params, IMyHttpHandler myHttpHandler) {
            getMyHttpClient().httpAsync(createReqInfo(MyReqType.POST, url, params, null, null, true), myHttpHandler);
        }

        public static void download(String url, Map<String, Object> params, IMyHttpHandler myHttpHandler, Map<String, List<String>> headers) {
            getMyHttpClient().httpAsync(createReqInfo(MyReqType.POST, url, params, headers, null, true), myHttpHandler);
        }
    }

    public static class Sync {
        public static void get(String url) {
            getMyHttpClient().httpSync(createReqInfo(MyReqType.GET, url, null, null, null, false), null);
        }

        public static void get(String url, IMyHttpHandler myHttpHandler) {
            getMyHttpClient().httpSync(createReqInfo(MyReqType.GET, url, null, null, null, false), myHttpHandler);
        }

        public static void get(String url, Map<String, Object> paramsMap, IMyHttpHandler myHttpHandler) {
            getMyHttpClient().httpSync(createReqInfo(MyReqType.GET, url, paramsMap, null, null, false), myHttpHandler);
        }

        public static void get(String url, Map<String, Object> paramsMap, IMyHttpHandler myHttpHandler, Map<String, List<String>> headers) {
            getMyHttpClient().httpSync(createReqInfo(MyReqType.GET, url, paramsMap, headers, null, false), myHttpHandler);
        }

        public static void get(String url, Map<String, Object> paramsMap, IMyHttpHandler myHttpHandler, Map<String, List<String>> headers, String tag) {
            getMyHttpClient().httpSync(createReqInfo(MyReqType.GET, url, paramsMap, headers, tag, false), myHttpHandler);
        }

        public static void post(String url, IMyHttpHandler myHttpHandler) {
            getMyHttpClient().httpSync(createReqInfo(MyReqType.POST, url, null, null, null, false), myHttpHandler);
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

        public static void download(String url, IMyHttpHandler myHttpHandler) {
            getMyHttpClient().httpSync(createReqInfo(MyReqType.POST, url, null, null, null, true), myHttpHandler);
        }

        public static void download(String url, Map<String, Object> params, IMyHttpHandler myHttpHandler) {
            getMyHttpClient().httpSync(createReqInfo(MyReqType.POST, url, params, null, null, true), myHttpHandler);
        }

        public static void download(String url, Map<String, Object> params, IMyHttpHandler myHttpHandler, Map<String, List<String>> headers) {
            getMyHttpClient().httpSync(createReqInfo(MyReqType.POST, url, params, headers, null, true), myHttpHandler);
        }
    }

}


