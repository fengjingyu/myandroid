package com.jingyu.java.myokhttp.handler;

import com.jingyu.java.myokhttp.req.ReqInfo;
import com.jingyu.java.myokhttp.resp.RespInfo;

import java.io.InputStream;

/**
 * @author fengjingyu@foxmail.com
 */
public class StringHttpHandler extends BaseHttpHandler<String> {

    @Override
    public String onParse(ReqInfo reqInfo, RespInfo respInfo, InputStream inputStream, long totalSize) {
        return parse(reqInfo, respInfo, inputStream, totalSize);
    }
}
