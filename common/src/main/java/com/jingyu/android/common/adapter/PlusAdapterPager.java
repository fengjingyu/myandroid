package com.jingyu.android.common.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fengjingyu@foxmail.com
 *  不会回收的viewpager
 */
public class PlusAdapterPager extends PagerAdapter {
    private List<? extends View> viewList;
    private List<String> titles;
    private Map<View, Boolean> map;

    @Override
    public CharSequence getPageTitle(int position) {
        if (null != titles) {
            return titles.get(position);
        }
        return "";
    }

    public PlusAdapterPager(List<? extends View> viewList, List<String> titles) {
        super();
        this.viewList = viewList;
        this.titles = titles;
        this.map = new HashMap<View, Boolean>();
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
        // 防止回收重新创建
        if (map.containsKey(view)) {
            return view;
        } else {
            container.addView(view);
            map.put(view, true);
        }
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }
}