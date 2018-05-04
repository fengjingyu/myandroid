package com.jingyu.app.middle.okhttp.handler;

import android.app.Activity;

import com.jingyu.android.common.bean.JsonBean;
import com.jingyu.android.common.bean.JsonParse;
import com.jingyu.app.middle.okhttp.IRespMsgCode;
import com.jingyu.java.myokhttp.req.MyReqInfo;
import com.jingyu.java.myokhttp.resp.MyRespInfo;

import java.io.InputStream;

/**
 * @author fengjingyu@foxmail.com
 * @description 适用于不需要建模型的接口（如添加收藏等无ui数据的接口）
 */
public class JsonHttpHandler extends BaseHttpHandler<JsonHttpHandler.MyJsonBean> {

    public JsonHttpHandler(Activity activityContext, boolean isShowDialog) {
        super(activityContext, isShowDialog);
    }

    public JsonHttpHandler(Activity activity) {
        super(activity);
    }

    public JsonHttpHandler() {
    }

    @Override
    public MyJsonBean onParse(MyReqInfo myReqInfo, MyRespInfo myRespInfo, InputStream inputStream, long totalSize) {
        return JsonParse.getJsonParseData(parse(myReqInfo, myRespInfo, inputStream, totalSize), MyJsonBean.class);
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
