package com.jingyu.app.middle;

import android.support.multidex.MultiDexApplication;

/**
 * @author fengjingyu@foxmail.com
 * @description 动态权限的问题:app中的初始化转移到LaunchActivity中
 */
public class MyApp extends MultiDexApplication {

    private static MyApp instance;

    public static MyApp getApplication() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

}
