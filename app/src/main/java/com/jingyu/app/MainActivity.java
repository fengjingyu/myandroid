package com.jingyu.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.jingyu.android.common.activity.ActivityManager;
import com.jingyu.android.common.log.Logger;
import com.jingyu.app.fragment.maintab.TabFragmentFour;
import com.jingyu.app.fragment.maintab.TabFragmentOne;
import com.jingyu.app.middle.MyBaseActivity;
import com.jingyu.app.fragment.maintab.TabFragmentThree;
import com.jingyu.app.fragment.maintab.TabFragmentTwo;

public class MainActivity extends MyBaseActivity {

    public static final int CLICK_QUIT_INTERVAL = 1000;

    private long lastClickQuitTime;

    private RadioGroup tab_group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTabGroup();

    }

    private void initTabGroup() {
        tab_group = getViewById(R.id.tab_group);
        tab_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                hideAllFragment();
                if (checkedId == R.id.tab_item1) {
                    showFragmentByClass(TabFragmentOne.class, R.id.content);
                } else if (checkedId == R.id.tab_item2) {
                    showFragmentByClass(TabFragmentTwo.class, R.id.content);
                } else if (checkedId == R.id.tab_item3) {
                    showFragmentByClass(TabFragmentThree.class, R.id.content);
                } else if (checkedId == R.id.tab_item4) {
                    showFragmentByClass(TabFragmentFour.class, R.id.content);
                }
            }
        });
        RadioButton tab_item1 = getViewById(R.id.tab_item1);
        tab_item1.setChecked(true);
    }

    @Override
    public void onBackPressed() {
        long quitTime = System.currentTimeMillis();
        if (quitTime - lastClickQuitTime <= CLICK_QUIT_INTERVAL) {
            ActivityManager.appExit();
        } else {
            lastClickQuitTime = quitTime;
            Logger.shortToast("快速再按一次退出");
        }
    }

    public static void actionStart(Context activityContext) {
        activityContext.startActivity(new Intent(activityContext, MainActivity.class));
    }

}
