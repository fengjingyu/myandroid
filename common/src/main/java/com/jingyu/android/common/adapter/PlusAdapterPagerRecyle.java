package com.jingyu.android.common.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author  fengjingyu@foxmail.com
 *  会回收的viewpager
 */
public class PlusAdapterPagerRecyle extends PagerAdapter {
    private List<? extends View> viewList;
    private List<String> titles;

    public PlusAdapterPagerRecyle(List<? extends View> viewList, List<String> titles) {
        super();
        this.viewList = viewList;
        this.titles = titles;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (null != titles) {
            return titles.get(position);
        }
        return "";
    }

    @Override
    public int getCount() {
        if (viewList != null) {
            return viewList.size();
        }
        return 0;
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = viewList.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = viewList.get(position);
        container.removeView(view);
    }
}