package com.jingyu.app.middle;

import android.app.Application;
import com.jingyu.android.common.sp.SPHelper;

/**
 * @author fengjingyu@foxmail.com
 */
public class MySp {
    private MySp() {
    }

    private static final String SP_FILE_NAME = "sp_file_common";
    private static SPHelper spHelper;

    private static final String IS_FIRST_IN = "isFirstIn";
    private static final String IS_LOGIN = "isLogin";
    private static final String USER_NAME = "userName";
    private static final String USER_ID = "userId";
    private static final String USER_TOKEN = "userToken";
    private static final String USER_PHONE = "userPhone";

    public static void initSP(Application application) {
        spHelper = new SPHelper(application, SP_FILE_NAME);
    }

    public static void clear() {
        spHelper.clear();
    }

    public static boolean isLogin() {
        return spHelper.spGet(IS_LOGIN, false);
    }

    public static boolean isFirstIn() {
        return spHelper.spGet(IS_FIRST_IN, true);
    }

    public static String getUserId() {
        return spHelper.spGet(USER_ID, "");
    }

    public static String getUserToken() {
        return spHelper.spGet(USER_TOKEN, "");
    }

    public static String getUserName() {
        return spHelper.spGet(USER_NAME, "");
    }

    public static String getUserPhone() {
        return spHelper.spGet(USER_PHONE, "");
    }

    public static void setLogin(boolean isLogin) {
        spHelper.spPut(IS_LOGIN, isLogin);
    }

    public static void setFirstIn(boolean value) {
        spHelper.spPut(IS_FIRST_IN, value);
    }

    public static void setUserId(String userId) {
        spHelper.spPut(USER_ID, userId);
    }

    public static void setUserToken(String userToken) {
        spHelper.spPut(USER_TOKEN, userToken);
    }

    public static void setUserName(String userName) {
        spHelper.spPut(USER_NAME, userName);
    }

    public static void setUserPhone(String userPhoneNum) {
        spHelper.spPut(USER_PHONE, userPhoneNum);
    }

}
