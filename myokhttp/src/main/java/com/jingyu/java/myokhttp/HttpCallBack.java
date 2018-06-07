package com.jingyu.java.myokhttp;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.jingyu.java.myokhttp.handler.IHttpHandler;
import com.jingyu.java.myokhttp.log.LogUtil;
import com.jingyu.java.myokhttp.req.ReqInfo;
import com.jingyu.java.myokhttp.resp.RespInfo;
import com.jingyu.java.myokhttp.resp.RespType;
import com.jingyu.java.mytool.util.CollectionUtil;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @author fengjingyu@foxmail.com
 */
public class HttpCallBack<T> implements Callback {
    /**
     * 可查看如url 返回的数据等
     */
    public static final String TAG_HTTP = "myhttp";
    public static final String LINE = "@@@@@@";

    private RespInfo respInfo;
    private ReqInfo reqInfo;
    private IHttpHandler<T> iHttpHandler;
    private T resultBean;

    public HttpCallBack(ReqInfo reqInfo, IHttpHandler<T> iHttpHandler) {
        this.reqInfo = reqInfo;
        this.iHttpHandler = iHttpHandler;
        this.respInfo = new RespInfo();
    }

    @Override
    public void onFailure(Call call, IOException e) {
        respInfo.setRespType(RespType.FAILURE);
        respInfo.setThrowable(e);
        respInfo.setHttpCode(0);
        LogUtil.i(TAG_HTTP, reqInfo + LINE + "onFailure()" + LINE + respInfo.getThrowable());

        runOnSpecifiedThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (iHttpHandler != null) {
                        iHttpHandler.onFailure(reqInfo, respInfo);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtil.i(TAG_HTTP, reqInfo + LINE + "onFailure（） 异常了" + e);
                } finally {
                    try {
                        handleFinally();
                    } catch (Exception e) {
                        e.printStackTrace();
                        LogUtil.i(TAG_HTTP, reqInfo + LINE + "onFailure()-->handleFinally（） 异常了" + e);
                    }
                }
            }
        });
    }

    @Override
    public void onResponse(Call call, Response response) {
        respInfo.setHttpCode(response.code());
        respInfo.setRespHeaders(response.headers().toMultimap());
        respInfo.setThrowable(null);

        LogUtil.i(TAG_HTTP, "onResponse()" + LINE + reqInfo.getUrl() + LINE + "httpCode = " + respInfo.getHttpCode());
        printHeaderInfo(respInfo.getRespHeaders());

        try {
            if (iHttpHandler != null) {
                InputStream inputStream = response.body().byteStream();
                long totalSize = response.body().contentLength();
                resultBean = iHttpHandler.onParse(reqInfo, respInfo, inputStream, totalSize);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultBean = null;
        }

        runOnSpecifiedThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (iHttpHandler != null) {
                        // 有传入回调iMyHttpHandler; 如果没有传入回调iMyHttpHandler, 则直接调用finally{ }
                        if (resultBean != null) {
                            // 解析成功
                            if (iHttpHandler.onMatchAppCode(reqInfo, respInfo, resultBean)) {
                                // 项目接口的状态码正确
                                respInfo.setRespType(RespType.SUCCESS);
                                iHttpHandler.onSuccess(reqInfo, respInfo, resultBean);
                            } else {
                                // 项目接口的状态码有误
                                respInfo.setRespType(RespType.APP_CODE_EXCEPTION);
                                iHttpHandler.onAppCodeException(reqInfo, respInfo, resultBean);
                            }
                        } else {
                            // 解析失败
                            respInfo.setRespType(RespType.PARSE_EXCEPTION);
                            iHttpHandler.onParseException(reqInfo, respInfo);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtil.i(TAG_HTTP, reqInfo + LINE + "onResponse（） 异常了" + e);
                } finally {
                    try {
                        handleFinally();
                    } catch (Exception e) {
                        e.printStackTrace();
                        LogUtil.i(TAG_HTTP, reqInfo + LINE + "onResponse-->handleFinally（） 异常了" + e);
                    }
                }
            }
        });
    }

    protected void printHeaderInfo(Map<String, List<String>> headers) {
        for (Map.Entry<String, List<String>> header : headers.entrySet()) {
            List<String> values = header.getValue();
            if (CollectionUtil.isNotEmpty(values)) {
                LogUtil.i(TAG_HTTP, "headers-->" + header.getKey() + "=" + Arrays.toString(values.toArray()));
            }
        }
    }

    protected void handleFinally() {
        if (iHttpHandler != null) {
            iHttpHandler.onFinally(reqInfo, respInfo);
        }
    }

    /**
     * 在指定的线程运行
     */
    public void runOnSpecifiedThread(Runnable runnable) {
        //android
        //Handler mHandler = new Handler(Looper.getMainLooper());
        //mHandler.post(runnable)
        if (runnable != null) {
            runnable.run();
        }
    }
}
