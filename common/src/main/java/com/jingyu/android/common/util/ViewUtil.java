package com.jingyu.android.common.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ListView;

@Deprecated
public class ViewUtil {

    @Deprecated
    public static void setGridViewStyle(GridView view, boolean show_bar, int num) {
        setGridViewStyle(view, show_bar, 0, 0, num);
    }

    @Deprecated
    public static void setGridViewStyle(GridView view, boolean show_bar) {
        setGridViewStyle(view, show_bar, 0, 0, 1);
    }

    @Deprecated
    public static void setGridViewStyle(GridView view, boolean show_bar, int space_h_px, int space_v_px, int num) {
        view.setCacheColorHint(Color.TRANSPARENT);
        view.setSelector(new ColorDrawable(Color.TRANSPARENT));
        view.setVerticalScrollBarEnabled(show_bar);
        view.setHorizontalSpacing(space_h_px);
        view.setVerticalSpacing(space_v_px);
        view.setNumColumns(num);
    }

    @Deprecated
    public static void setListViewStyle(ListView view, Drawable divider_drawable, int height_px, boolean show_bar) {
        view.setCacheColorHint(Color.TRANSPARENT);
        view.setSelector(new ColorDrawable(Color.TRANSPARENT));
        view.setDivider(divider_drawable);
        view.setDividerHeight(height_px);
        view.setVerticalScrollBarEnabled(show_bar);
    }

    @Deprecated
    public static void setListViewStyle(ListView view, boolean show_bar) {
        setListViewStyle(view, null, 0, show_bar);
    }

    @Deprecated
    public static void setExpandListViewStyle(Context context, ExpandableListView view, boolean show_bar, int groupIndicate) {
        view.setCacheColorHint(Color.TRANSPARENT);
        view.setSelector(new ColorDrawable(Color.TRANSPARENT));
        view.setVerticalScrollBarEnabled(show_bar);
        if (groupIndicate <= 0) {
            view.setGroupIndicator(null);
        } else {
            view.setGroupIndicator(context.getResources().getDrawable(groupIndicate));
        }
    }

}
