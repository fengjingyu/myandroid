package com.jingyu.app.middle;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jingyu.android.common.activity.PlusFragment;
import com.jingyu.android.common.log.Logger;


/**
 * @author fengjingyu@foxmail.com
 * @description
 */
public abstract class MyBaseFragment extends PlusFragment {
    @Override
    public void onAttach(Context context) {
        Logger.d(this + "--onAttach");
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Logger.d(this + "--onCreate");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Logger.d(this + "--onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public View createView(LayoutInflater inflater, int layout_id, ViewGroup container) {
        Logger.d(this + "--onCreateView?.createView()");
        return super.createView(inflater, layout_id, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Logger.d(this + "--onViewCreated");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Logger.d(this + "--onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        Logger.d(this + "--onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        Logger.d(this + "--onResume");
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Logger.d(this + "--onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        Logger.d(this + "--onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Logger.d(this + "--onStop");
        super.onStop();
    }

    @Override
    public void onLowMemory() {
        Logger.d(this + "--onLowMemory");
        super.onLowMemory();
    }

    @Override
    public void onDestroyView() {
        Logger.d(this + "--onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Logger.d(this + "--onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Logger.d(this + "--onDetach");
        super.onDetach();
    }

}
