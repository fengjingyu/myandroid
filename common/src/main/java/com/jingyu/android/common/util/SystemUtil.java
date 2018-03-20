package com.jingyu.android.common.util;

import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.FileProvider;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.jingyu.android.common.log.Logger;
import com.jingyu.java.mytool.util.StringUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * @author fengjingyu@foxmail.com
 */
public class SystemUtil {

    public static int getPid() {
        return android.os.Process.myPid();
    }

    public static long getThreadId() {
        return Thread.currentThread().getId();
    }

    public static boolean isMainProcess(Context context) {
        String packageName = context.getPackageName();
        String processName = getProcessName(context);
        return packageName.equals(processName);
    }

    public static String getProcessName(Context context) {
        int pid = getPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return "";
    }

    public static int getVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            return -1;
        }
    }

    public static String getVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            return "-1";
        }
    }

    public static String getOSVersion() {
        return Build.VERSION.RELEASE;
    }

    public static int getOSVersionSDKINT() {
        return Build.VERSION.SDK_INT;
    }

    public static String getCPUInfos() {
        try {
            StringBuilder sb = new StringBuilder();
            FileReader fr = new FileReader("/proc/cpuinfo");
            BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
            String str = "";
            while ((str = localBufferedReader.readLine()) != null) {
                sb.append(str);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 模拟器 0000 0000 0000 000
     * XIAOMI 8619 4503 5963 123
     * 标准的imei是15位，但是有的是14位
     */
    public static String getDeviceId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    public static String getSysLanguage() {
        return Locale.getDefault().getLanguage();
    }

    public static String getModel() {
        return Build.MODEL;
    }

    public static String getPhoneBrand() {
        return Build.BRAND;
    }

    public static String getSimSerialNum(Context context) {
        return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getSimSerialNumber();
    }

    /**
     * 检测手机是否已插入SIM卡
     */
    public static boolean isCheckSimCardAvailable(Context context) {
        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm.getSimState() != TelephonyManager.SIM_STATE_READY) {
            return false;
        }
        return true;
    }

    /**
     * 获取运营商信息
     */
    public static String getOperatorName(Context con) {
        TelephonyManager telManager = (TelephonyManager) con.getSystemService(Context.TELEPHONY_SERVICE);
        String imsi = telManager.getSubscriberId();
        if (imsi != null && !"".equals(imsi)) {
            if (imsi.startsWith("46000") || imsi.startsWith("46002")) {// 因为移动网络编号46000下的IMSI已经用完，所以虚拟了一个46002编号，134/159号段使用了此编号
                return "中国移动";
            } else if (imsi.startsWith("46001")) {
                return "中国联通";
            } else if (imsi.startsWith("46003")) {
                return "中国电信";
            }
        }
        return "";
    }

    /**
     * 微信包名
     */
    public static final String WEIXIN_PACKAGE_NAME = "com.tencent.mm";
    /**
     * QQ包名
     */
    public static final String MOBILEQQ_PACKAGE_NAME = "com.tencent.mobileqq";

    /**
     * 判断微信是否安装
     *
     * @param context
     * @param appPackageName 应用包名
     * @return true 安装;  false 没安装
     */
    public static boolean checkAppInstall(Context context, String appPackageName) {
        if (null != context && !StringUtil.isBlank(appPackageName)) {
            final PackageManager packageManager = context.getPackageManager();
            List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
            if (pinfo != null) {
                for (int i = 0; i < pinfo.size(); i++) {
                    String pn = pinfo.get(i).packageName;
                    if (pn.equals(appPackageName)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 获取top的Activity的ComponentName
     */
    public static ComponentName getTopActivityCompomentName(Context paramContext) {
        List<ActivityManager.RunningTaskInfo> localList = null;
        if (paramContext != null) {
            ActivityManager localActivityManager = (ActivityManager) paramContext
                    .getSystemService(Context.ACTIVITY_SERVICE);
            if (localActivityManager != null) {
                localList = localActivityManager.getRunningTasks(1);

                if ((localList == null) || (localList.size() <= 0)) {
                    return null;
                }
            }
        }
        return localList.get(0).topActivity;
    }

    /**
     * 程序是否在前台运行
     *
     * @return true为前台
     */
    public static boolean isAppOnForeground(Context context) {

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        String packageName = context.getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();

        if (appProcesses == null) {
            return false;
        }

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {

            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }

    /**
     * 跳到发短信
     *
     * @param context 上下文
     * @param phone   电话
     * @param content 短信内容
     */
    public static void toSendSMS(Context context, String phone, String content) {
        try {
            Uri uri = Uri.parse("smsto:" + phone);
            Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
            intent.putExtra("sms_body", content);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Logger.shortToast("您没有短信功能，此功能不能正常进行！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 跳到外部电话
     *
     * @param context 上下文
     * @param phone   电话
     */
    public static void toDial(Context context, String phone) {
        if (!StringUtil.isBlank(phone)) {
            try {
                Uri uri = Uri.parse("tel:" + phone);
                Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Logger.shortToast("您没有拨打电话功能，此功能不能正常进行！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Intent getCallIntent(String phoneNum) {
        Uri uri = Uri.parse("tel:" + phoneNum);
        Intent intent = new Intent(Intent.ACTION_CALL, uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    /**
     * 跳到外部浏览器
     *
     * @param context 上下文
     * @param url     地址
     */
    public static void toWeb(Context context, String url) {
        if (!StringUtil.isBlank(url)) {
            try {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Logger.shortToast("您没有浏览器，此功能不能正常进行，请安装浏览器后在试！");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static void installApk(Context context, File file, String apkPackageName) {
        try {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT >= 24) {
                //Android 7.0 以上不支持 file://协议 需要通过 FileProvider 访问 sd卡 下面的文件，所以 Uri 需要通过 FileProvider 构造，协议为 content://
                //Android 7.0及以上,这种通过 FileProvider 形式取得的 Uri 只能在 7.0 以上的设备上使用，在以下的系统会报错
                Uri apkUri = FileProvider.getUriForFile(context, apkPackageName + ".fileprovider", file);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
            } else {
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            }
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.shortToast("安装出错");
        }
    }

    public static void uninstallApk(Context context, String packageName) {
        try {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_DELETE);
            intent.setData(Uri.parse("package:" + packageName));
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 图片存到系统相册
     */
    public static void saveFileToSystem(Context context, File file) {
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), file.getName(), null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));
    }

    /**
     * 模拟按home键效果
     */
    public static void pressHomeKey(Context context) {
        try {
            Intent i = new Intent(Intent.ACTION_MAIN);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addCategory(Intent.CATEGORY_HOME);
            context.startActivity(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void toSetting(Context context) {
        try {
            Uri packageURI = Uri.parse("package:" + context.getPackageName());
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.shortToast("打开设置界面失败");
        }

    }

    public static boolean toTencentMarket(Context context, String packageName) {
        Uri uri = Uri.parse("market://details?id=" + packageName);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        boolean result = true;
        try {
            goToMarket.setClassName("com.tencent.android.qqdownloader", "com.tencent.pangu.link.LinkProxyActivity");
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    /**
     * 获取application中指定的meta-data
     *
     * @return 如果没有获取成功(没有对应值，或者异常)，则返回值为空
     */
    public static String getAppMetaData(Context ctx, String key) {
        if (ctx == null || TextUtils.isEmpty(key)) {
            return null;
        }
        String resultData = null;
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        resultData = applicationInfo.metaData.get(key) + "";
                    }
                }

            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        return resultData;
    }

}