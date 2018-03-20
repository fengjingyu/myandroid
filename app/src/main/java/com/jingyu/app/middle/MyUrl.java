package com.jingyu.app.middle;

/**
 * @author fengjingyu@foxmail.com
 */
public class MyUrl {

    // 通用接口
    public static String getHostUrl(String key) {
        return MyEnv.getHOST() + key;
    }

    // 通知接口
    public static String getPushUrl(String key) {
        return MyEnv.getPUSH() + key;
    }

    // html接口
    public static String getHtmlUrl(String key) {
        return MyEnv.getHTML() + key;
    }

    //TODO Url
    public static final String LOGIN = "/app/auth/login/";
}
