package com.jingyu.app.middle.okhttp.loading;

import android.app.Activity;
import android.app.Dialog;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fengjingyu@foxmail.com
 *         管理需要显示加载dialog的http请求
 *         当有多个并发请求都需要显示dialog时，只显示一个，且多个请求都返回后，才关闭dialog
 */
public class DialogManager {
    /**
     * 每一个activity对应一个DialogManager
     */
    private static DialogManager instance;
    /**
     * 记录需要加载dialog的网络请求
     * <p>
     * String:哪一个网络请求tag
     * Boolean:为null或false表示还没发请求或请求完成了，为true表示请求未返回
     */
    private Map<String, Boolean> mRequests;
    /**
     * 加载中的dialog
     */
    private Dialog mHttpDialog;
    /**
     * 当前在哪个页面
     */
    private Activity mCurrentActivity;

    private Activity getCurrentActivity() {
        return mCurrentActivity;
    }

    private DialogManager(Activity activity) {
        this.mCurrentActivity = activity;
        this.mRequests = new HashMap<>();
    }

    public static DialogManager getInstance(Activity activity) {
        if (instance == null) {
            instance = new DialogManager(activity);
        } else {
            if (instance.getCurrentActivity() == activity) {
                // do nothing
            } else {
                instance.closeHttpDialog();
                instance = new DialogManager(activity);
            }
        }
        return instance;
    }

    private boolean isAllRequestEnd() {
        for (Map.Entry<String, Boolean> entry : mRequests.entrySet()) {
            if (entry.getValue()) {
                // 表示还有请求没有回来
                return false;
            }
        }
        return true;
    }

    public void setDialog(Dialog httpDialog) {
        if (httpDialog == null) {
            throw new RuntimeException("DialogManager---setDialog()的httpDialog参数为null");
        }
        if (mHttpDialog == null || isAllRequestEnd()) {
            // 只有没有进行中的请求时才可以更新dialog
            mHttpDialog = httpDialog;
        }
    }

    /**
     * @param uniqueTag 记录这次请求的唯一标识符,可以用respHandler.toString();
     */
    public void mayShow(String uniqueTag) {
        if (mHttpDialog == null) {
            throw new RuntimeException("DialogManager---mayShow()方法内的dialog为null,请先调用setDailog()");
        }

        if (isAllRequestEnd()) {
            // 没有正在进行的请求 或 所有已发出的请求都已返回
            mHttpDialog.show();
        } else {
            // 有转dialog的请求未返回,页面正在转dialog
        }

        // 记录需要转dialog的请求
        mRequests.put(uniqueTag, true);
    }

    /**
     * @param uniqueTag 记录这次请求的唯一标识符,可以用respHandler.toString();
     */
    public void mayClose(String uniqueTag) {
        if (mRequests.containsKey(uniqueTag)) {
            // 从集合中删除需要转dialog的请求
            mRequests.remove(uniqueTag);
            if (isAllRequestEnd()) {
                closeHttpDialog();
                instance = null;
            }
        }
    }

    private void closeHttpDialog() {
        if (mHttpDialog != null && mHttpDialog.isShowing()) {
            mHttpDialog.cancel();
        }
    }

}
