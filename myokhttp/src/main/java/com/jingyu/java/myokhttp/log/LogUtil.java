package com.jingyu.java.myokhttp.log;

public class LogUtil {
    private static ILog log;

    public static ILog getILog() {
        return log;
    }

    public static void setLog(ILog iLog) {
        LogUtil.log = iLog;
    }

    public static void i(String tag, String msg) {
        log.i(tag, msg);
    }

    public static void e(String tag, String msg, Exception e) {
        log.e(tag, msg, e);
    }
}
