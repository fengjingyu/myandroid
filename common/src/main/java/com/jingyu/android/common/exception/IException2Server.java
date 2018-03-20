package com.jingyu.android.common.exception;

/**
 * @author  fengjingyu@foxmail.com
 */
public interface IException2Server {
    /**
     * 回调接口
     */
    void uploadException2Server(String info, Throwable ex, Thread thread, ExceptionInfo model, ExceptionDb exceptionModelDb);
}
