package com.jingyu.app.middle.okhttp.control;

import android.os.Handler;
import android.os.Looper;

import com.jingyu.java.myhttp.HttpCallBack;
import com.jingyu.java.myhttp.handler.IHttpHandler;
import com.jingyu.java.myhttp.req.ReqInfo;

/**
 * Created by jingyu on 2018/2/21.
 */

public class MainThreadCallBack extends HttpCallBack {

    private static Handler mHandler = new Handler(Looper.getMainLooper());

    public MainThreadCallBack(ReqInfo reqInfo, IHttpHandler iHttpHandler) {
        super(reqInfo, iHttpHandler);
    }

    @Override
    public void runOnSpecifiedThread(Runnable runnable) {
        if (runnable != null) {
            mHandler.post(runnable);
        }
    }
}
