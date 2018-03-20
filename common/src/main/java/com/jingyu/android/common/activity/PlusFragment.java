package com.jingyu.android.common.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author fengjingyu@foxmail.com
 */
public abstract class PlusFragment extends Fragment {

    private ViewGroup mContainer;

    @SuppressWarnings("unchecked")
    public <T extends View> T getViewById(int id) {
        return (T) mContainer.findViewById(id);
    }

    @SuppressWarnings("unchecked")
    public <T extends Fragment> T getFragmentByTag(String tag) {
        return (T) getChildFragmentManager().findFragmentByTag(tag);
    }

    public Intent getIntent() {
        if (getActivity() != null) {
            return getActivity().getIntent();
        }
        return null;
    }

    public View createView(LayoutInflater inflater, int layout_id, ViewGroup container) {
        return mContainer = (ViewGroup) inflater.inflate(layout_id, container, false);
    }

    public void addChildFragment(int layout_id, Fragment fragment, String tag, boolean isToBackStack) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.add(layout_id, fragment, tag);
        if (isToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.commitAllowingStateLoss();
        getChildFragmentManager().executePendingTransactions();
    }

    public void addChildFragment(int layout_id, Fragment fragment) {
        addChildFragment(layout_id, fragment, fragment.getClass().getSimpleName(), false);
    }

    public void replaceFragment(int layout_id, Fragment fragment, String tag, boolean isToBackStack) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.replace(layout_id, fragment, tag);
        if (isToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.commitAllowingStateLoss();
        getChildFragmentManager().executePendingTransactions();
    }

    public void replaceFragment(int layout_id, Fragment fragment) {
        replaceFragment(layout_id, fragment, fragment.getClass().getSimpleName(), false);
    }

    public void removeFragment(Fragment fragment) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.remove(fragment);
        ft.commitAllowingStateLoss();
        getChildFragmentManager().executePendingTransactions();
    }

    public void hideChildFragment(Fragment fragment) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.hide(fragment);
        ft.commitAllowingStateLoss();
        getChildFragmentManager().executePendingTransactions();
    }

    public void showChildFragment(Fragment fragment) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.show(fragment);
        ft.commitAllowingStateLoss();
        getChildFragmentManager().executePendingTransactions();
    }

    /**
     * hidden为true时, 是隐藏的状态 ; hidden为false时,是显示的状态
     * 该方法只有在对fragment进行了hide和show操作时,才会被调用,如果是被别的界面遮住了,是不会调用的
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    /**
     * @param permission android.Manifest.permission
     */
    protected boolean isPermissionGranted(String permission) {
        return ContextCompat.checkSelfPermission(getActivity(), permission) == PackageManager.PERMISSION_GRANTED;
    }

    protected void permissionRequest(String[] permissions, int requestCode) {
        // 这里不要使用ActivityCompat.requestPermission,否则只会回调到Activity的onRequestPermissionsResult
        // 这个方法请求权限,activity如果有多个fragment,只会回调到请求了权限的fragment的onRequestPermissionsResult
        requestPermissions(permissions, requestCode);
    }
}
