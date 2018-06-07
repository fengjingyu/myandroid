package com.jingyu.app.middle.okhttp.handler;

import android.app.Activity;

import com.jingyu.android.common.bean.JsonBean;
import com.jingyu.android.common.bean.JsonParse;
import com.jingyu.app.middle.okhttp.IRespMsgCode;
import com.jingyu.java.myokhttp.req.ReqInfo;
import com.jingyu.java.myokhttp.resp.RespInfo;

import java.io.InputStream;

/**
 * @author fengjingyu@foxmail.com
 * @description 适用于不需要建模型的接口（如添加收藏等无ui数据的接口）
 */
public class MyJsonHttpHandler extends MyBaseHttpHandler<MyJsonHttpHandler.MyJsonBean> {

    public MyJsonHttpHandler(Activity activityContext, boolean isShowDialog) {
        super(activityContext, isShowDialog);
    }

    public MyJsonHttpHandler(Activity activity) {
        super(activity);
    }

    public MyJsonHttpHandler() {
    }

    @Override
    public MyJsonBean onParse(ReqInfo reqInfo, RespInfo respInfo, InputStream inputStream, long totalSize) {
        return JsonParse.getJsonParseData(parse(reqInfo, respInfo, inputStream, totalSize), MyJsonBean.class);
    }

    public class MyJsonBean extends JsonBean implements IRespMsgCode {

        //todo
        @Override
        public String getCode() {
            return getString("code");
        }

        //todo
        @Override
        public String getMsg() {
            return getString("msg");
        }
    }
}
