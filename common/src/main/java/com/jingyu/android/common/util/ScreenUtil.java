package com.jingyu.android.common.util;

import android.content.Context;
import android.graphics.Rect;
import android.text.InputFilter;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.ScrollView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author fengjingyu@foxmail.com
 */
public class ScreenUtil {

    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public static float getDensityDpi(Context context) {
        return context.getResources().getDisplayMetrics().densityDpi;
    }

    public static int getScreenHeightPx(Context context) {
        return getScreenSizeByMetric(context)[1];
    }

    public static int getScreenWidthPx(Context context) {
        return getScreenSizeByMetric(context)[0];
    }

    public static int getScreenHeightDP(Context context) {
        return px2dip(context, getScreenHeightPx(context));
    }

    public static int getScreenWidthDP(Context context) {
        return px2dip(context, getScreenWidthPx(context));
    }

    public static int dip2px(Context context, float dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (scale * dipValue + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static float sp2px(Context context, float sp) {
        float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

    /**
     * 获取屏幕分辨率
     */
    public static int[] getScreenSizeByMetric(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metric = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metric);
        int[] size = new int[3];
        size[0] = metric.widthPixels;
        size[1] = metric.heightPixels;
        size[2] = metric.densityDpi;
        return size;
    }

    /**
     * 触摸的点是否在一个view内
     */
    public static boolean isTouchInsideView(List<? extends View> views, MotionEvent ev, int extraDistance) {

        if (views == null || views.isEmpty()) {
            return false;
        }

        for (View v : views) {

            int[] sizes = new int[2];
            v.getLocationOnScreen(sizes);

            Rect mRect = new Rect();
            mRect.left = sizes[0] - extraDistance;
            mRect.top = sizes[1] - extraDistance;
            mRect.right = sizes[0] + v.getWidth() + extraDistance;
            mRect.bottom = sizes[1] + v.getHeight() + extraDistance;

            if (mRect.contains((int) ev.getX(), (int) ev.getY())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 系统默认的scroll有效距离
     */
    public static int getTouchSlop(Context context) {
        return ViewConfiguration.get(context).getScaledTouchSlop();
    }

    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    public static void scrollUp(final ScrollView view) {
        view.post(new Runnable() {
            public void run() {
                view.fullScroll(ScrollView.FOCUS_UP);
            }
        });
    }

    public static void setMaxLength(TextView textView, int length) {
        textView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(length)});
    }
}





