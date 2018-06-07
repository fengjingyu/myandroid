package com.jingyu.java.myokhttp.handler;

import com.jingyu.java.myokhttp.req.ReqInfo;
import com.jingyu.java.myokhttp.resp.RespInfo;
import com.jingyu.java.mytool.lib.gson.GsonUtil;

import java.io.InputStream;

/**
 * @author fengjingyu@foxmail.com
 * @description gson解析的http回调类
 */
public class GsonHttpHandler<T> extends BaseHttpHandler<T> {

    private Class<T> clazz;

    public GsonHttpHandler(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T onParse(ReqInfo reqInfo, RespInfo respInfo, InputStream inputStream, long totalSize) {
        return GsonUtil.parseJson(parse(reqInfo, respInfo, inputStream, totalSize), clazz);
    }
}
