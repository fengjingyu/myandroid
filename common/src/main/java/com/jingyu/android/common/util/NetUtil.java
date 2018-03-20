package com.jingyu.android.common.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.jingyu.android.common.log.Logger;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * @author fengjingyu@foxmail.com
 *
 */
public class NetUtil {

    /**
     * 网络是否可用
     */
    public static boolean isNetworkAvailable(Context context) {

        ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = mConnectivityManager.getActiveNetworkInfo();

        return info != null && info.isConnected();
    }

    /**
     * 是否是wifi
     */
    public static boolean isNetWorkWifi(Context context) {
        if (isNetworkAvailable(context)) {
            ConnectivityManager conManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (conManager.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * ip地址
     */
    public static String getIpAddr() {
        String ip = "000.000.000.000";
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        ip = inetAddress.getHostAddress().toString();
                        return ip;
                    }
                }
            }
        } catch (SocketException ex) {
        }
        return ip;
    }

    /**
     * 获取网络类型
     *
     * @return String 返回网络类型
     */
    public static String getAccessNetworkType(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        int type = cm.getActiveNetworkInfo().getType();
        if (type == ConnectivityManager.TYPE_WIFI) {
            return "wifi";
        } else if (type == ConnectivityManager.TYPE_MOBILE) {
            return "phone";
        }
        return "";
    }

    /**
     * 获得当前网络类型
     *
     * @return TYPE_MOBILE_CMNET:1 TYPE_MOBILE_CMWAP:2 TYPE_WIFI:3 TYPE_NO:0(未知类型)
     */
    public static int getNetWorkType(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // 获得当前网络信息
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni != null && ni.isAvailable()) {
            int currentNetWork = ni.getType();
            if (currentNetWork == ConnectivityManager.TYPE_MOBILE) {
                if (ni.getExtraInfo() != null && ni.getExtraInfo().equals("cmwap")) {
                    Logger.d("", "当前网络为:cmwap网络");
                    return TYPE_MOBILE_CMWAP;
                } else if (ni.getExtraInfo() != null && ni.getExtraInfo().equals("uniwap")) {
                    Logger.d("", "当前网络为:uniwap网络");
                    return TYPE_MOBILE_CMWAP;
                } else if (ni.getExtraInfo() != null && ni.getExtraInfo().equals("3gwap")) {
                    Logger.d("", "当前网络为:3gwap网络");
                    return TYPE_MOBILE_CMWAP;
                } else if (ni.getExtraInfo() != null && ni.getExtraInfo().contains("ctwap")) {
                    Logger.d("", "当前网络为:" + ni.getExtraInfo() + "网络");
                    return TYPE_MOBILE_CTWAP;
                } else {
                    Logger.d("", "当前网络为:net网络");
                    return TYPE_MOBILE_CMNET;
                }

            } else if (currentNetWork == ConnectivityManager.TYPE_WIFI) {
                Logger.d("", "当前网络为:WIFI网络");
                return TYPE_WIFI;
            }
        }
        Logger.d("", "当前网络为:不是我们考虑的网络");
        return TYPE_NO;
    }

    public static final int TYPE_NO = 0;
    public static final int TYPE_MOBILE_CMNET = 1;
    public static final int TYPE_MOBILE_CMWAP = 2;
    public static final int TYPE_WIFI = 3;
    public static final int TYPE_MOBILE_CTWAP = 4; // 移动梦网代理

    /**
     * 获取当前的网络类型
     */
    public static String getCurrentNetType(Context context) {
        String type = "";
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) {
            type = "null";
        } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
            type = "0";
        } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
            int subType = info.getSubtype();
            if (subType == TelephonyManager.NETWORK_TYPE_CDMA || subType == TelephonyManager.NETWORK_TYPE_GPRS
                    || subType == TelephonyManager.NETWORK_TYPE_EDGE) {
                type = "1";//2g
            } else if (subType == TelephonyManager.NETWORK_TYPE_UMTS || subType == TelephonyManager.NETWORK_TYPE_HSDPA
                    || subType == TelephonyManager.NETWORK_TYPE_EVDO_A || subType == TelephonyManager.NETWORK_TYPE_EVDO_0
                    || subType == TelephonyManager.NETWORK_TYPE_EVDO_B) {
                type = "2";//3g
            } else if (subType == TelephonyManager.NETWORK_TYPE_LTE) {// LTE是3g到4g的过渡，是3.9G的全球标准
                type = "3";//4g
            }
        }
        return type;
    }

    /**
     * 获取网络类型
     *
     * @return 返回网络类型 2g 3g 4g wifi
     */
    public static String getNetworkType(Context context) {
        try {
            String strNetworkType = "";
            NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    strNetworkType = "WIFI";
                } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    String _strSubTypeName = networkInfo.getSubtypeName();
                    // TD-SCDMA   networkType is 17
                    int networkType = networkInfo.getSubtype();
                    switch (networkType) {
                        case TelephonyManager.NETWORK_TYPE_GPRS:
                        case TelephonyManager.NETWORK_TYPE_EDGE:
                        case TelephonyManager.NETWORK_TYPE_CDMA:
                        case TelephonyManager.NETWORK_TYPE_1xRTT:
                        case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
                            strNetworkType = "2G";
                            break;
                        case TelephonyManager.NETWORK_TYPE_UMTS:
                        case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        case TelephonyManager.NETWORK_TYPE_EVDO_A:
                        case TelephonyManager.NETWORK_TYPE_HSDPA:
                        case TelephonyManager.NETWORK_TYPE_HSUPA:
                        case TelephonyManager.NETWORK_TYPE_HSPA:
                        case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
                        case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
                        case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
                            strNetworkType = "3G";
                            break;
                        case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
                            strNetworkType = "4G";
                            break;
                        default:
                            // http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
                            if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA") || _strSubTypeName.equalsIgnoreCase("WCDMA") || _strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                                strNetworkType = "3G";
                            } else {
                                strNetworkType = _strSubTypeName;
                            }
                            break;
                    }
                }
            }
            return strNetworkType;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
