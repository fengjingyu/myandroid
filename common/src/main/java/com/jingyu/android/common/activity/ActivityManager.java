package com.jingyu.android.common.activity;

import android.app.Activity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * @author fengjingyu@foxmail.com
 *  管理activity
 */
public class ActivityManager {

    private static Stack<Activity> stack = new Stack<Activity>();

    public static Stack<Activity> getStack() {
        return stack;
    }

    private ActivityManager() {
    }

    /**
     * 添加Activity到栈中
     */
    public static void addActivityToStack(Activity activity) {
        stack.push(activity);
    }

    /**
     * 把Activity移出栈
     */
    public static void delActivityFromStack(Activity activity) {
        stack.remove(activity);
    }

    /**
     * 获取顶层Activity（activity不删除）
     */
    public static Activity getCurrentActivity() {
        if (stack.isEmpty()) {
            return null;
        }
        return stack.lastElement();
    }

    /**
     * 判断某个acivity实例是否存在
     */
    public static boolean isActivityExist(Class<?> cls) {
        for (Activity activity : stack) {
            if (activity.getClass().equals(cls)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取某(几)个activity（activity不删除）
     */
    public static List<Activity> getActivity(Class<?> cls) {
        List<Activity> list = new ArrayList<Activity>();
        for (Activity activity : stack) {
            if (activity.getClass().equals(cls)) {
                list.add(activity);
            }
        }
        return list;
    }

    /**
     * 结束指定的一个Activity
     */
    public static void finishActivity(Activity activity) {
        if (activity != null) {
            stack.remove(activity);
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    /**
     * 通过class ， 结束指定类名的（几个或一个）Activity
     */
    public static void finishActivity(Class<?> cls) {

        for (Iterator<Activity> it = stack.iterator(); it.hasNext(); ) {
            Activity activity = it.next();
            if (activity.getClass().equals(cls)) {
                // finishActivity(activity);// 并发修改异常
                it.remove();
                if (!activity.isFinishing()) {
                    activity.finish();
                }
            }
        }
    }

    /**
     * 结束当前Activity
     */
    public static void finishCurrentActivity() {
        finishActivity(getCurrentActivity());
    }

    public static void finishAllActivity() {
        if (stack != null && !stack.isEmpty()) {
            Stack<Activity> currentStack = new Stack<>();
            currentStack.addAll(stack);
            for (Activity activity : currentStack) {
                if (activity != null) {
                    if (!activity.isFinishing()) {
                        activity.finish();
                    }
                }
            }
            stack.clear();
        }
    }

    /**
     * 去到指定的activity，该activity之上的activitys将销毁
     *
     * 如果该activity不存在，则一个也不删除
     */
    public static Activity toActivity(Class<? extends Activity> activity_class) {

        if (isActivityExist(activity_class)) {
            // activity存在
            Activity toActivity = null;

            while (true) {

                if (stack.isEmpty()) {
                    return null;
                }

                // 获取顶层Activity（activity暂不删除）
                toActivity = getCurrentActivity();

                if (toActivity == null) {
                    return null;
                }

                if (toActivity.getClass().getName().equals(activity_class.getName())) {
                    // 这个activity是返回的，不可以删
                    break;
                } else {
                    // 删除activity
                    finishActivity(toActivity);
                }

            }
            return toActivity;
        } else {
            // activity不存在
            return null;
        }
    }

    /**
     * 退出应用程序
     */
    public static void appExit() {
        try {
            ActivityManager.finishAllActivity();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

}
