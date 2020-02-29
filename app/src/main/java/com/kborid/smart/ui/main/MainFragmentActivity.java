package com.kborid.smart.ui.main;

import android.os.Bundle;

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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainFragmentActivity extends BaseSimpleActivity {

    private static final int DEFAULT_INDEX = 0;
    private static final String[] mBottomBarTitles = PRJApplication.getInstance().getResources().getStringArray(R.array.BottomBarTitle);

    @BindView(R.id.viewpager)
    ViewPager viewPager;
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
                viewPager.setCurrentItem(i, true);
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
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bottomBar.check(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
