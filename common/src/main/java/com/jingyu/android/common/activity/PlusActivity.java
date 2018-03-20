package com.jingyu.android.common.activity;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jingyu.android.common.log.Logger;
import com.jingyu.android.common.util.InputUtil;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * @author fengjingyu@foxmail.com
 */
public abstract class PlusActivity extends AppCompatActivity {

    @SuppressWarnings("unchecked")
    public <T extends View> T getViewById(int id) {
        return (T) findViewById(id);
    }

    @SuppressWarnings("unchecked")
    public <T extends Fragment> T getFragmentByTag(String tag) {
        return (T) getSupportFragmentManager().findFragmentByTag(tag);
    }

    public Activity getActivity() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            Logger.d(this + "回收后重新创建了");
        }

        ActivityManager.addActivityToStack(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.delActivityFromStack(this);
    }

    @Override
    public void finish() {
        // 隐藏键盘
        InputUtil.hiddenInputMethod(this);
        super.finish();
    }

    public void addFragment(int layout_id, Fragment fragment, String tag, boolean isToBackStack) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(layout_id, fragment, tag);
        if (isToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.commitAllowingStateLoss();
        getSupportFragmentManager().executePendingTransactions();
    }

    public void addFragment(int layout_id, Fragment fragment, boolean isToBackStack) {
        addFragment(layout_id, fragment, fragment.getClass().getSimpleName(), isToBackStack);
    }

    public void addFragment(int layout_id, Fragment fragment) {
        addFragment(layout_id, fragment, fragment.getClass().getSimpleName(), false);
    }

    public void replaceFragment(int layout_id, Fragment fragment, String tag, boolean isToBackStack) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(layout_id, fragment, tag);
        if (isToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.commitAllowingStateLoss();
        getSupportFragmentManager().executePendingTransactions();
    }

    public void replaceFragment(int layout_id, Fragment fragment, boolean isToBackStack) {
        replaceFragment(layout_id, fragment, fragment.getClass().getSimpleName(), isToBackStack);
    }

    public void replaceFragment(int layout_id, Fragment fragment) {
        replaceFragment(layout_id, fragment, fragment.getClass().getSimpleName(), false);
    }

    public void removeFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.remove(fragment);
        ft.commitAllowingStateLoss();
        getSupportFragmentManager().executePendingTransactions();
    }

    public void removeFragments(List<Fragment> fragments) {
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment != null) {
                    removeFragment(fragment);
                }
            }
        }
    }

    public void removeAllFragments() {
        // 调用陔方法之后,如果再次调用,getFragments()有size,但是元素为null
        removeFragments(getSupportFragmentManager().getFragments());
    }

    public void showFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.show(fragment);
        ft.commitAllowingStateLoss();
        getSupportFragmentManager().executePendingTransactions();
    }

    public Fragment showFragmentByClass(Class<? extends Fragment> fragment_class, int layout_id) {
        Fragment fragment = getFragmentByTag(fragment_class.getSimpleName());
        if (fragment == null) {
            try {
                Constructor<? extends Fragment> cons = fragment_class.getConstructor();
                fragment = cons.newInstance();
                addFragment(layout_id, fragment);
            } catch (Exception e) {
                e.printStackTrace();
                Logger.e("showFragmentByClass()异常--" + fragment_class, e);
            }
        } else {
            showFragment(fragment);
        }
        return fragment;
    }

    public void hideFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.hide(fragment);
        ft.commitAllowingStateLoss();
        getSupportFragmentManager().executePendingTransactions();
    }

    public void hideFragment(List<Fragment> fragments) {
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment != null) {
                    hideFragment(fragment);
                }
            }
        }
    }

    public void hideAllFragment() {
        hideFragment(getSupportFragmentManager().getFragments());
    }

    /**
     * @param permission android.Manifest.permission
     */
    protected boolean isPermissionGranted(String permission) {
        return ContextCompat.checkSelfPermission(getActivity(), permission) == PackageManager.PERMISSION_GRANTED;
    }

    protected void permissionRequest(String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions(getActivity(), permissions, requestCode);
    }

}
