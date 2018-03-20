package com.jingyu.android.common.adapter;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author  fengjingyu@foxmail.com
 */
public abstract class PlusAdapter<T> extends BaseAdapter {

    public List<T> list;
    public Context context;

    public PlusAdapter(Context context, List<T> list) {
        this.list = list;
        this.context = context;
    }

    public List<T> getList() {
        if (list == null) {
            return list = new ArrayList<T>();
        }
        return list;
    }

    public void update(List<T> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        if (list != null)
            return list.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (list != null) {
            return list.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        // 默认是返回1的 ， 即只有一种类型
        return super.getViewTypeCount();

        // 如果有多种类型，重写该方法
        // return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);

        // 如果有多种类型,重写该方法
        // if () {
        // return MY;
        // } else {
        // return SHE;
        // }
    }

}

