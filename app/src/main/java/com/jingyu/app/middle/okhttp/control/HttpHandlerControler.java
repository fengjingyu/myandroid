package com.jingyu.app.middle.okhttp.control;

import android.os.Handler;
import android.os.Looper;
import com.jingyu.android.common.log.Logger;
import com.jingyu.java.myokhttp.handler.IMyHttpHandler;
import com.jingyu.java.myokhttp.handler.control.MyHttpHandlerController;
import com.jingyu.java.myokhttp.req.MyReqInfo;

/**
 * Created by jingyu on 2018/2/21.
 */

public class HttpHandlerControler extends MyHttpHandlerController {

    Handler mHandler = new Handler(Looper.getMainLooper());

    public HttpHandlerControler(MyReqInfo myReqInfo, IMyHttpHandler iMyHttpHandler) {
        super(myReqInfo, iMyHttpHandler);
    }

    @Override
    public void runOnSpecifiedThread(Runnable runnable) {
        if (runnable != null) {
            mHandler.post(runnable);
        }
    }

    @Override
    public void log(String tag, String msg) {
        Logger.i(tag, msg);
    }
}
