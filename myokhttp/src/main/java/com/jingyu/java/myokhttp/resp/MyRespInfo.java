package com.jingyu.java.myokhttp.resp;

import com.jingyu.java.mytool.Constants;
import com.jingyu.java.mytool.bean.CloneBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fengjingyu@foxmail.com
 * http返回的信息
 */
public class MyRespInfo extends CloneBean {

    /**
     * 返回的成功失败的类型，目前有5个类型
     */
    private MyRespType myRespType;

    private int httpCode;

    private Map<String, List<String>> respHeaders;

    private byte[] dataBytes;

    private String dataString;

    /**
     * throwable null为success，非空为fail
     */
    private Throwable throwable;

    public MyRespInfo(MyRespType myRespType, int httpCode, Map<String, List<String>> respHeaders, byte[] dataBytes, String dataString, Throwable throwable) {
        this.myRespType = myRespType;
        this.httpCode = httpCode;
        this.respHeaders = respHeaders;
        this.dataBytes = dataBytes;
        this.dataString = dataString;
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

    public byte[] getDataBytes() {
        return dataBytes;
    }

    public void setDataBytes(byte[] dataBytes) {
        this.dataBytes = dataBytes;
    }

    public String getDataString() {
        return dataString;
    }

    public void setDataString(String dataString) {
        this.dataString = dataString;
    }

    public void setDataString(byte[] bytes) {
        try {
            dataString = new String(bytes, Constants.ENCODING_UTF8);
        } catch (Exception e) {
            e.printStackTrace();
            dataString = null;
        }
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public boolean isSuccessAll() {
        return MyRespType.SUCCESS_ALL == myRespType;
    }
}
