package com.jingyu.android.common.util;

import com.jingyu.android.common.log.Logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author xujinshan
 *
 */
public class TimeUtil {
    public static void main(String[] args) {

        String timePoint = getCurrentTime();
        String timeQuantum = "01:13-23:9";
        boolean flag = m(timePoint, timeQuantum);
        System.out.println(flag);


        timePoint = getCurrentTime();
        timeQuantum = "01:13-03:20";
        flag = m(timePoint, timeQuantum);
        System.out.println(flag);
    }

    /**
     * 获取当前系统时间
     *
     * @return String 当前系统时间
     */
    public static String getCurrentTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String str = sdf.format(date);
        str = str.substring(8, 12);

        return str;
    }

    /**
     * 计算传入时间是否在某一时间段内
     *
     * @param timePoint   传入时间
     * @param timeQuantum 时间段
     * @return boolean 传入时间是否在时间段内（true：在；false：不在）
     */
    public static boolean m(String timePoint, String timeQuantum) {
        boolean flag = false;

        try {
            String str_timePoint = timePoint;
            String str_timeQuantum = timeQuantum;
            Logger.d("当前时间点：" + timePoint);
            Logger.d("当前时间段：" + timeQuantum);

            String[] ss = str_timeQuantum.split("-");
            int length = ss.length;
            for (int i = 0; i < length; i++) {
                // 判断传入的时间点是否早于起始时间
                if (i == 0) {
                    String time_01 = checkTimeFormat(ss[i]);
                    String t_timePoint = timePoint;
                    String t_time_01 = time_01.replace(":", "");

                    int int_timePoint = Integer.valueOf(t_timePoint);
                    int int_time_01 = Integer.valueOf(t_time_01);

                    flag = int_time_01 < int_timePoint ? true : false;
                }
                // 判断传入的时间是否晚于结束时间
                else if (i == 1) {
                    if (flag) {
                        String time_02 = checkTimeFormat(ss[i]);
                        String t_timePoint = timePoint;
                        String t_time_02 = time_02.replace(":", "");

                        int int_timePoint = Integer.valueOf(t_timePoint);
                        int int_time_02 = Integer.valueOf(t_time_02);

                        flag = int_time_02 > int_timePoint ? true : false;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }

        return flag;
    }

    /**
     * 检查时间格式是否正确，对分钟内容为1位数的情况进行处理
     *
     * @param time 时间
     * @return String 处理后的时间，保证分钟内容为2位数
     */
    public static String checkTimeFormat(String time) {
        String returnStr = time;

        String[] ss = time.split(":");
        int length = ss.length;
        if (length == 2) {
            if (ss[1].length() == 1) {
                String str = "0" + ss[1];
                returnStr = ss[0] + ":" + str;
            }
        } else {
            // TODO 当出现时间格式为“0”时的处理
        }

        return returnStr;
    }

    /**
     * 判断当前日期是星期几
     */
    public static int dayForWeek(Date date) {

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayForWeek = 0;
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            dayForWeek = 7;
        } else {
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        return dayForWeek;
    }
}


