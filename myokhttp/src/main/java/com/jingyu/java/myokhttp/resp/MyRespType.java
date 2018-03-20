package com.jingyu.java.myokhttp.resp;

/**
 * @author fengjingyu@foxmail.com
 *  http的响应结果
 */
public enum MyRespType {

    /**
     * FAILURE:网络请求失败
     * SUCCESS_WAITING_PARSE:网络请求成功，还未解析，未判断状态码
     * SUCCESS_BUT_PARSE_WRONG：网络请求成功，解析失败
     * SUCCESS_BUT_CODE_WRONG：网络请求成功，解析成功，项目业务逻辑的状态码有误
     * SUCCESS_ALL:网络请求成功，解析 与 项目业务逻辑的状态码都是成功的
     */
    FAILURE,
    SUCCESS_WAITING_PARSE,
    SUCCESS_BUT_PARSE_WRONG,
    SUCCESS_BUT_CODE_WRONG,
    SUCCESS_ALL

}
