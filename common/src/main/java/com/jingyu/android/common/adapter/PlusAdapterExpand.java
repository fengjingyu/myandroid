package com.jingyu.android.common.adapter;

import android.content.Context;
import android.widget.BaseExpandableListAdapter;

import java.util.List;

/**
 * @author fengjingyu@foxmail.com
 */
public abstract class PlusAdapterExpand<E, T> extends BaseExpandableListAdapter {
    /**
     * 子item
     */
    public List<List<T>> listChild;
    /**
     * 父item
     */
    public List<E> listParaent;
    public Context context;

    public PlusAdapterExpand(Context context, List<List<T>> listChild, List<E> listParaent) {
        this.listChild = listChild;
        this.listParaent = listParaent;
        this.context = context;
    }

    public void update(List<List<T>> listChild, List<E> listParaent) {
        this.listChild = listChild;
        this.listParaent = listParaent;
    }

    @Override
    public int getGroupCount() {
        if (listParaent != null) {
            return listParaent.size();
        }
        return 0;
    }

    @Override
    public E getGroup(int i) {
        if (listParaent != null) {
            return listParaent.get(i);
        }
        return null;
    }

    @Override
    public long getGroupId(int i) {

        return i;

    }

    @Override
    public int getChildrenCount(int i) {
        if (listChild != null && listChild.get(i) != null) {
            return listChild.get(i).size();
        }
        return 0;
    }


    @Override
    public T getChild(int groupPosition, int childPosition) {

        if (listChild != null && listChild.get(groupPosition) != null) {
            return listChild.get(groupPosition).get(childPosition);
        }
        return null;
    }


    @Override
    public long getChildId(int groupPosition, int childPosition) {

        return childPosition;

    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {

        return true;

    }


    @Override
    public boolean hasStableIds() {

        return true;

    }

}
