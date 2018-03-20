package com.jingyu.java.mytool.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static String FORMAT_HH_MM = "HH:mm";

    public static String FORMAT_MM_DD_HH_MM = "MM月dd日 HH:mm";

    /**
     * 英文简写（默认）如：2010-12-01
     */
    public static String FORMAT_SHORT = "yyyy-MM-dd";

    /**
     * 英文全称  如：2010-12-01 23:15:06
     */
    public static String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";

    /**
     * 精确到毫秒的完整时间    如：yyyy-MM-dd HH:mm:ss.S
     */
    public static String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";

    /**
     * 029
     * 中文全称  如：2010年12月01日  23:15分
     * 030
     */
    public static String FORMAT_LONG_CN_1 = "yyyy年MM月dd日 HH:mm";

    /**
     * 精确到毫秒的完整中文时间
     */
    public static String FORMAT_FULL_CN = "yyyy年MM月dd日 HH时mm分ss秒SSS毫秒";

    /**
     * 把一个时间段,按HH:mm:ss显示
     */
    public static String formatTime(long millisecond) {
        long h, m, s;
        h = millisecond / 1000 / 60 / 60;
        m = millisecond / 1000 / 60 % 60;
        s = millisecond / 1000 % 60;
        String time = (h < 10 ? "0" + h : h) + ":" +
                (m < 10 ? "0" + m : m) + ":" +
                (s < 10 ? "0" + s : s);
        return time;
    }

    /**
     * 传入一个时间差， 获取剩余时间
     */
    public static String[] getRemainTime(long differ) {
        differ = differ / 1000;
        String[] result = new String[4];
        long days = differ / (60 * 60 * 24); // 天
        long hours = (differ - days * 60 * 60 * 24) / (60 * 60); // 小时
        long minutes = (differ - days * (60 * 60 * 24) - hours * (60 * 60)) / (60); // 分
        long seconds = (differ - (days * 60 * 60 * 24) - (hours * 60 * 60)) - minutes * (60); // 秒

        if (days < 10) {
            result[0] = ("0" + days);
        } else {
            result[0] = (days + "");
        }

        if (hours < 10) {
            result[1] = ("0" + hours);
        } else {
            result[1] = (hours + "");
        }

        if (minutes < 10) {
            result[2] = ("0" + minutes);
        } else {
            result[2] = (minutes + "");
        }

        if (seconds < 10) {
            result[3] = ("0" + seconds);
        } else {
            result[3] = (seconds + "");
        }
        return result;
    }

    public static String getRemainTimeNoDay(long time) {
        time = time / 1000;
        String h = String.valueOf(time / 3600);
        String m = String.valueOf(time % 3600 / 60);
        String s = String.valueOf(time % 3600 % 60);

        if (h.length() == 1) {
            h = "0" + h;
        }
        if (m.length() == 1) {
            m = "0" + m;
        }
        if (s.length() == 1) {
            s = "0" + s;
        }
        return h + ":" + m + ":" + s;
    }

    /**
     * 根据预设格式返回当前日期
     */
    public static String getNow() {
        return format(new Date());
    }

    /**
     * 根据用户格式返回当前日期
     *
     * @param format
     * @return
     */
    public static String getNow(String format) {
        return format(new Date(), format);
    }

    /**
     * 使用预设格式格式化日期
     *
     * @param date
     * @return
     */
    public static String format(Date date) {
        return format(date, getDatePattern());
    }

    /**
     * 使用用户格式格式化日期
     *
     * @param date    日期
     * @param pattern 日期格式
     * @return
     */
    public static String format(Date date, String pattern) {
        String returnValue = "";
        if (date != null) {

            SimpleDateFormat df = new SimpleDateFormat(pattern);

            returnValue = df.format(date);

        }
        return (returnValue);
    }

    /**
     * 使用预设格式提取字符串日期
     *
     * @param strDate 日期字符串
     * @return
     */
    public static Date parse(String strDate) {
        return parse(strDate, getDatePattern());
    }

    /**
     * 使用用户格式提取字符串日期
     *
     * @param strDate 日期字符串
     * @param pattern 日期格式
     * @return
     */
    public static Date parse(String strDate, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 在日期上增加数个整月
     *
     * @param date 日期
     * @param n    要增加的月数
     * @return
     */
    public static Date addMonth(Date date, int n) {
        Calendar cal = Calendar.getInstance();

        cal.setTime(date);

        cal.add(Calendar.MONTH, n);

        return cal.getTime();
    }

    /**
     * 在日期上增加天数
     *
     * @param date 日期
     * @param n    要增加的天数
     * @return
     */
    public static Date addDay(Date date, int n) {
        Calendar cal = Calendar.getInstance();

        cal.setTime(date);

        cal.add(Calendar.DATE, n);

        return cal.getTime();
    }

    /**
     * 148
     * 获取日期年份
     *
     * @param date 日期
     * @return
     */

    public static String getYear(Date date) {
        return format(date).substring(0, 4);
    }

    /**
     * 按默认格式的字符串距离今天的天数
     *
     * @param date 日期字符串
     *             * @return
     */
    public static int countDays(String date) {
        long t = Calendar.getInstance().getTime().getTime();

        Calendar c = Calendar.getInstance();

        c.setTime(parse(date));

        long t1 = c.getTime().getTime();

        return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
    }


    /**
     * 按用户格式字符串距离今天的天数
     *
     * @param date   日期字符串
     * @param format 日期格式
     * @return
     */

    public static int countDays(String date, String format) {
        long t = Calendar.getInstance().getTime().getTime();

        Calendar c = Calendar.getInstance();

        c.setTime(parse(date, format));

        long t1 = c.getTime().getTime();

        return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
    }

    /**
     * 获得默认的 date pattern
     */
    public static String getDatePattern() {
        return FORMAT_LONG;
    }

    public static boolean isToday(long time) {
        Date mayTime = new Date(time);
        Date today = new Date();
        String mayData = format(mayTime, FORMAT_SHORT);
        String todayDate = format(today, FORMAT_SHORT);
        if (mayData.equals(todayDate)) {
            return true;
        }
        return false;
    }
}
