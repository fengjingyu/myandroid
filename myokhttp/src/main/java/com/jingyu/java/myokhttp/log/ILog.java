package com.jingyu.java.myokhttp.log;

public interface ILog {

    void i(String tag, String msg);

    void e(String tag, String msg, Exception e);
}
