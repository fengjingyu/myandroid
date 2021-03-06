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
import com.jingyu.java.myhttp.HttpConst;
import com.jingyu.java.myhttp.handler.IHttpHandler;
import com.jingyu.java.myhttp.req.ReqInfo;
import com.jingyu.java.myhttp.resp.RespInfo;
import com.jingyu.java.mytool.util.StringUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fengjingyu@foxmail.com
 */
public abstract class MyBaseHttpHandler<T> implements IHttpHandler<T> {

    private Activity activityContext;

    protected boolean isShowDialog;

    //TODO 配置服务端定义的成功状态码
    public static final String REQ_SUCCESS = "1";

    public MyBaseHttpHandler(Activity activityContext) {
        super();
        this.activityContext = activityContext;
        this.isShowDialog = true;
    }

    public MyBaseHttpHandler(Activity activityContext, boolean isShowDialog) {
        this.activityContext = activityContext;
        this.isShowDialog = isShowDialog;
    }

    public MyBaseHttpHandler() {
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
    public ReqInfo onReadySendRequest(ReqInfo reqInfo) {
//        Logger.d(myReqInfo);
//        Map<String, List<String>> newHeaders = getCommonHeaders(myReqInfo.getHeadersMap());
//        Map<String, Object> newParams = getCommonEncryptParams(myReqInfo.getParamsMap());
//
//        MyReqInfo newMyReqInfo = new MyReqInfo.Builder(myReqInfo).headersMap(newHeaders).paramsMap(newParams).builder();
//        showDialog();
//        Logger.d(newMyReqInfo);
//        return newMyReqInfo;
        return reqInfo;
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
    public boolean onMatchAppCode(ReqInfo reqInfo, RespInfo respInfo, T resultBean) {
        //TODO 解析规则
        if (resultBean instanceof IRespMsgCode) {
            if (StringUtil.equals(((IRespMsgCode) resultBean).getCode(), REQ_SUCCESS)) {
                return true;
            } else {
                Logger.shortToast(((IRespMsgCode) resultBean).getMsg());
                return false;
            }
        } else {
            Logger.e(HttpConst.TAG_HTTP, "onMatchAppStatusCode()中的返回结果不是IHttpRespInfo类型", null);
            throw new RuntimeException("onMatchAppStatusCode()中的返回结果不是IHttpRespInfo类型");
        }
    }

    @Override
    public void onFailure(ReqInfo reqInfo, RespInfo respInfo) {
        Logger.shortToast("网络出错啦");
    }

    @Override
    public void onFinally(ReqInfo myReqInfo, RespInfo myRespInfo) {
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
    public void onParseException(ReqInfo reqInfo, RespInfo respInfo) {

    }

    @Override
    public void onAppCodeException(ReqInfo reqInfo, RespInfo respInfo, T resultBean) {

    }

    @Override
    public void onSuccess(ReqInfo reqInfo, RespInfo respInfo, T resultBean) {

    }
}
