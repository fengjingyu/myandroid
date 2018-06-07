package com.jingyu.java.mytool.util;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RegularUtil {
    /**
     * 正则表达式：验证用户名
     */
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,17}$";

    /**
     * 正则表达式：验证密码
     */
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";

    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 正则表达式：验证汉字
     */
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";

    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";

    /**
     * 正则表达式：验证URL
     */
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

    /**
     * 正则表达式：验证IP地址
     */
    public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";

    /**
     * 校验用户名
     *
     * @param username
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUsername(String username) {
        return Pattern.matches(REGEX_USERNAME, username);
    }

    /**
     * 校验密码
     *
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }

    /**
     * 校验手机号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    /**
     * 校验邮箱
     *
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }

    /**
     * 校验汉字
     *
     * @param chinese
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isChinese(String chinese) {
        return Pattern.matches(REGEX_CHINESE, chinese);
    }

    /**
     * 校验身份证
     *
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        return Pattern.matches(REGEX_ID_CARD, idCard);
    }

    /**
     * 校验URL
     *
     * @param url
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUrl(String url) {
        return Pattern.matches(REGEX_URL, url);
    }

    /**
     * 校验IP地址
     *
     * @param ipAddr
     * @return
     */
    public static boolean isIPAddr(String ipAddr) {
        return Pattern.matches(REGEX_IP_ADDR, ipAddr);
    }

    /**
     * 判断字符是否是数值或者中文
     */
    public boolean isDigitOrChinese(String str) {
        String regex = "^[a-z0-9A-Z\u4e00-\u9fa5]+$";
        return str.matches(regex);
    }

    public static String delHtml(String str) {
        String info = str.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll("<[^>]*>", "");
        info = info.replaceAll("[(/>)<]", "");
        return info;
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
     *
     * @param version1
     */
    public static int compareVersion(String version1, String version2) throws Exception {
        if (version1 == null || version2 == null) {
            throw new Exception("参数异常");
        }
        String[] versionArray1 = version1.split("\\.");
        String[] versionArray2 = version2.split("\\.");

        // 如果是0.01.002,则当位数>=2时,去除前面的零-> 0.1.2
        versionArray1 = Arrays.stream(versionArray1).map(value -> {
            if (value.length() >= 2) {
                String result = value.replaceFirst("^0*", "");
                if (result.trim().length() == 0) {
                    // 0.0000.1 - > 0.0.1
                    result = "0";
                }
                return result;
            }
            return value;
        }).collect(Collectors.toList()).toArray(new String[versionArray1.length]);

        versionArray2 = Arrays.stream(versionArray2).map(value -> {
            if (value.length() >= 2) {
                String result = value.replaceFirst("^0*", "");
                if (result.trim().length() == 0) {
                    // 0.0000.1 - > 0.0.1
                    result = "0";
                }
                return result;
            }
            return value;
        }).collect(Collectors.toList()).toArray(new String[versionArray2.length]);

        int idx = 0;
        int minLength = Math.min(versionArray1.length, versionArray2.length);//取最小长度值
        int diff = 0;
        while (idx < minLength
                && (diff = versionArray1[idx].length() - versionArray2[idx].length()) == 0//先比较长度
                && (diff = versionArray1[idx].compareTo(versionArray2[idx])) == 0) {//再比较字符
            ++idx;
        }
        //如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大；
        diff = (diff != 0) ? diff : versionArray1.length - versionArray2.length;
        return diff;
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
}