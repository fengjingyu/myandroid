package com.jingyu.java.myokhttp;

import com.jingyu.java.myokhttp.handler.IMyHttpHandler;
import com.jingyu.java.myokhttp.req.MyProgressRequestBody;
import com.jingyu.java.myokhttp.req.MyReqInfo;
import com.jingyu.java.myokhttp.req.MyUploadFile;
import com.jingyu.java.mytool.util.CollectionsUtil;
import com.jingyu.java.mytool.util.StringUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.*;

/**
 * @author fengjingyu@foxmail.com
 */
public class MyHttpClient {

    private OkHttpClient okHttpClient;

    protected OkHttpClient getOkHttpClient() {
        return new OkHttpClient();
    }

    public MyHttpClient() {
        okHttpClient = getOkHttpClient();
    }

    public void httpSync(MyReqInfo myReqInfo, IMyHttpHandler iMyHttpHandler) {
        MyHttpCallBack myHttpCallBack = getHttpCallBack(myReqInfo, iMyHttpHandler);
        Call call = null;
        try {
            call = okHttpClient.newCall(getRequest(myReqInfo, iMyHttpHandler));
            Response response = call.execute();
            if (response != null && response.isSuccessful()) {
                myHttpCallBack.onResponse(call, response);
            }
        } catch (IOException e) {
            e.printStackTrace();
            myHttpCallBack.onFailure(call, e);
        }
    }

    public void httpAsync(MyReqInfo myReqInfo, IMyHttpHandler iMyHttpHandler) {
        try {
            Request request = getRequest(myReqInfo, iMyHttpHandler);
            okHttpClient.newCall(request).enqueue(getHttpCallBack(myReqInfo, iMyHttpHandler));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Request getRequest(MyReqInfo myReqInfo, IMyHttpHandler iMyHttpHandler) {
        if (iMyHttpHandler != null) {
            // 发送请求之前，这里可以修改请求参数信息
            myReqInfo = iMyHttpHandler.onReadySendRequest(myReqInfo);
        }

        Request.Builder requestBuilder = new Request.Builder();
        // 构建请求方式、参数、url
        buildeTypeParamsUrl(myReqInfo, requestBuilder, iMyHttpHandler);
        // 构建请求header
        buildHeader(myReqInfo, requestBuilder);
        // 返回request
        return requestBuilder.build();
    }

    private void buildeTypeParamsUrl(MyReqInfo myReqInfo, Request.Builder requestBuilder, IMyHttpHandler iMyHttpHandler) {

        if (isGet(myReqInfo, requestBuilder)) {
            return;
        }

        if (isPost(myReqInfo, requestBuilder, iMyHttpHandler)) {
            return;
        }

        throw new RuntimeException("buildeTypeParamsUrl()---请求类型不匹配");

    }

    private boolean isPost(MyReqInfo myReqInfo, Request.Builder requestBuilder, IMyHttpHandler iMyHttpHandler) {
        if (myReqInfo.isPost()) {

            requestBuilder.url(myReqInfo.getUrl());

            if (isPostString(myReqInfo, requestBuilder)) {
                return true;
            }

            if (isPostForm(myReqInfo, requestBuilder)) {
                return true;
            }

            if (isPostMultiForm(myReqInfo, requestBuilder, iMyHttpHandler)) {
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
     * //post: 如果contenttype是application/json 如{"a":"b"} ,url?key=dog
     * //      则springmvc的注解 @RequestParam String key 获取的是dog
     * //      则springmvc的注解 @RequestBody String conent 获取的是{"a":"b"}
     */
    private boolean isPostString(MyReqInfo myReqInfo, Request.Builder requestBuilder) {
        if (StringUtil.isAvaliable(myReqInfo.getPostString())) {

            if (!myReqInfo.getParamsMap().isEmpty()) {
                requestBuilder.url(myReqInfo.getUrl() + myReqInfo.buildUrlParams(myReqInfo.getParamsMap()));
            }

            requestBuilder.post(RequestBody.create(MediaType.parse(myReqInfo.getPostStringContentType()), myReqInfo.getPostString()));
            return true;
        }
        return false;
    }

    private boolean isPostForm(MyReqInfo myReqInfo, Request.Builder requestBuilder) {
        if (isIncludeFile(myReqInfo)) {
            return false;
        }

        Map<String, Object> paramsMap = myReqInfo.getParamsMap();

        if (CollectionsUtil.isMapAvaliable(paramsMap)) {
            FormBody.Builder formBodyBuilder = new FormBody.Builder();

            for (Map.Entry<String, Object> entry : paramsMap.entrySet()) {
                formBodyBuilder.add(entry.getKey(), String.valueOf(entry.getValue()));
            }
            requestBuilder.post(formBodyBuilder.build());
        }

        return true;
    }

    private boolean isPostMultiForm(MyReqInfo myReqInfo, Request.Builder requestBuilder, IMyHttpHandler iMyHttpHandler) {

        Map<String, Object> paramsMap = myReqInfo.getParamsMap();

        if (CollectionsUtil.isMapAvaliable(paramsMap)) {

            MultipartBody.Builder multiBuilder = new MultipartBody.Builder();
            multiBuilder.setType(MultipartBody.FORM);

            for (Map.Entry<String, Object> entry : paramsMap.entrySet()) {

                if (entry.getValue() == null) {
                    continue;
                }

                if (entry.getValue() instanceof File) {

                    MyUploadFile myUploadFile = new MyUploadFile((File) entry.getValue());

                    if (myUploadFile.isExist()) {
                        multiBuilder.addFormDataPart(entry.getKey(), myUploadFile.getName(), RequestBody.create(myUploadFile.getMediaType(), myUploadFile.getFile()));
                    } else {
                        continue;
                    }

                } else {
                    multiBuilder.addFormDataPart(entry.getKey(), String.valueOf(entry.getValue()));
                }
            }

            MultipartBody multipartBody = multiBuilder.build();
            MyProgressRequestBody myProgressRequestBody = getProgressRequestBody(iMyHttpHandler, multipartBody);
            requestBuilder.post(myProgressRequestBody);
        }

        return true;
    }

    private boolean isIncludeFile(MyReqInfo myReqInfo) {

        Map<String, Object> paramsMap = myReqInfo.getParamsMap();

        if (CollectionsUtil.isMapAvaliable(paramsMap)) {
            for (Map.Entry<String, Object> entry : paramsMap.entrySet()) {
                if (entry.getValue() instanceof File) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isGet(MyReqInfo myReqInfo, Request.Builder builder) {
        if (myReqInfo.isGet()) {

            builder.get().url(myReqInfo.getUrl() + myReqInfo.buildUrlParams(myReqInfo.getParamsMap()));

            return true;
        }
        return false;
    }


    private Request.Builder buildHeader(MyReqInfo myReqInfo, Request.Builder builder) {
        Map<String, List<String>> headers = myReqInfo.getHeadersMap();

        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {

            List<String> values = entry.getValue();
            String key = entry.getKey();

            if (CollectionsUtil.isListAvaliable(values)) {
                for (String value : values) {
                    builder.addHeader(key, value);
                }
            }
        }
        return builder;
    }

    protected MyProgressRequestBody getProgressRequestBody(IMyHttpHandler iMyHttpHandler, MultipartBody multipartBody) {
        return new MyProgressRequestBody(multipartBody, iMyHttpHandler);
    }

    protected MyHttpCallBack getHttpCallBack(MyReqInfo myReqInfo, IMyHttpHandler iMyHttpHandler) {
        return new MyHttpCallBack(myReqInfo, iMyHttpHandler);
    }

}
