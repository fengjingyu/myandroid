package com.jingyu.java.myokhttp;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.jingyu.java.myokhttp.handler.IMyHttpHandler;
import com.jingyu.java.myokhttp.log.LogUtil;
import com.jingyu.java.myokhttp.req.MyReqInfo;
import com.jingyu.java.myokhttp.resp.MyRespInfo;
import com.jingyu.java.myokhttp.resp.MyRespType;
import com.jingyu.java.mytool.util.CollectionsUtil;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @author fengjingyu@foxmail.com
 */
public class MyHttpCallBack<T> implements Callback {
    /**
     * 可查看如url 返回的数据等
     */
    public static final String TAG_HTTP = "myhttp";
    public static final String LINE = "@@@@@@";

    private MyRespInfo myRespInfo;
    private MyReqInfo myReqInfo;
    private IMyHttpHandler<T> iMyHttpHandler;
    private T resultBean;

    public MyHttpCallBack(MyReqInfo myReqInfo, IMyHttpHandler<T> iMyHttpHandler) {
        this.myReqInfo = myReqInfo;
        this.iMyHttpHandler = iMyHttpHandler;
        this.myRespInfo = new MyRespInfo();
    }

    @Override
    public void onFailure(Call call, IOException e) {
        myRespInfo.setMyRespType(MyRespType.FAILURE);
        myRespInfo.setThrowable(e);
        myRespInfo.setHttpCode(0);
        LogUtil.i(TAG_HTTP, myReqInfo + LINE + "onFailure()" + LINE + myRespInfo.getThrowable());

        runOnSpecifiedThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (iMyHttpHandler != null) {
                        iMyHttpHandler.onFailure(myReqInfo, myRespInfo);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtil.i(TAG_HTTP, myReqInfo + LINE + "onFailure（） 异常了" + e);
                } finally {
                    try {
                        handleFinally();
                    } catch (Exception e) {
                        e.printStackTrace();
                        LogUtil.i(TAG_HTTP, myReqInfo + LINE + "onFailure()-->handleFinally（） 异常了" + e);
                    }
                }
            }
        });
    }

    @Override
    public void onResponse(Call call, Response response) {
        myRespInfo.setHttpCode(response.code());
        myRespInfo.setRespHeaders(response.headers().toMultimap());
        myRespInfo.setThrowable(null);

        LogUtil.i(TAG_HTTP, "onResponse()" + LINE + myReqInfo.getUrl() + LINE + "httpCode = " + myRespInfo.getHttpCode());
        printHeaderInfo(myRespInfo.getRespHeaders());

        try {
            if (iMyHttpHandler != null) {
                InputStream inputStream = response.body().byteStream();
                long totalSize = response.body().contentLength();
                resultBean = iMyHttpHandler.onParse(myReqInfo, myRespInfo, inputStream, totalSize);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultBean = null;
        }

        runOnSpecifiedThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (iMyHttpHandler != null) {
                        // 有传入回调iMyHttpHandler; 如果没有传入回调iMyHttpHandler, 则直接调用finally{ }
                        if (resultBean != null) {
                            // 解析成功
                            if (iMyHttpHandler.onMatchAppCode(myReqInfo, myRespInfo, resultBean)) {
                                // 项目接口的状态码正确
                                myRespInfo.setMyRespType(MyRespType.SUCCESS);
                                iMyHttpHandler.onSuccess(myReqInfo, myRespInfo, resultBean);
                            } else {
                                // 项目接口的状态码有误
                                myRespInfo.setMyRespType(MyRespType.APP_CODE_EXCEPTION);
                                iMyHttpHandler.onAppCodeException(myReqInfo, myRespInfo, resultBean);
                            }
                        } else {
                            // 解析失败
                            myRespInfo.setMyRespType(MyRespType.PARSE_EXCEPTION);
                            iMyHttpHandler.onParseException(myReqInfo, myRespInfo);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtil.i(TAG_HTTP, myReqInfo + LINE + "onResponse（） 异常了" + e);
                } finally {
                    try {
                        handleFinally();
                    } catch (Exception e) {
                        e.printStackTrace();
                        LogUtil.i(TAG_HTTP, myReqInfo + LINE + "onResponse-->handleFinally（） 异常了" + e);
                    }
                }
            }
        });
    }

    protected void printHeaderInfo(Map<String, List<String>> headers) {
        for (Map.Entry<String, List<String>> header : headers.entrySet()) {
            List<String> values = header.getValue();
            if (CollectionsUtil.isListAvaliable(values)) {
                LogUtil.i(TAG_HTTP, "headers-->" + header.getKey() + "=" + Arrays.toString(values.toArray()));
            }
        }
    }

    protected void handleFinally() {
        if (iMyHttpHandler != null) {
            iMyHttpHandler.onFinally(myReqInfo, myRespInfo);
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
