package com.jingyu.java.mytool.util;

import com.jingyu.java.mytool.Constants;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author fengjingyu@foxmail.com
 */
public class StringUtil {

    /**
     * 比较两个字符串
     */
    public static boolean equals(String str1, String str2) {
        if (str1 == null && str2 == null) {
            throw new RuntimeException("StringUtil.equalsString()--传入了两个null字符串");
        } else if (str1 != null) {
            return str1.equals(str2);
        } else {
            return false;
        }
    }

    /**
     * 只判断空 和 空格
     */
    public static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
    }

    /**
     * 只判断空 和 空格
     */
    public static boolean isAvaliable(String str) {
        return !(str == null || str.trim().length() == 0);
    }

    /**
     * 把null转为""
     */
    public static String notNull(String str) {
        if (isBlank(str)) {
            return "";
        }
        return str;
    }

    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            e.printStackTrace();
            return defValue;
        }
    }

    /**
     * 默认0
     */
    public static int toInt(String obj) {
        return toInt(obj, 0);
    }

    public static long toLong(String obj, long defValue) {
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return defValue;
        }
    }

    /**
     * 默认0
     */
    public static long toLong(String obj) {
        return toLong(obj, 0);
    }

    public static double toDouble(String obj, double defValue) {
        try {
            return Double.parseDouble(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return defValue;
        }
    }

    /**
     * 默认0
     */
    public static double toDouble(String obj) {
        return toDouble(obj, 0D);
    }

    public static boolean toBool(String b, boolean defValue) {
        try {
            return Boolean.parseBoolean(b);
        } catch (Exception e) {
            e.printStackTrace();
            return defValue;
        }
    }

    /**
     * 默认false
     */
    public static boolean toBool(String b) {
        return toBool(b, false);
    }

    public static boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断字符是否是数值或者中文
     */
    public boolean isDigitOrChinese(String str) {
        String regex = "^[a-z0-9A-Z\u4e00-\u9fa5]+$";
        return str.matches(regex);
    }

    /**
     * 统计一个字符串在另外一个字符串中出现的次数
     *
     * @param origin
     * @param key
     * @return
     */
    public static int countTimes(String origin, String key) {
        String temp = new String(origin);
        int count = 0;
        while (temp.contains(key)) {
            count++;
            int position = temp.indexOf(key);
            if (position >= 0) {
                temp = temp.substring(position + key.length(), temp.length());
            }
        }
        return count;
    }

    /**
     * 删除最后一个指定的字符串，该字符串必须出现在最后尾，否则不会删除
     * <p>
     * 123abcdefgABC ,比如justDeleteLastSymbol（"123abcdefg,"，","）可以， 返回 123abcdefg
     * 123abcdefgABC ,,比如justDeleteLastSymbol（"123abcdefg,h"，","）不可以，返回 123abcdefg,h
     *
     * @param symbol
     * @return
     */
    public static String deleteLastSymbol(String origin, String symbol) {
        if (origin.contains(symbol)) {
            origin = origin.trim();
            // 最后一个该字符的位置
            int index = origin.lastIndexOf(symbol);
            if ((index + symbol.length()) == origin.length()) {
                // 如果该字符是在最后尾
                return origin.substring(0, index);
            }
        }
        return origin;
    }

    /**
     * 获取origin中第一个simbol之后的字符串
     *
     * @param origin
     * @param symbol
     * @return
     */
    public static String subStringAfterFirstSimbol(String origin, String symbol) {
        int index = origin.indexOf(symbol);
        if (index >= 0) {
            if (index + symbol.length() == origin.length()) {
                return "";
            } else {
                return origin.substring(index + symbol.length(), origin.length()).trim();
            }
        }
        return origin;
    }

    /**
     * 获取origin中最后一个simbol之后的字符串
     *
     * @param origin
     * @param symbol
     * @return
     */
    public static String subStringAfterLastSimbol(String origin, String symbol) {
        int index = origin.lastIndexOf(symbol);
        if (index >= 0) {
            if (index + symbol.length() == origin.length()) {
                return "";
            } else {
                return origin.substring(index + symbol.length(), origin.length()).trim();
            }
        }
        return origin;
    }

    /**
     * 获取origin中第一个simbol之前的字符串
     *
     * @param origin
     * @param symbol
     * @return
     */
    public static String subStringBeforeFirstSimbol(String origin, String symbol) {
        int index = origin.indexOf(symbol);
        if (index > 0) {
            return origin.substring(0, index).trim();
        } else if (index == 0) {
            return "";
        }
        return origin;
    }

    /**
     * 获取origin中最后一个simbol之前的字符串
     *
     * @param origin
     * @param symbol
     * @return
     */
    public static String subStringBeforeLastSimbol(String origin, String symbol) {
        int position = origin.lastIndexOf(symbol);
        if (position > 0) {
            return origin.substring(0, position).trim();
        } else if (position == 0) {
            return "";
        }
        return origin;
    }

    /**
     * 获取url或文件名后缀
     */
    public static String getUrlOrFileSuffix(String str) {
        int position = str.lastIndexOf(".");
        if (position > 0) {
            return str.substring(position + 1, str.length());
        }
        return null;
    }

    /**
     * 获取一个url的最后的文件名，不带文件后缀名
     */
    public static String getUrlOrFileName(String str) {
        int last_dot_position = str.lastIndexOf(".");

        if (last_dot_position > 0) {
            str = str.substring(0, last_dot_position);
        }

        int last_line_position = str.lastIndexOf("/");

        if (last_line_position > 0) {
            str = str.substring(last_line_position + 1, str.length());
        }
        return str;
    }

    /**
     * 获取文件大小,单位字节
     *
     * @param filePath 传入文件的绝对路径
     * @return
     */
    public static long getFileSize(String filePath) {
        long size = 0;
        File file = new File(filePath);
        if (file.exists()) {
            size = file.length();
        }
        return size;
    }

    /**
     * 换算文件大小含单位
     *
     * @param size
     * @return
     */
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

    /**
     * 获取两数相除的百分比
     */
    public static String getPercentText(double progress, double totalLenght) {
        DecimalFormat format = new DecimalFormat("###.00");
        double resultSize = (double) (progress * 100 / totalLenght);
        String reusltText = format.format(resultSize);
        return reusltText + "%";
    }

    /**
     * 显示下载数量
     */
    public static String getDownNumberText(long number) {
        double resultSize;
        String resultStr = "";
        DecimalFormat format = new DecimalFormat("###.00");
        if (number > 10000 * 10000) {// 亿
            resultSize = number / (10000 * 10000.00);
            resultStr = format.format(resultSize) + "亿";
        } else if (number > 10000) {// 万
            resultSize = number / (10000.00);
            resultStr = format.format(resultSize) + "万";
        } else {
            resultStr = number + "";
        }
        return resultStr;
    }

    public static String delHtml(String str) {
        String info = str.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll("<[^>]*>", "");
        info = info.replaceAll("[(/>)<]", "");
        return info;
    }

    public static final String encodeURL(String str) {
        try {
            return URLEncoder.encode(str, Constants.ENCODING_UTF8);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static final String decodeURL(String str) {
        try {
            return URLDecoder.decode(str, Constants.ENCODING_UTF8);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 判断给定字符串是否包含空白符
     * 空白串是指含有空格、制表符、回车符、换行符组成的字符串
     * 若输入字符串为null或空字符串，也返回true
     */
    public static boolean isIncludeBlank(String input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == ' ' || c == '\t' || c != '\r' || c != '\n') {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否是邮件
     */
    public static boolean isEmail(String email) {
        if (email == null || email.trim().length() == 0)
            return false;
        Pattern emailer = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        return emailer.matcher(email).matches();
    }

    /**
     * 是否是电话号码
     */
    public static boolean isPhoneNum(String num) {
        if (num != null && num.length() == 11) {
            // if (num.matches("1[34568]\\d{9}")) {
            // 访问网络获取验证码
            return true;
            // }
        }
        return false;
    }

    /**
     * 检测输入的邮箱是否符合要求.
     */
    public static boolean validateEmail(String number) {
        Pattern p = Pattern
                .compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
        Matcher m = p.matcher(number);
        return m.matches();
    }

    /**
     * 判断固定电话
     */
    public static boolean isFixPhoneNumber(String gnumber) {
        Pattern pattern = Pattern
                .compile("0\\d{2,3}(\\(\\d{3,4}\\)|\\d{3,4}-|\\s)?\\d{8}");
        Matcher matcher = pattern.matcher(gnumber);
        return matcher.matches();
    }

    /**
     * 设置第一个字母为小写
     */
    public static String setFirstLetterSmall(String origin) {

        char[] chars = origin.toCharArray();

        if (chars[0] >= 65 && chars[0] < 90) {
            chars[0] = (char) (chars[0] + 32);
        }

        return new String(chars);

    }

    /**
     * 设置第一个字母为大写
     */
    public static String setFirstLetterBig(String origin) {

        if (isBlank(origin)) {
            return origin;
        }

        char[] chars = origin.toCharArray();

        if (chars[0] >= 97 && chars[0] <= 122) {
            chars[0] = (char) (chars[0] - 32);
        }

        return new String(chars);

    }

    /**
     * 加密手机号码
     *
     * @return 加密处理后的手机号码（当手机号码长度不是11位时，不进行加密处理）
     */
    public static String encrypPhoneNumber(String number) {
        String newnumber = number;
        if (number.trim().length() == 11) {
            newnumber = number.substring(0, 3) + "****" + number.substring(number.length() - 4, number.length());
        } else {
            newnumber = number;
        }
        return newnumber;
    }

    /**
     * 加密用户名
     *
     * @return 加密处理后的用户名（当用户名是一个字符时，不进行加密处理）
     */
    public static String encrypName(String name) {
        String newName = name;
        if (null != name && name.trim().length() > 1) {
            if (name.length() == 2) {
                newName = "*" + name.substring(name.length() - 1, name.length());
            } else {
                newName = "*" + name.substring(name.length() - 2, name.length());
            }
        }
        return newName;
    }

    /**
     * 加密邮箱
     */
    public static String encrypEmail(String email) {

        String newemail = "";
        if (email == null || "".equals(email.trim())) {
            return newemail;
        }
        String[] subemail = email.split("@");
        if (subemail[0].length() <= 3) {
            newemail = subemail[0].substring(0, 1) + "***" + "@" + subemail[1];
        } else {
            newemail = subemail[0].substring(0, subemail[0].length() - 3) + "***" + "@" + subemail[1];
        }
        return newemail;
    }

    /**
     * 加密其它证件号
     */
    public static String encrypCard(String cardnum) {
        String newcardnum = "";
        if (cardnum == null || "".equals(cardnum.trim())) {
            return newcardnum;
        }
        if (cardnum.length() >= 12) {
            newcardnum = cardnum.substring(0, cardnum.length() - 11) + "********" + cardnum.substring(cardnum.length() - 3, cardnum.length());
        } else {
            newcardnum = cardnum.substring(0, 1) + "********" + cardnum.substring(cardnum.length() - 3, cardnum.length());
        }
        return newcardnum;
    }

    /**
     * 加密身份证(仅显示后四位)
     */
    public static String encrypAuthenCard(String authencardnum) {
        String newauthencardnum = "";
        if (authencardnum == null || "".equals(authencardnum.trim())) {
            return newauthencardnum;
        }
        if (authencardnum.length() == 18) {
            newauthencardnum = "******" + "********" + authencardnum.substring(authencardnum.length() - 4, authencardnum.length());
        } else if (authencardnum.length() == 15) {
            newauthencardnum = "******" + "*****" + authencardnum.substring(authencardnum.length() - 4, authencardnum.length());
        }
        return newauthencardnum;
    }

    /**
     * 加密身份证
     */
    public static String encrypAuthenCardV2(String authencardnum) {
        String newauthencardnum = "";
        if (authencardnum == null || "".equals(authencardnum.trim())) {
            return newauthencardnum;
        }
        if (authencardnum.length() == 18) {
            newauthencardnum = authencardnum.substring(0, 6) + "**********" + authencardnum.substring(authencardnum.length() - 2, authencardnum.length());
        } else if (authencardnum.length() == 15) {
            newauthencardnum = authencardnum.substring(0, 6) + "********" + authencardnum.substring(authencardnum.length() - 1, authencardnum.length());
        }
        return newauthencardnum;
    }

    /**
     * unicode 转 中文
     */
    public static String parseUnicode(String line) {
        int len = line.length();
        char[] out = new char[len];// 保存解析以后的结果
        int outLen = 0;
        for (int i = 0; i < len; i++) {
            char aChar = line.charAt(i);
            if (aChar == '\\') {
                aChar = line.charAt(++i);
                if (aChar == 'u') {
                    int value = 0;
                    for (int j = 0; j < 4; j++) {
                        aChar = line.charAt(++i);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "StringUtil--Malformed \\uxxxx encoding.");
                        }
                    }
                    out[outLen++] = (char) value;
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    out[outLen++] = aChar;
                }
            } else {
                out[outLen++] = aChar;
            }
        }
        return new String(out, 0, outLen);
    }

    /**
     * 全角转换为半角
     */
    public static String toDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * 半角转换为全角
     */
    public static String toSBC(String input) {
        // 半角转全角：
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 32) {
                c[i] = (char) 12288;
                continue;
            }
            if (c[i] < 127)
                c[i] = (char) (c[i] + 65248);
        }
        return new String(c);
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
