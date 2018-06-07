package com.jingyu.java.mytool.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class DecimalUtil {

    public static String getFileSizeByUnit(long size) {
        if (size <= 0) {
            return "0";
        }
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "未知大小";
        if (size < 1024) {
            fileSizeString = df.format((double) size) + "B";
        } else if (size < 1048576) {
            fileSizeString = df.format((double) size / 1024) + "K";
        } else if (size < 1073741824) {
            fileSizeString = df.format((double) size / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) size / 1073741824) + "G";
        }
        return fileSizeString;
    }

    public static String getPercentText(double progress, double totalLenght) {
        DecimalFormat format = new DecimalFormat("###.00");
        double resultSize = (double) (progress * 100 / totalLenght);
        String reusltText = format.format(resultSize);
        return reusltText + "%";
    }

    /**
     * 只保留整数部分，且不显示千分符。不四舍五入，以截断方式处理。
     *
     * @param doubleNumber 源数据
     * @return String 被格式化后的数据，以字符串方式返回
     */
    public static String decimalFormatForIntNoCommas(double doubleNumber) {
        DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance();
        df.applyPattern("##0");
        doubleNumber = ((int) (doubleNumber * 100)) / 100.0;
        String str_earning = df.format(doubleNumber);

        return str_earning;
    }

    /**
     * 只保留整数部分，且不显示千分符。不四舍五入，以截断方式处理。
     *
     * @param doubleNumber 源数据
     * @return String 被格式化后的数据，以字符串方式返回
     */
    public static String decimalFormatForIntNoCommas(String doubleNumber) {
        return decimalFormatForIntNoCommas(Double.valueOf(doubleNumber));
    }

    /**
     * 只保留整数部分。不四舍五入，以截断方式处理。
     *
     * @param doubleNumber 源数据
     * @return String 被格式化后的数据，以字符串方式返回
     */
    public static String decimalFormatForInt(double doubleNumber) {
        DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance();
        df.applyPattern("#,##0");
        doubleNumber = ((int) (doubleNumber * 100)) / 100.0;
        String str_earning = df.format(doubleNumber);

        return str_earning;
    }

    /**
     * 只保留整数部分。不四舍五入，以截断方式处理。
     *
     * @param doubleNumber 源数据
     * @return String 被格式化后的数据，以字符串方式返回
     */
    public static String decimalFormatForInt(String doubleNumber) {
        return decimalFormatForInt(Double.valueOf(doubleNumber));
    }


    /**
     * 保留小数点后两位，当小数部分不足两位时，进行补零。不四舍五入，以截断方式处理。
     *
     * @param doubleNumber 源数据
     * @return String 被格式化后的数据，以字符串方式返回
     */
    public static String decimalFormat(double doubleNumber) {
        return decimalFormat(String.valueOf(doubleNumber));
    }

    /**
     * 保留小数点后两位，当小数部分不足两位时，进行补零。不四舍五入，以截断方式处理。
     *
     * @param stringNumber 源数据
     * @return String 被格式化后的数据，以字符串方式返回
     */
    public static String decimalFormat(String stringNumber) {
        double doubleNumber = 0.0d;

        try {
            doubleNumber = Double.valueOf(stringNumber);
        } catch (Exception e) {
            doubleNumber = 0.0d;
        }

        DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance();
        df.applyPattern("#,##0.00");

        BigDecimal bigDec_doubleNumber = new BigDecimal(Double.toString(doubleNumber));
        BigDecimal bigDec_100d = new BigDecimal(Double.toString(100.0d));
        BigDecimal result1 = bigDec_doubleNumber.multiply(bigDec_100d);

        doubleNumber = result1.doubleValue() / 100.0d;
        String str_earning = df.format(doubleNumber);

        return str_earning;
    }
}
