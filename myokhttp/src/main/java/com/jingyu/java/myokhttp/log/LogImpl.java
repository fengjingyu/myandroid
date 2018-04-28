package com.jingyu.java.myokhttp.log;

public class LogImpl implements ILog {
    @Override
    public void i(String tag, String msg) {
        System.out.println(tag + "::" + msg);
    }

    @Override
    public void e(String tag, String msg, Exception e) {
        System.out.println(tag + "::" + msg + "::" + e);
    }
}
