package com.jingyu.app.middle;

import com.jingyu.android.common.log.Logger;

/**
 * @author fengjingyu@foxmail.com
 * @description
 */
public class MyEnv {

    enum RunEnvironment {
        DEV, // 开发
        TEST, //测试
        RELEASE // 项目上线
    }

    //TODO 项目上线前改为RELEASE
    public final static RunEnvironment CURRENT_RUN_ENVIRONMENT = RunEnvironment.DEV;

    //TODO 项目上线前检查配置
    static {
        if (CURRENT_RUN_ENVIRONMENT == RunEnvironment.DEV) {
            SHOW_DEBUG_TOAST = true;
            CONSOLE_LOG_LEVEL = Logger.ALL;
            ERROR_LOG_2_FILE = true;
            INIT_LEAK_CANARY = true;
            INIT_CRASH_HANDLER = true;
            SHOW_EXCEPTION_ACTIVITY = true;
            HOST = "http://host/dev";
            PUSH = "http://push/dev";
            HTML = "http://html/dev";
            APP_DIR_NAME = "app_name_dev";
        } else if (CURRENT_RUN_ENVIRONMENT == RunEnvironment.TEST) {
            SHOW_DEBUG_TOAST = false;
            CONSOLE_LOG_LEVEL = Logger.ALL;
            ERROR_LOG_2_FILE = true;
            INIT_LEAK_CANARY = true;
            INIT_CRASH_HANDLER = true;
            SHOW_EXCEPTION_ACTIVITY = true;
            HOST = "http://host/test";
            PUSH = "http://push/test";
            HTML = "http://html/test";
            APP_DIR_NAME = "app_name_test";
        } else if (CURRENT_RUN_ENVIRONMENT == RunEnvironment.RELEASE) {
            SHOW_DEBUG_TOAST = false;
            CONSOLE_LOG_LEVEL = Logger.NOTHING;
            ERROR_LOG_2_FILE = true;
            INIT_LEAK_CANARY = false;
            INIT_CRASH_HANDLER = false;
            SHOW_EXCEPTION_ACTIVITY = false;
            HOST = "http://host/release";
            PUSH = "http://push/release";
            HTML = "http://html/release";
            APP_DIR_NAME = "app_name";
        } else {
            throw new RuntimeException("MyEnv-->RunEnvironment的值有误");
        }
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------
    // 是否弹出调试的土司
    private final static boolean SHOW_DEBUG_TOAST;
    // 输出的信息级别
    private final static int CONSOLE_LOG_LEVEL;
    // 错误信息日志信息是否写到文件里
    private final static boolean ERROR_LOG_2_FILE;
    // 是否启用内存泄漏检测库
    private final static boolean INIT_LEAK_CANARY;
    // 是否初始化crashHandler
    private final static boolean INIT_CRASH_HANDLER;
    // 是否打印异常的日志到屏幕（只有在INIT_CRASH_HANDLER为true时，该设置才有效）
    private final static boolean SHOW_EXCEPTION_ACTIVITY;
    // 域名
    private final static String HOST;
    private final static String PUSH;
    private final static String HTML;
    //app的顶层目录名
    private final static String APP_DIR_NAME;

    public static boolean isShowDebugToast() {
        return SHOW_DEBUG_TOAST;
    }

    public static int getConsoleLogLevel() {
        return CONSOLE_LOG_LEVEL;
    }

    public static boolean isErrorLog2File() {
        return ERROR_LOG_2_FILE;
    }

    public static boolean isInitLeakCanary() {
        return INIT_LEAK_CANARY;
    }

    public static boolean isInitCrashHandler() {
        return INIT_CRASH_HANDLER;
    }

    public static boolean isShowExceptionActivity() {
        return SHOW_EXCEPTION_ACTIVITY;
    }

    public static String getHOST() {
        return HOST;
    }

    public static String getPUSH() {
        return PUSH;
    }

    public static String getHTML() {
        return HTML;
    }

    public static String getAppDirName() {
        return APP_DIR_NAME;
    }
}