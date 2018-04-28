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

        public static void get(String url, Map<String, Object> paramsMap, IMyHttpHandler myHttpHandler) {
            getMyHttpClient().httpAsync(createReqInfo(MyReqType.GET, url, paramsMap, null, null, "", ""), myHttpHandler);
        }

        public static void get(String url, Map<String, Object> paramsMap, Map<String, List<String>> headers, IMyHttpHandler myHttpHandler) {
            getMyHttpClient().httpAsync(createReqInfo(MyReqType.GET, url, paramsMap, headers, null, "", ""), myHttpHandler);
        }

        public static void get(String url, Map<String, Object> paramsMap, Map<String, List<String>> headers, String tag, IMyHttpHandler myHttpHandler) {
            getMyHttpClient().httpAsync(createReqInfo(MyReqType.GET, url, paramsMap, headers, tag, "", ""), myHttpHandler);
        }

        public static void post(String url, Map<String, Object> paramsMap, IMyHttpHandler myHttpHandler) {
            getMyHttpClient().httpAsync(createReqInfo(MyReqType.POST, url, paramsMap, null, null, "", ""), myHttpHandler);
        }

        public static void post(String url, Map<String, Object> paramsMap, Map<String, List<String>> headers, IMyHttpHandler myHttpHandler) {
            getMyHttpClient().httpAsync(createReqInfo(MyReqType.POST, url, paramsMap, headers, null, "", ""), myHttpHandler);
        }

        public static void post(String url, Map<String, Object> paramsMap, Map<String, List<String>> headers, String tag, IMyHttpHandler myHttpHandler) {
            getMyHttpClient().httpAsync(createReqInfo(MyReqType.POST, url, paramsMap, headers, tag, "", ""), myHttpHandler);
        }

        public static void post(String url, String postString, String contentType, IMyHttpHandler myHttpHandler) {
            getMyHttpClient().httpAsync(createReqInfo(MyReqType.POST, url, null, null, null, postString, contentType), myHttpHandler);
        }

        public static void post(String url, String postString, String contentType, Map<String, List<String>> headers, IMyHttpHandler myHttpHandler) {
            getMyHttpClient().httpAsync(createReqInfo(MyReqType.POST, url, null, headers, null, postString, contentType), myHttpHandler);
        }

        public static void post(String url, String postString, String contentType, Map<String, List<String>> headers, String tag, IMyHttpHandler myHttpHandler) {
            getMyHttpClient().httpAsync(createReqInfo(MyReqType.POST, url, null, headers, tag, postString, contentType), myHttpHandler);
        }

        public static void post(String url, Map<String, Object> map, String postString, String contentType, IMyHttpHandler myHttpHandler) {
            getMyHttpClient().httpAsync(createReqInfo(MyReqType.POST, url, map, null, null, postString, contentType), myHttpHandler);
        }

        public static void post(String url, Map<String, Object> map, String postString, String contentType, Map<String, List<String>> headers, IMyHttpHandler myHttpHandler) {
            getMyHttpClient().httpAsync(createReqInfo(MyReqType.POST, url, map, headers, null, postString, contentType), myHttpHandler);
        }

        public static void post(String url, Map<String, Object> map, String postString, String contentType, Map<String, List<String>> headers, String tag, IMyHttpHandler myHttpHandler) {
            getMyHttpClient().httpAsync(createReqInfo(MyReqType.POST, url, map, headers, tag, postString, contentType), myHttpHandler);
        }
    }

    public static class Sync {

        public static void get(String url, Map<String, Object> paramsMap, IMyHttpHandler myHttpHandler) {
            getMyHttpClient().httpSync(createReqInfo(MyReqType.GET, url, paramsMap, null, null, "", ""), myHttpHandler);
        }

        public static void get(String url, Map<String, Object> paramsMap, Map<String, List<String>> headers, IMyHttpHandler myHttpHandler) {
            getMyHttpClient().httpSync(createReqInfo(MyReqType.GET, url, paramsMap, headers, null, "", ""), myHttpHandler);
        }

        public static void get(String url, Map<String, Object> paramsMap, Map<String, List<String>> headers, String tag, IMyHttpHandler myHttpHandler) {
            getMyHttpClient().httpSync(createReqInfo(MyReqType.GET, url, paramsMap, headers, tag, "", ""), myHttpHandler);
        }

        public static void post(String url, Map<String, Object> paramsMap, IMyHttpHandler myHttpHandler) {
            getMyHttpClient().httpSync(createReqInfo(MyReqType.POST, url, paramsMap, null, null, "", ""), myHttpHandler);
        }

        public static void post(String url, Map<String, Object> paramsMap, Map<String, List<String>> headers, IMyHttpHandler myHttpHandler) {
            getMyHttpClient().httpSync(createReqInfo(MyReqType.POST, url, paramsMap, headers, null, "", ""), myHttpHandler);
        }

        public static void post(String url, Map<String, Object> paramsMap, Map<String, List<String>> headers, String tag, IMyHttpHandler myHttpHandler) {
            getMyHttpClient().httpSync(createReqInfo(MyReqType.POST, url, paramsMap, headers, tag, "", ""), myHttpHandler);
        }

        public static void post(String url, String postString, String contentType, IMyHttpHandler myHttpHandler) {
            getMyHttpClient().httpSync(createReqInfo(MyReqType.POST, url, null, null, null, postString, contentType), myHttpHandler);
        }

        public static void post(String url, String postString, String contentType, Map<String, List<String>> headers, IMyHttpHandler myHttpHandler) {
            getMyHttpClient().httpSync(createReqInfo(MyReqType.POST, url, null, headers, null, postString, contentType), myHttpHandler);
        }

        public static void post(String url, String postString, String contentType, Map<String, List<String>> headers, String tag, IMyHttpHandler myHttpHandler) {
            getMyHttpClient().httpSync(createReqInfo(MyReqType.POST, url, null, headers, tag, postString, contentType), myHttpHandler);
        }

        public static void post(String url, Map<String, Object> map, String postString, String contentType, IMyHttpHandler myHttpHandler) {
            getMyHttpClient().httpSync(createReqInfo(MyReqType.POST, url, map, null, null, postString, contentType), myHttpHandler);
        }

        public static void post(String url, Map<String, Object> map, String postString, String contentType, Map<String, List<String>> headers, IMyHttpHandler myHttpHandler) {
            getMyHttpClient().httpSync(createReqInfo(MyReqType.POST, url, map, headers, null, postString, contentType), myHttpHandler);
        }

        public static void post(String url, Map<String, Object> map, String postString, String contentType, Map<String, List<String>> headers, String tag, IMyHttpHandler myHttpHandler) {
            getMyHttpClient().httpSync(createReqInfo(MyReqType.POST, url, map, headers, tag, postString, contentType), myHttpHandler);
        }
    }

    private static MyReqInfo createReqInfo(MyReqType type, String url, Map<String, Object> paramsMap, Map<String, List<String>> headers, String tag, String postString, String postStringContentType) {
        return new MyReqInfo.Builder()
                .myReqType(type)
                .url(url)
                .paramsMap(paramsMap)
                .headersMap(headers)
                .tag(tag)
                .postString(postString)
                .postStringContentType(postStringContentType)
                .builder();
    }
}


