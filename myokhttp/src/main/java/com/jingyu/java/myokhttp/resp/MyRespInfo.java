package com.jingyu.java.myokhttp.resp;

import com.jingyu.java.mytool.bean.CloneBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fengjingyu@foxmail.com
 * http返回的信息
 */
public class MyRespInfo extends CloneBean {

    private MyRespType myRespType;

    private int httpCode;

    private Map<String, List<String>> respHeaders;

    /**
     * success 为 null
     */
    private Throwable throwable;

    public MyRespInfo(MyRespType myRespType, int httpCode, Map<String, List<String>> respHeaders, Throwable throwable) {
        this.myRespType = myRespType;
        this.httpCode = httpCode;
        this.respHeaders = respHeaders;
        this.throwable = throwable;
    }

    public MyRespInfo() {
    }

    public MyRespType getMyRespType() {
        return myRespType;
    }

    public void setMyRespType(MyRespType myRespType) {
        this.myRespType = myRespType;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public Map<String, List<String>> getRespHeaders() {
        if (respHeaders == null) {
            respHeaders = new HashMap<>();
        }
        return respHeaders;
    }

    public void setRespHeaders(Map<String, List<String>> respHeaders) {
        if (respHeaders == null) {
            respHeaders = new HashMap<>();
        }
        this.respHeaders = respHeaders;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
