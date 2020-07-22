package com.kborid.smart.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kborid.library.adapter.FragmentAdapter;
import com.kborid.smart.PRJApplication;
import com.kborid.smart.R;
import com.kborid.smart.ui.fragment.NewsTabFragment;
import com.kborid.smart.ui.fragment.PhotoTabFragment;
import com.kborid.smart.ui.fragment.UserTabFragment;
import com.kborid.smart.ui.fragment.VideoTabFragment;
import com.thunisoft.common.base.BaseSimpleActivity;
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
    BottomNavigationView bottomBar;

    @Override
    protected int getLayoutResId() {
        return R.layout.act_main;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        initFragment();
        bottomBar.setOnNavigationItemSelectedListener(menuItem -> {
            int order = menuItem.getOrder();
            viewPager.setCurrentItem(order);
            return true;
        });
    }

    private void initFragment() {
        List<Fragment> fragments = new ArrayList<>(mBottomBarTitles.length);
        fragments.add(NewsTabFragment.newInstance(mBottomBarTitles[0]));
        fragments.add(PhotoTabFragment.newInstance(mBottomBarTitles[1]));
        fragments.add(VideoTabFragment.newInstance(mBottomBarTitles[2]));
        fragments.add(UserTabFragment.newInstance(mBottomBarTitles[3]));
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), mBottomBarTitles, fragments));
        viewPager.setCurrentItem(DEFAULT_INDEX);
        viewPager.setOffscreenPageLimit(mBottomBarTitles.length);
        viewPager.setSlidingEnabled(false);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                int itemId = bottomBar.getMenu().getItem(position).getItemId();
                bottomBar.setSelectedItemId(itemId);
            }
        });
    }

    private void changedStatusBar(int index) {
        Window window = getWindow();
        int flag = window.getDecorView().getSystemUiVisibility();
        if (index == 3) {
            flag &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            window.setStatusBarColor(getResources().getColor(R.color.mainColor, null));
        } else {
            flag |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            window.setStatusBarColor(getResources().getColor(R.color.white, null));
        }
        window.getDecorView().setSystemUiVisibility(flag);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
