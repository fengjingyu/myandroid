package com.jingyu.app.middle.okhttp.control;

import android.os.Handler;
import android.os.Looper;

import com.jingyu.java.myokhttp.MyHttpCallBack;
import com.jingyu.java.myokhttp.handler.IMyHttpHandler;
import com.jingyu.java.myokhttp.req.MyReqInfo;

/**
 * Created by jingyu on 2018/2/21.
 */

public class HttpCallBack extends MyHttpCallBack {

    Handler mHandler = new Handler(Looper.getMainLooper());

    public HttpCallBack(MyReqInfo myReqInfo, IMyHttpHandler iMyHttpHandler) {
        super(myReqInfo, iMyHttpHandler);
    }

    @Override
    public void runOnSpecifiedThread(Runnable runnable) {
        if (runnable != null) {
            mHandler.post(runnable);
        }
    }
}
