package com.kborid.smart.ui.main;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.kborid.library.base.BaseSimpleActivity;
import com.kborid.smart.R;
import com.kborid.smart.activity.FragmentAdapter;
import com.kborid.smart.ui.mainTab.MainTabFragment;
import com.thunisoft.ui.bottombar.NavigationBottomBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainFragmentActivity extends BaseSimpleActivity {

    private static final String[] titles = new String[]{"音乐", "聆听", "浏览", "我的"};
    private static final int DEFAULT_INDEX = 0;

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.bottomBar)
    NavigationBottomBar bottomBar;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(false);
        }
        List<Fragment> fragments = new ArrayList<>(5);
        fragments.add(MainTabFragment.newInstance(titles[0]));
        fragments.add(MainTabFragment.newInstance(titles[1]));
        fragments.add(MainTabFragment.newInstance(titles[2]));
        fragments.add(MainTabFragment.newInstance(titles[3]));
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), titles, fragments));
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
}
