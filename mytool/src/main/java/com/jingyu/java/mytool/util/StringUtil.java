package com.jingyu.java.mytool.util;

import com.jingyu.java.mytool.Constants;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author fengjingyu@foxmail.com
 */
public class StringUtil {

    public static boolean equals(String str1, String str2) {
        if (str1 == null && str2 == null) {
            throw new RuntimeException("StringUtil.equalsString()--传入了两个null字符串");
        } else if (str1 != null) {
            return str1.equals(str2);
        } else {
            return false;
        }
    }

    public static boolean isAnyBlank(String... strs) {
        if (strs == null) {
            return true;
        }
        for (String str : strs) {
            if (isBlank(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAnyNull(Object... objs) {
        if (objs == null) {
            return true;
        }
        for (Object obj : objs) {
            if (obj == null) {
                return true;
            }
        }
        return false;
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
    public static boolean isNotBlank(String str) {
        return !(str == null || str.trim().length() == 0);
    }

    /**
     * 把null转为""
     */
    public static String toNotNull(String str) {
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

    /**
     * 统计一个字符串在另外一个字符串中出现的次数
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
     * 123abcdefgABC ,比如justDeleteLastSymbol（"123abcdefg,h"，","）不可以，返回 123abcdefg,h
     */
    public static String deleteLastStr(String origin, String str) {
        if (origin.contains(str)) {
            origin = origin.trim();
            // 最后一个该字符的位置
            int index = origin.lastIndexOf(str);
            if ((index + str.length()) == origin.length()) {
                // 如果该字符是在最后尾
                return origin.substring(0, index);
            }
        }
        return origin;
    }

    /**
     * 获取origin中第一个str之后的字符串
     */
    public static String subStringAfterFirstStr(String origin, String str) {
        int index = origin.indexOf(str);
        if (index >= 0) {
            if (index + str.length() == origin.length()) {
                return "";
            } else {
                return origin.substring(index + str.length(), origin.length()).trim();
            }
        }
        return origin;
    }

    /**
     * 获取origin中最后一个Str之后的字符串
     */
    public static String subStringAfterLastStr(String origin, String str) {
        int index = origin.lastIndexOf(str);
        if (index >= 0) {
            if (index + str.length() == origin.length()) {
                return "";
            } else {
                return origin.substring(index + str.length(), origin.length()).trim();
            }
        }
        return origin;
    }

    /**
     * 获取origin中第一个str之前的字符串
     */
    public static String subStringBeforeFirstStr(String origin, String str) {
        int index = origin.indexOf(str);
        if (index > 0) {
            return origin.substring(0, index).trim();
        } else if (index == 0) {
            return "";
        }
        return origin;
    }

    /**
     * 获取origin中最后一个str之前的字符串
     */
    public static String subStringBeforeLastStr(String origin, String str) {
        int position = origin.lastIndexOf(str);
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
    public static String getSuffixName(String origin) {
        int position = origin.lastIndexOf(".");
        if (position > 0) {
            return origin.substring(position + 1, origin.length());
        }
        return null;
    }

    /**
     * 获取不包含后缀名的文件名称
     */
    public static String getFileName(String origin) {
        int lastDotPosition = origin.lastIndexOf(".");

        if (lastDotPosition > 0) {
            origin = origin.substring(0, lastDotPosition);
        }

        int lastLinePosition = origin.lastIndexOf("/");

        if (lastLinePosition > 0) {
            origin = origin.substring(lastLinePosition + 1, origin.length());
        }
        return origin;
    }

    public static final String encodeURL(String str) {
        try {
            return URLEncoder.encode(str, Constants.UTF8);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static final String decodeURL(String str) {
        try {
            return URLDecoder.decode(str, Constants.UTF8);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String setFirstLetterLower(String origin) {
        char[] chars = origin.toCharArray();

        if (chars[0] >= 65 && chars[0] < 90) {
            chars[0] = (char) (chars[0] + 32);
        }

        return new String(chars);
    }

    public static String setFirstLetterUpper(String origin) {
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
}
