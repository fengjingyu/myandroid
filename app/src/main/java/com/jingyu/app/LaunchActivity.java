package com.jingyu.app;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.DownloadBuilder;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.callback.ForceUpdateListener;
import com.allenliu.versionchecklib.v2.callback.RequestVersionListener;
import com.jingyu.android.common.activity.ActivityManager;
import com.jingyu.android.common.exception.CrashHandler;
import com.jingyu.android.common.log.Logger;
import com.jingyu.android.common.util.ScreenUtil;
import com.jingyu.android.common.util.SystemUtil;
import com.jingyu.app.middle.MyEnv;
import com.jingyu.app.middle.MyHttp;
import com.jingyu.app.middle.MyImg;
import com.jingyu.app.middle.MySp;
import com.jingyu.app.middle.MyBaseActivity;
import com.jingyu.app.middle.MyFile;
import com.jingyu.app.middle.okhttp.control.HttpHandlerControler;
import com.jingyu.java.myokhttp.MyHttpClient;
import com.jingyu.java.myokhttp.handler.IMyHttpHandler;
import com.jingyu.java.myokhttp.handler.control.MyHttpHandlerController;
import com.jingyu.java.myokhttp.req.MyReqInfo;

import java.util.ArrayList;

/**
 * 动态权限的问题:初始化从application放到了LaunchActivity
 */
public class LaunchActivity extends MyBaseActivity {

    private DownloadBuilder downloadBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkPermission();
    }

    private void checkPermission() {
        boolean writePermission = isPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        boolean phoneStatePermission = isPermissionGranted(Manifest.permission.READ_PHONE_STATE);

        if (writePermission && phoneStatePermission) {
            init();
        } else {
            ArrayList<String> list = new ArrayList<String>();

            if (writePermission) {
                list.add(Manifest.permission.READ_PHONE_STATE);
            } else if (phoneStatePermission) {
                list.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            } else {
                list.add(Manifest.permission.READ_PHONE_STATE);
                list.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            permissionRequest(list.toArray(new String[list.size()]), REQUEST_CODE);
        }
    }

    public static final int REQUEST_CODE = 1001;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE:
                boolean isAllSuccess = true;
                for (int grantResult : grantResults) {
                    if (grantResult == PackageManager.PERMISSION_DENIED) {
                        isAllSuccess = false;
                        break;
                    }
                }
                if (isAllSuccess) {
                    init();
                } else {
                    SystemUtil.toSetting(getActivity());
                    Toast.makeText(getApplicationContext(), "权限被拒绝了", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void init() {
        initLog();

        initSp();

        initImageLoader();

        initHttp();

        initCrashHandler();

        simpleDeviceInfo();

        checkUpdate();
    }

    private void checkUpdate() {
        downloadBuilder = AllenVersionChecker
                .getInstance()
                .requestVersion()
                .setRequestUrl("https://www.baidu.com") //todo 服务器的升级检测接口
                .request(new RequestVersionListener() {
                    @Nullable
                    @Override
                    public UIData onRequestVersionSuccess(String result) {
                        //todo 拿到服务器返回的数据（downloadUrl和一些其他的UI数据），解析
                        //todo 如果是最新版本直接return null
                        if (true) {
                            MainActivity.actionStart(getActivity());
                            finish();
                            return null;
                        } else {
                            UIData uiData = UIData.create();
                            uiData.setDownloadUrl("http://test-1251233192.coscd.myqcloud.com/1_1.apk");
                            uiData.setTitle("更新的title");
                            uiData.setContent("更新的内容");
                            return uiData;
                        }
                    }

                    @Override
                    public void onRequestVersionFailure(String message) {
                        Toast.makeText(getActivity(), "网络不可用，请检查网络设置", Toast.LENGTH_LONG).show();
                        finish();
                    }
                })
                .setForceUpdateListener(new ForceUpdateListener() {
                    @Override
                    public void onShouldForceUpdate() {
                        //Toast.makeText(getActivity(), "onShouldForceUpdate", Toast.LENGTH_SHORT).show();
                        ActivityManager.appExit();
                    }
                });

        downloadBuilder.excuteMission(getActivity());
    }


    private void initLog() {
        Logger.Options options = new Logger.Options();
        options.consoleLogLevel = MyEnv.getConsoleLogLevel();
        options.isErrorLog2File = MyEnv.isErrorLog2File();
        options.isShowDebugToast = MyEnv.isShowDebugToast();
        options.logDir = MyFile.getLogDir(getApplicationContext());
        Logger.initLog(getApplication(), options);
    }

    private void initSp() {
        MySp.initSP(getApplication());
    }

    private void initHttp() {
        MyHttp.setMyHttpClient(new MyHttpClient() {
            @Override
            protected MyHttpHandlerController getHttpHandlerController(MyReqInfo myReqInfo, IMyHttpHandler iMyHttpHandler) {
                return new HttpHandlerControler(myReqInfo, iMyHttpHandler);
            }
        });
    }

    private void initImageLoader() {
        MyImg.initImg(getApplication());
    }

    private void initCrashHandler() {
        if (MyEnv.isInitCrashHandler()) {
            CrashHandler.getInstance().init(getApplication(), MyEnv.isShowExceptionActivity(), MyFile.getCrashDir(getApplicationContext()));
        }
    }

    // 设备启动时，输出设备与app的基本信息
    public void simpleDeviceInfo() {
        Logger.d("域名环境--" + MyEnv.CURRENT_RUN_ENVIRONMENT);
        Logger.d("deviceId--" + SystemUtil.getDeviceId(getApplicationContext()));
        Logger.d("model--" + SystemUtil.getModel());
        Logger.d("operatorName--" + SystemUtil.getOperatorName(getApplicationContext()));
        Logger.d("osversion--" + SystemUtil.getOSVersion());
        Logger.d("osversionsdkint--" + SystemUtil.getOSVersionSDKINT());
        Logger.d("phonebrand--" + SystemUtil.getPhoneBrand());
        Logger.d("simNum--" + SystemUtil.getSimSerialNum(getApplicationContext()));
        Logger.d("language--" + SystemUtil.getSysLanguage());
        Logger.d("versionCode--" + SystemUtil.getVersionCode(getApplicationContext()));
        Logger.d("versionName--" + SystemUtil.getVersionName(getApplicationContext()));
        Logger.d("screenHeightPx--" + ScreenUtil.getScreenHeightPx(getApplicationContext()));
        Logger.d("screenWidthPx--" + ScreenUtil.getScreenWidthPx(getApplicationContext()));
        Logger.d("getDensity--" + ScreenUtil.getDensity(getApplicationContext()));
        Logger.d("getDensityDpi--" + ScreenUtil.getDensityDpi(getApplicationContext()));
        Logger.d("screenHeightDP--" + ScreenUtil.getScreenHeightDP(getApplicationContext()));
        Logger.d("screenWidthDP--" + ScreenUtil.getScreenWidthDP(getApplicationContext()));
        Logger.d("statusBarHeightPx--" + ScreenUtil.getStatusBarHeight(getApplicationContext()));
    }

}
