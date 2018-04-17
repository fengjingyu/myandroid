package com.jingyu.java.mytool.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static String yyyy_MM_dd = "yyyy-MM-dd";

    public static String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";

    public static String yyyy_MM_dd_HH_mm_ss_S = "yyyy-MM-dd HH:mm:ss.S";

    public static String CN_yyyy_MM_dd_HH_mm_ss = "yyyy年MM月dd日 HH时mm分ss秒";

    public static boolean isValidDate(String s, String pattern) {
        DateFormat fmt = new SimpleDateFormat(pattern);
        try {
            fmt.parse(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String format(Date date, String pattern) {
        String returnValue = "";
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            returnValue = df.format(date);
        }
        return returnValue;
    }

    public static Date parse(String strDate, String pattern) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.parse(strDate);
    }

    public static Date addMonth(Date date, int n) {
        Calendar cal = Calendar.getInstance();

        cal.setTime(date);

        cal.add(Calendar.MONTH, n);

        return cal.getTime();
    }

    public static Date addDay(Date date, int n) {
        Calendar cal = Calendar.getInstance();

        cal.setTime(date);

        cal.add(Calendar.DATE, n);

        return cal.getTime();
    }

    public static boolean isToday(long time) {
        Date mayTime = new Date(time);
        Date today = new Date();
        String mayData = format(mayTime, yyyy_MM_dd);
        String todayDate = format(today, yyyy_MM_dd);
        if (mayData.equals(todayDate)) {
            return true;
        }
        return false;
    }

    public static long getDaySub(String beginDateStr, String endDateStr, String pattern) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return getDaySub(format.parse(beginDateStr), format.parse(endDateStr));
    }

    public static long getDaySub(Date beginDate, Date endDate) {
        return (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
    }

    /**
     * 得到n天之后的日期
     */
    public static String geDayDate(int days, String pattern) {
        Calendar canlendar = Calendar.getInstance();
        canlendar.add(Calendar.DATE, days); // 日期减 如果不够减会将月变动
        SimpleDateFormat sdfd = new SimpleDateFormat(pattern);
        return sdfd.format(canlendar.getTime());
    }

    /**
     * 得到n天之后是周几
     */
    public static String getDayWeek(int days) {
        Calendar canlendar = Calendar.getInstance();
        canlendar.add(Calendar.DATE, days); // 日期减 如果不够减会将月变动
        SimpleDateFormat sdf = new SimpleDateFormat("E");
        return sdf.format(canlendar.getTime());
    }

    /**
     * 指定日期加上天数后的日期
     */
    public static Date plusDay(int days, Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    /**
     * 指定日期加上天数后的日期
     */
    public static Date plusDay(int days, String dateString, String pattern) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = sdf.parse(dateString);
        return plusDay(days, date, pattern);
    }
}
