package com.jingyu.app.middle;

import com.jingyu.java.myokhttp.HttpClient;
import com.jingyu.java.myokhttp.handler.IHttpHandler;
import com.jingyu.java.myokhttp.req.ReqInfo;
import com.jingyu.java.myokhttp.req.ReqType;

import java.util.List;
import java.util.Map;

/**
 * @author fengjingyu@foxmail.com
 */
public class MyHttp {

    private MyHttp() {
    }

    private static HttpClient httpClient = new HttpClient();

    public static HttpClient getHttpClient() {
        return httpClient;
    }

    public static void setMyHttpClient(HttpClient httpClient) {
        MyHttp.httpClient = httpClient;
    }

    public static class Async {

        public static void request(ReqInfo reqInfo, IHttpHandler iHttpHandler) {
            getHttpClient().httpAsync(reqInfo, iHttpHandler);
        }

        // get
        public static void get(String url, Map<String, Object> queryMap, IHttpHandler iHttpHandler) {
            getHttpClient().httpAsync(createReqInfo(ReqType.GET, url, null, queryMap, null, null, null, null), iHttpHandler);
        }

        public static void get(String url, Map<String, List<String>> headers, Map<String, Object> queryMap, IHttpHandler iHttpHandler) {
            getHttpClient().httpAsync(createReqInfo(ReqType.GET, url, headers, queryMap, null, null, null, null), iHttpHandler);
        }

        public static void get(String url, Map<String, List<String>> headers, Map<String, Object> queryMap, String tag, IHttpHandler iHttpHandler) {
            getHttpClient().httpAsync(createReqInfo(ReqType.GET, url, headers, queryMap, null, null, null, tag), iHttpHandler);
        }

        // post表单(含多文件file)
        public static void post(String url, Map<String, Object> bodyMap, IHttpHandler iHttpHandler) {
            getHttpClient().httpAsync(createReqInfo(ReqType.POST, url, null, null, bodyMap, null, null, null), iHttpHandler);
        }

        public static void post(String url, Map<String, List<String>> headers, Map<String, Object> bodyMap, IHttpHandler iHttpHandler) {
            getHttpClient().httpAsync(createReqInfo(ReqType.POST, url, headers, null, bodyMap, null, null, null), iHttpHandler);
        }

        public static void post(String url, Map<String, List<String>> headers, Map<String, Object> queryMap, Map<String, Object> bodyMap, String tag, IHttpHandler iHttpHandler) {
            getHttpClient().httpAsync(createReqInfo(ReqType.POST, url, headers, queryMap, bodyMap, null, null, tag), iHttpHandler);
        }

        // postString
        public static void post(String url, String bodyContent, String contentType, IHttpHandler iHttpHandler) {
            getHttpClient().httpAsync(createReqInfo(ReqType.POST, url, null, null, null, bodyContent, contentType, null), iHttpHandler);
        }

        public static void post(String url, Map<String, List<String>> headers, String bodyContent, String contentType, IHttpHandler iHttpHandler) {
            getHttpClient().httpAsync(createReqInfo(ReqType.POST, url, headers, null, null, bodyContent, contentType, null), iHttpHandler);
        }

        public static void post(String url, Map<String, List<String>> headers, Map<String, Object> queryMap, String bodyContent, String contentType, String tag, IHttpHandler iHttpHandler) {
            getHttpClient().httpAsync(createReqInfo(ReqType.POST, url, headers, queryMap, null, bodyContent, contentType, tag), iHttpHandler);
        }

    }

    public static class Sync {

        public static void request(ReqInfo myReqInfo, IHttpHandler iHttpHandler) {
            getHttpClient().httpSync(myReqInfo, iHttpHandler);
        }

        // get
        public static void get(String url, Map<String, Object> queryMap, IHttpHandler iHttpHandler) {
            getHttpClient().httpSync(createReqInfo(ReqType.GET, url, null, queryMap, null, null, null, null), iHttpHandler);
        }

        public static void get(String url, Map<String, List<String>> headers, Map<String, Object> queryMap, IHttpHandler iHttpHandler) {
            getHttpClient().httpSync(createReqInfo(ReqType.GET, url, headers, queryMap, null, null, null, null), iHttpHandler);
        }

        public static void get(String url, Map<String, List<String>> headers, Map<String, Object> queryMap, String tag, IHttpHandler iHttpHandler) {
            getHttpClient().httpSync(createReqInfo(ReqType.GET, url, headers, queryMap, null, null, null, tag), iHttpHandler);
        }

        // post表单(含多文件file)
        public static void post(String url, Map<String, Object> bodyMap, IHttpHandler iHttpHandler) {
            getHttpClient().httpSync(createReqInfo(ReqType.POST, url, null, null, bodyMap, null, null, null), iHttpHandler);
        }

        public static void post(String url, Map<String, List<String>> headers, Map<String, Object> bodyMap, IHttpHandler iHttpHandler) {
            getHttpClient().httpSync(createReqInfo(ReqType.POST, url, headers, null, bodyMap, null, null, null), iHttpHandler);
        }

        public static void post(String url, Map<String, List<String>> headers, Map<String, Object> queryMap, Map<String, Object> bodyMap, String tag, IHttpHandler iHttpHandler) {
            getHttpClient().httpSync(createReqInfo(ReqType.POST, url, headers, queryMap, bodyMap, null, null, tag), iHttpHandler);
        }

        // postString
        public static void post(String url, String bodyContent, String contentType, IHttpHandler iHttpHandler) {
            getHttpClient().httpSync(createReqInfo(ReqType.POST, url, null, null, null, bodyContent, contentType, null), iHttpHandler);
        }

        public static void post(String url, Map<String, List<String>> headers, String bodyContent, String contentType, IHttpHandler iHttpHandler) {
            getHttpClient().httpSync(createReqInfo(ReqType.POST, url, headers, null, null, bodyContent, contentType, null), iHttpHandler);
        }

        public static void post(String url, Map<String, List<String>> headers, Map<String, Object> queryMap, String bodyContent, String contentType, String tag, IHttpHandler iHttpHandler) {
            getHttpClient().httpSync(createReqInfo(ReqType.POST, url, headers, queryMap, null, bodyContent, contentType, tag), iHttpHandler);
        }

    }

    private static ReqInfo createReqInfo(ReqType type, String url, Map<String, List<String>> headers,
                                         Map<String, Object> queryMap, Map<String, Object> bodyFormMap,
                                         String bodyContent, String contentType,
                                         String tag) {
        return new ReqInfo.Builder()
                .reqType(type)
                .url(url)
                .headersMap(headers)
                .queryMap(queryMap)
                .bodyFormMap(bodyFormMap)
                .bodyContent(bodyContent)
                .contentType(contentType)
                .tag(tag)
                .builder();
    }
}


