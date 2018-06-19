package com.jingyu.java.myokhttp;

import com.jingyu.java.myokhttp.handler.IHttpHandler;
import com.jingyu.java.myokhttp.req.ProgressRequestBody;
import com.jingyu.java.myokhttp.req.ReqInfo;
import com.jingyu.java.myokhttp.req.UploadFile;
import com.jingyu.java.mytool.util.CollectionUtil;
import com.jingyu.java.mytool.util.StringUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.*;

/**
 * @author fengjingyu@foxmail.com
 */
public class HttpClient {

    private OkHttpClient okHttpClient;

    public HttpClient() {
        okHttpClient = initOkHttpClient();
    }

    public void httpSync(ReqInfo reqInfo, IHttpHandler iHttpHandler) {
        HttpCallBack httpCallBack = createHttpCallBack(reqInfo, iHttpHandler);
        Call call = null;
        try {
            Request request = getRequest(reqInfo, iHttpHandler);
            call = okHttpClient.newCall(request);
            Response response = call.execute();
            if (response != null) {
                httpCallBack.onResponse(call, response);
            }
        } catch (IOException e) {
            e.printStackTrace();
            httpCallBack.onFailure(call, e);
        }
    }

    public void httpAsync(ReqInfo reqInfo, IHttpHandler iHttpHandler) {
        try {
            Request request = getRequest(reqInfo, iHttpHandler);
            okHttpClient.newCall(request).enqueue(createHttpCallBack(reqInfo, iHttpHandler));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Request getRequest(ReqInfo reqInfo, IHttpHandler iHttpHandler) {
        // 构建请求前拦截, 可以对请求头 请求加密等信息做统一修改
        reqInfo = interceptBuildRequest(reqInfo);
        // show进度条以及个别请求可能需要配置额外的请求信息
        if (iHttpHandler != null) {
            reqInfo = iHttpHandler.onReadySendRequest(reqInfo);
        }
        // 构建请求
        Request.Builder requestBuilder = new Request.Builder();
        // 构建请求方式、参数、url
        buildeTypeParamsUrl(reqInfo, requestBuilder, iHttpHandler);
        // 构建请求header
        buildHeader(reqInfo, requestBuilder);
        // 返回request
        return requestBuilder.build();
    }

    private void buildeTypeParamsUrl(ReqInfo reqInfo, Request.Builder requestBuilder, IHttpHandler iHttpHandler) {

        requestBuilder.url(reqInfo.getUrl() + reqInfo.buildUrlQuery(reqInfo.getQueryMap()));

        if (isGet(reqInfo, requestBuilder)) {
            return;
        }

        if (isPost(reqInfo, requestBuilder, iHttpHandler)) {
            return;
        }

        throw new RuntimeException("buildeTypeParamsUrl()---请求类型不匹配");

    }

    private boolean isPost(ReqInfo reqInfo, Request.Builder requestBuilder, IHttpHandler iHttpHandler) {
        if (reqInfo.isPost()) {

            if (StringUtil.isNotBlank(reqInfo.getBodyContent()) && CollectionUtil.isNotEmpty(reqInfo.getBodyMap())) {
                throw new RuntimeException("请求体参数有误, bodyContent 与 bodyMap的值重复了");
            }

            if (isPostString(reqInfo, requestBuilder)) {
                return true;
            }

            if (isPostForm(reqInfo, requestBuilder)) {
                return true;
            }

            if (isPostMultiForm(reqInfo, requestBuilder, iHttpHandler)) {
                return true;
            }

        }
        return false;
    }

    /**
     * // post  在请求体里添加string 同时也可在 url?后面拼接参数
     * // post: 如果contenttype是表单key=value&key2=value2, url?key=dog,
     * //      则springmvc的注解 @RequestParam String key 获取的是 dog,value
     * //      则springmvc的注解 @RequestBody String content 获取的是 key=dog&key=value&key2=value2
     * // post: 如果contenttype是application/json 如{"a":"b"} ,url?key=dog
     * //      则springmvc的注解 @RequestParam String key 获取的是dog
     * //      则springmvc的注解 @RequestBody String conent 获取的是{"a":"b"}
     */
    private boolean isPostString(ReqInfo reqInfo, Request.Builder requestBuilder) {
        if (StringUtil.isNotBlank(reqInfo.getBodyContent())) {
            // reqInfo.getContentType()可以为空, okhttp默认会设置为"application/octet-stream" or springmvc默认用"application/octet-stream"接收
            requestBuilder.post(RequestBody.create(MediaType.parse(reqInfo.getContentType()), reqInfo.getBodyContent()));
            return true;
        }
        return false;
    }

    private boolean isPostForm(ReqInfo reqInfo, Request.Builder requestBuilder) {
        if (isIncludeFile(reqInfo)) {
            return false;
        }

        Map<String, Object> bodyMap = reqInfo.getBodyMap();

        if (CollectionUtil.isNotEmpty(bodyMap)) {
            FormBody.Builder formBodyBuilder = new FormBody.Builder();

            for (Map.Entry<String, Object> entry : bodyMap.entrySet()) {
                formBodyBuilder.add(entry.getKey(), String.valueOf(entry.getValue()));
            }
            requestBuilder.post(formBodyBuilder.build());
        }

        return true;
    }

    private boolean isPostMultiForm(ReqInfo reqInfo, Request.Builder requestBuilder, IHttpHandler iHttpHandler) {
        Map<String, Object> bodyMap = reqInfo.getBodyMap();

        if (CollectionUtil.isNotEmpty(bodyMap)) {

            MultipartBody.Builder multiBuilder = new MultipartBody.Builder();
            multiBuilder.setType(MultipartBody.FORM);

            for (Map.Entry<String, Object> entry : bodyMap.entrySet()) {

                if (entry.getValue() == null) {
                    continue;
                }

                if (entry.getValue() instanceof File) {

                    UploadFile uploadFile = new UploadFile((File) entry.getValue());

                    if (uploadFile.isExist()) {
                        multiBuilder.addFormDataPart(entry.getKey(), uploadFile.getName(), RequestBody.create(uploadFile.getMediaType(), uploadFile.getFile()));
                    } else {
                        continue;
                    }

                } else {
                    multiBuilder.addFormDataPart(entry.getKey(), String.valueOf(entry.getValue()));
                }
            }

            MultipartBody multipartBody = multiBuilder.build();
            ProgressRequestBody progressRequestBody = createProgressRequestBody(iHttpHandler, multipartBody);
            requestBuilder.post(progressRequestBody);
        }

        return true;
    }

    private boolean isIncludeFile(ReqInfo reqInfo) {
        Map<String, Object> bodyMap = reqInfo.getBodyMap();

        if (CollectionUtil.isNotEmpty(bodyMap)) {
            for (Map.Entry<String, Object> entry : bodyMap.entrySet()) {
                if (entry.getValue() instanceof File) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isGet(ReqInfo reqInfo, Request.Builder requestBuilder) {
        if (reqInfo.isGet()) {
            requestBuilder.get();
            return true;
        }
        return false;
    }


    private Request.Builder buildHeader(ReqInfo reqInfo, Request.Builder builder) {
        Map<String, List<String>> headers = reqInfo.getHeadersMap();

        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {

            List<String> values = entry.getValue();
            String key = entry.getKey();

            if (CollectionUtil.isNotEmpty(values)) {
                for (String value : values) {
                    builder.addHeader(key, value);
                }
            }
        }
        return builder;
    }

    protected ProgressRequestBody createProgressRequestBody(IHttpHandler iHttpHandler, MultipartBody multipartBody) {
        return new ProgressRequestBody(multipartBody, iHttpHandler);
    }

    protected HttpCallBack createHttpCallBack(ReqInfo reqInfo, IHttpHandler iHttpHandler) {
        return new HttpCallBack(reqInfo, iHttpHandler);
    }

    protected ReqInfo interceptBuildRequest(ReqInfo reqInfo) {
        return reqInfo;
    }

    protected OkHttpClient initOkHttpClient() {
        return new OkHttpClient();
    }

}
