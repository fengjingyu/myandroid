package com.jingyu.java.myokhttp.handler.control;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.jingyu.java.myokhttp.handler.IMyHttpHandler;
import com.jingyu.java.myokhttp.req.MyReqInfo;
import com.jingyu.java.myokhttp.resp.MyRespInfo;
import com.jingyu.java.myokhttp.resp.MyRespType;
import com.jingyu.java.mytool.util.CollectionsUtil;
import com.jingyu.java.mytool.util.IOUtil;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Response;

/**
 * @author fengjingyu@foxmail.com
 */
public class MyHttpHandlerController<T> implements Callback {
    /**
     * 可查看如url 返回的json等
     */
    public static final String TAG_HTTP = "myhttp";
    public static final String LINE = "@@@@@@";

    private MyRespInfo myRespInfo = new MyRespInfo();
    private MyReqInfo myReqInfo;
    private IMyHttpHandler<T> IMyHttpHandler;

    public MyHttpHandlerController(MyReqInfo myReqInfo, IMyHttpHandler<T> IMyHttpHandler) {
        this.myReqInfo = myReqInfo;
        this.IMyHttpHandler = IMyHttpHandler;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        myRespInfo.setMyRespType(MyRespType.FAILURE);
        myRespInfo.setThrowable(e);
        myRespInfo.setHttpCode(0);

        log(TAG_HTTP, myReqInfo + LINE + "onFailure() exception" + LINE + myRespInfo.getThrowable());

        handleFail();
    }

    @Override
    public void onResponse(Call call, final Response response) throws IOException {
        myRespInfo.setHttpCode(response.code());
        myRespInfo.setRespHeaders(headers2Map(response.headers()));
        myRespInfo.setThrowable(null);
        myRespInfo.setMyRespType(MyRespType.SUCCESS_WAITING_PARSE);

        log(TAG_HTTP, "onResponse()" + LINE + myReqInfo.getUrl() + LINE + "httpCode  " + myRespInfo.getHttpCode());
        printHeaderInfo(myRespInfo.getRespHeaders());

        if (IMyHttpHandler != null) {
            long totalSize = response.body().contentLength();
            InputStream inputStream = response.body().byteStream();
            if (myReqInfo.isDownload()) {
                IMyHttpHandler.onDownloadInfo(myReqInfo, myRespInfo, inputStream, totalSize);
                handleSuccess(null, true);
            } else {
                // 只能读一次，否则异常
                // byte[] bytes = response.body().bytes();
                byte[] bytes = IOUtil.getBytes(inputStream);
                myRespInfo.setDataBytes(bytes);
                myRespInfo.setDataString(bytes);
                handleSuccess(parse(), false);
            }
        } else {
            handleSuccess(null, false);
        }
    }

    private Map<String, List<String>> headers2Map(Headers headers) {
        return headers.toMultimap();
    }

    protected void handleFail() {
        runOnSpecifiedThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (IMyHttpHandler != null) {
                        IMyHttpHandler.onFailure(myReqInfo, myRespInfo);
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                    log(TAG_HTTP, myReqInfo + LINE + "handleFail()-->onFailure（） 异常了" + e1);
                } finally {
                    try {
                        end();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                        log(TAG_HTTP, myReqInfo + LINE + "handleFail()-->end（） 异常了" + e1);
                    }
                }
            }
        });
    }

    protected void handleSuccess(final T resultBean, final boolean isDownload) {
        runOnSpecifiedThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (IMyHttpHandler != null && !isDownload) {
                        if (resultBean != null) {

                            // http请求成功，解析成功，接下来判断状态码
                            if (IMyHttpHandler.onMatchAppStatusCode(myReqInfo, myRespInfo, resultBean)) {
                                myRespInfo.setMyRespType(MyRespType.SUCCESS_ALL);
                                // 项目的该接口的状态码正确
                                IMyHttpHandler.onSuccessAll(myReqInfo, myRespInfo, resultBean);
                            } else {
                                // http请求成功，解析成功，项目的该接口的状态码有误
                                myRespInfo.setMyRespType(MyRespType.SUCCESS_BUT_CODE_WRONG);
                                IMyHttpHandler.onSuccessButCodeWrong(myReqInfo, myRespInfo, resultBean);
                            }
                        } else {
                            // http请求成功，但是解析失败或者没解析
                            myRespInfo.setMyRespType(MyRespType.SUCCESS_BUT_PARSE_WRONG);
                            IMyHttpHandler.onSuccessButParseWrong(myReqInfo, myRespInfo);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log(TAG_HTTP, myReqInfo + LINE + "handleSuccess（） 异常了" + e);
                } finally {
                    try {
                        end();
                    } catch (Exception e) {
                        e.printStackTrace();
                        log(TAG_HTTP, myReqInfo + LINE + "handleSuccess-->end（） 异常了" + e);
                    }
                }
            }
        });
    }

    protected void printHeaderInfo(Map<String, List<String>> headers) {
        for (Map.Entry<String, List<String>> header : headers.entrySet()) {

            List<String> values = header.getValue();

            if (CollectionsUtil.isListAvaliable(values)) {
                log(TAG_HTTP, "headers-->" + header.getKey() + "=" + Arrays.toString(values.toArray()));
            }
        }
    }

    protected void end() {
        if (IMyHttpHandler != null) {
            IMyHttpHandler.onEnd(myReqInfo, myRespInfo);
        }
    }

    protected T parse() {
        try {

            log(TAG_HTTP, "to parse()" + LINE + myReqInfo.getUrl() + "返回的" + myRespInfo.getDataString());

            // 如果解析失败一定得返回null或者crash
            T resultBean = IMyHttpHandler.onParse(myReqInfo, myRespInfo);

            if (resultBean == null) {
                runOnSpecifiedThread(new Runnable() {
                    @Override
                    public void run() {
                        log(TAG_HTTP, "解析数据失败" + LINE + myReqInfo + LINE + myRespInfo.getDataString());
                    }
                });
            }

            return resultBean;

        } catch (final Exception e) {
            e.printStackTrace();
            runOnSpecifiedThread(new Runnable() {
                @Override
                public void run() {
                    log(TAG_HTTP, "解析数据异常" + LINE + myReqInfo + LINE + myRespInfo.getDataString() + e);
                }
            });
            return null;
        }
    }

    /**
     * 在指定的线程运行
     * 比如：在android使用okhttp库时，回调是在子线程的，这里可以统一处理到主线程回调
     */
    public void runOnSpecifiedThread(Runnable runnable) {
        //Handler mHandler = new Handler(Looper.getMainLooper());
        //mHandler.post(runnable)
        if (runnable != null) {
            runnable.run();
        }
    }

    public void log(String tag, String msg) {
        System.out.println(tag + "::" + msg);
    }
}
