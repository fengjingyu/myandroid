package com.jingyu.java.myokhttp.resp;

/**
 * @author fengjingyu@foxmail.com
 * http的响应类型
 */
public enum MyRespType {

    /**
     * FAILURE: 网络请求失败
     * PARSE_EXCEPTION：网络请求成功，解析失败
     * APP_CODE_EXCEPTION：网络请求成功，解析成功，项目业务逻辑的状态码有误
     * SUCCESS: 网络请求成功，解析成功, 项目业务逻辑的状态码正确
     */
    FAILURE,
    PARSE_EXCEPTION,
    APP_CODE_EXCEPTION,
    SUCCESS

}
