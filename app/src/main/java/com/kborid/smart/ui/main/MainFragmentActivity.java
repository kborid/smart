package com.kborid.smart.ui.main;

import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.kborid.smart.PRJApplication;
import com.kborid.smart.R;
import com.kborid.smart.activity.FragmentAdapter;
import com.kborid.smart.ui.news.tab.NewsTabFragment;
import com.kborid.smart.ui.picture.tab.PicTabFragment;
import com.kborid.smart.ui.user.tab.UserTabFragment;
import com.kborid.smart.ui.video.tab.VideoTabFragment;
import com.thunisoft.common.base.BaseSimpleActivity;
import com.thunisoft.ui.bottombar.NavigationBottomBar;
import com.thunisoft.ui.widget.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainFragmentActivity extends BaseSimpleActivity {

    private static final int DEFAULT_INDEX = 0;
    private static final String[] mBottomBarTitles = PRJApplication.getInstance().getResources().getStringArray(R.array.BottomBarTitle);

    @BindView(R.id.viewpager)
    CustomViewPager viewPager;
    @BindView(R.id.bottomBar)
    NavigationBottomBar bottomBar;

    @Override
    protected int getLayoutResId() {
        return R.layout.act_main;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        initFragment();
        // 兼容lib库bug
        bottomBar.check(DEFAULT_INDEX + 1);
        bottomBar.check(DEFAULT_INDEX);
        bottomBar.setOnTabCheckedListener(new NavigationBottomBar.OnTabCheckedListener() {
            @Override
            public void onTabChecked(int i) {
                viewPager.setCurrentItem(i, false);
            }
        });
    }

    private void initFragment() {
        List<Fragment> fragments = new ArrayList<>(4);
        fragments.add(NewsTabFragment.newInstance(mBottomBarTitles[0]));
        fragments.add(PicTabFragment.newInstance(mBottomBarTitles[1]));
        fragments.add(VideoTabFragment.newInstance(mBottomBarTitles[2]));
        fragments.add(UserTabFragment.newInstance(mBottomBarTitles[3]));
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), mBottomBarTitles, fragments));
        viewPager.setCurrentItem(DEFAULT_INDEX);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setSlidingEnabled(false);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bottomBar.check(position);
                changedStatusBar(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void changedStatusBar(int index) {
        Window window = getWindow();
        int flag = window.getDecorView().getSystemUiVisibility();
        if (index == 3) {
            flag = flag & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            window.setStatusBarColor(getResources().getColor(R.color.mainColor, null));
        } else {
            flag = flag | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            window.setStatusBarColor(getResources().getColor(R.color.white, null));
        }
        window.getDecorView().setSystemUiVisibility(flag);
    }
}
