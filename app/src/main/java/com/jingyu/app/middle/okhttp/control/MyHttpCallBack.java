package com.jingyu.app.middle.okhttp.control;

import android.os.Handler;
import android.os.Looper;

import com.jingyu.java.myokhttp.HttpCallBack;
import com.jingyu.java.myokhttp.handler.IHttpHandler;
import com.jingyu.java.myokhttp.req.ReqInfo;

/**
 * Created by jingyu on 2018/2/21.
 */

public class MyHttpCallBack extends HttpCallBack {

    Handler mHandler = new Handler(Looper.getMainLooper());

    public MyHttpCallBack(ReqInfo reqInfo, IHttpHandler iHttpHandler) {
        super(reqInfo, iHttpHandler);
    }

    @Override
    public void runOnSpecifiedThread(Runnable runnable) {
        if (runnable != null) {
            mHandler.post(runnable);
        }
    }
}
