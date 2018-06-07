package com.jingyu.java.myokhttp.resp;

import com.jingyu.java.mytool.bean.CloneBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fengjingyu@foxmail.com
 * http返回的信息
 */
public class RespInfo extends CloneBean {

    private RespType respType;

    private int httpCode;

    private Map<String, List<String>> respHeaders;

    /**
     * success 为 null
     */
    private Throwable throwable;

    public RespInfo(RespType respType, int httpCode, Map<String, List<String>> respHeaders, Throwable throwable) {
        this.respType = respType;
        this.httpCode = httpCode;
        this.respHeaders = respHeaders;
        this.throwable = throwable;
    }

    public RespInfo() {
    }

    public RespType getRespType() {
        return respType;
    }

    public void setRespType(RespType respType) {
        this.respType = respType;
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
