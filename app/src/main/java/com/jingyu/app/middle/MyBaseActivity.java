package com.jingyu.app.middle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import com.jingyu.android.common.activity.PlusActivity;
import com.jingyu.android.common.log.Logger;
import com.jingyu.android.common.util.BroadcastUtil;

/**
 * @author fengjingyu@foxmail.com
 * @description
 */
public abstract class MyBaseActivity extends PlusActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Logger.d(this + "---onCreate");
        super.onCreate(savedInstanceState);
        hiddenTitleActionBar();
        initReceiver();
    }

    public void hiddenTitleActionBar() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.hide();
        }
    }

    /**
     * 是否有网络的回调，可能统一处理应用对网络转换的逻辑
     */
    private BroadcastReceiver mNetReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = mConnectivityManager.getActiveNetworkInfo();
            boolean hasConnectivity = (info != null && info.isConnected());

            if (hasConnectivity) {
                onNetNormal();
            } else {
                onNetLoss();
            }
        }
    };

    protected void onNetNormal() {
    }

    protected void onNetLoss() {
    }

    protected void initReceiver() {
        BroadcastUtil.register(this, 1000, ConnectivityManager.CONNECTIVITY_ACTION, mNetReceiver);
    }

    protected void unbindReceiver() {
        BroadcastUtil.unRegister(this, mNetReceiver);
    }

    @Override
    protected void onDestroy() {
        unbindReceiver();
        Logger.d(this + "---onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        Logger.d(this + "---onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Logger.d(this + "---onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Logger.d(this + "---onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Logger.d(this + "---onStop");
        super.onStop();
    }

    @Override
    protected void onRestart() {
        Logger.d(this + "---onRestart");
        super.onRestart();
    }

    @Override
    public void finish() {
        Logger.d(this + "---finish");
        super.finish();
    }

    @Override
    public void onBackPressed() {
        Logger.d(this + "---onBackPressed");
        super.onBackPressed();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Logger.d(this + "---onNewIntent");
        super.onNewIntent(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Logger.d(this + "---onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }
}
