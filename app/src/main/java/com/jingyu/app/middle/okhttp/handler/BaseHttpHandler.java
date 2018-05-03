package com.jingyu.app.middle.okhttp.handler;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.KeyEvent;

import com.jingyu.android.common.log.Logger;
import com.jingyu.app.MainActivity;
import com.jingyu.app.middle.okhttp.IRespMsgCode;
import com.jingyu.app.middle.okhttp.loading.DialogManager;
import com.jingyu.app.middle.okhttp.loading.HttpLoadingDialog;
import com.jingyu.java.myokhttp.MyHttpCallBack;
import com.jingyu.java.myokhttp.handler.IMyHttpHandler;
import com.jingyu.java.myokhttp.req.MyReqInfo;
import com.jingyu.java.myokhttp.resp.MyRespInfo;
import com.jingyu.java.mytool.Constants;
import com.jingyu.java.mytool.util.IOUtil;
import com.jingyu.java.mytool.util.StringUtil;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fengjingyu@foxmail.com
 */
public abstract class BaseHttpHandler<T> implements IMyHttpHandler<T> {

    private Activity activityContext;

    protected boolean isShowDialog;

    //TODO 配置服务端定义的成功状态码
    public static final String REQ_SUCCESS = "1";

    public String parse2String(MyReqInfo myReqInfo, MyRespInfo myRespInfo, InputStream inputStream) {
        // 只能读一次，否则异常
        try {
            myRespInfo.setDataBytes(IOUtil.getBytes(inputStream));
            return new String(myRespInfo.getDataBytes(), Constants.UTF8);
        } catch (Exception e) {
            throw new RuntimeException(myReqInfo.getUrl() + MyHttpCallBack.LINE + e);
        }
    }

    public File parse2File(MyReqInfo myReqInfo, InputStream inputStream, File file) {
        if (IOUtil.inputStream2File(inputStream, file)) {
            return file;
        } else {
            throw new RuntimeException("inputStream2file异常 " + MyHttpCallBack.LINE + "file = " + file + MyHttpCallBack.LINE + myReqInfo.getUrl());
        }
    }

    public BaseHttpHandler(Activity activityContext) {
        super();
        this.activityContext = activityContext;
        this.isShowDialog = true;
    }

    public BaseHttpHandler(Activity activityContext, boolean isShowDialog) {
        this.activityContext = activityContext;
        this.isShowDialog = isShowDialog;
    }

    public BaseHttpHandler() {
    }

    //TODO 统一配置请求头
    public Map<String, List<String>> getCommonHeaders(String tag, Map<String, List<String>> headers) {
        if (headers == null) {
            headers = new HashMap<>();
        }
        headers.put("_p", Arrays.asList("1"));
        return headers;
    }

    //TODO 统一配置加密参数
    public Map<String, Object> getCommonEncryptParams(String tag, Map<String, Object> paramsMap) {
        if (paramsMap == null) {
            paramsMap = new HashMap<>();
        }
        paramsMap.put("testKey0", "java");
        paramsMap.put("testKey3", "c");
        paramsMap.put("testKey6", "c++");
        return paramsMap;
    }

    @Override
    public MyReqInfo onReadySendRequest(MyReqInfo myReqInfo) {
//        Logger.d(myReqInfo);
//        Map<String, List<String>> newHeaders = getCommonHeaders(myReqInfo.getHeadersMap());
//        Map<String, Object> newParams = getCommonEncryptParams(myReqInfo.getParamsMap());
//
//        MyReqInfo newMyReqInfo = new MyReqInfo.Builder(myReqInfo).headersMap(newHeaders).paramsMap(newParams).builder();
//        showDialog();
//        Logger.d(newMyReqInfo);
//        return newMyReqInfo;
        return myReqInfo;
    }

    /**
     * 如果显示dialog，则isShowDialog为true 且 activityContext非空
     */
    private void showDialog() {
        if (activityContext != null && isShowDialog) {
            DialogManager dialogManager = DialogManager.getInstance(activityContext);
            // TODO 设置dialog
            Dialog dialog = new HttpLoadingDialog(activityContext);
            addDialogListener(dialog);
            dialogManager.setDialog(dialog);
            dialogManager.mayShow(toString());
        }
    }

    /**
     * 解析是否成功的规则，根据项目的json而定
     */
    @Override
    public boolean onMatchAppCode(MyReqInfo myReqInfo, MyRespInfo myRespInfo, T resultBean) {
        //TODO 解析规则
        if (resultBean instanceof IRespMsgCode) {
            if (StringUtil.equals(((IRespMsgCode) resultBean).getCode(), REQ_SUCCESS)) {
                return true;
            } else {
                statusCodeWrongToast(resultBean);
                return false;
            }
        } else {
            Logger.e(MyHttpCallBack.TAG_HTTP, "onMatchAppStatusCode()中的返回结果不是IHttpRespInfo类型", null);
            throw new RuntimeException("onMatchAppStatusCode()中的返回结果不是IHttpRespInfo类型");
        }
    }

    public void statusCodeWrongToast(T resultBean) {
        Logger.shortToast(((IRespMsgCode) resultBean).getMsg());
    }

    @Override
    public void onFailure(MyReqInfo myReqInfo, MyRespInfo myRespInfo) {
        Logger.shortToast("网络出错啦");
    }

    @Override
    public void onFinally(MyReqInfo myReqInfo, MyRespInfo myRespInfo) {
        closeDialog(isShowDialog);
    }

    private void closeDialog(boolean isShowDialog) {
        if (activityContext != null && isShowDialog) {
            DialogManager.getInstance(activityContext).mayClose(toString());
        }
    }

    public final void addDialogListener(Dialog dialog) {
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    closeDialog(true);
                    //TODO
                    if (activityContext != null && !(activityContext instanceof MainActivity)) {
                        activityContext.finish();
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onUploadProgress(long bytesWritten, long totalSize) {

    }

    @Override
    public void onParseException(MyReqInfo myReqInfo, MyRespInfo myRespInfo) {

    }

    @Override
    public void onAppCodeException(MyReqInfo myReqInfo, MyRespInfo myRespInfo, T resultBean) {

    }

    @Override
    public void onSuccess(MyReqInfo myReqInfo, MyRespInfo myRespInfo, T resultBean) {

    }
}
