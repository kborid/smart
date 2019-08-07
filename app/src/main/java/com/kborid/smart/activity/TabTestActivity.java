package com.kborid.smart.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.kborid.smart.R;
import com.kborid.smart.fragment.first.FragmentFirst;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TabTestActivity extends SimpleActivity {

    private static final String TAG = TabTestActivity.class.getSimpleName();

    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.vp_msg)
    ViewPager mViewPager;

    private static final String[] tabLabels = {"TabOne", "TabTwo", "TabThree"};
    private List<Fragment> mFragments = new ArrayList<>();

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_tablayout_test;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        super.initEventAndData(savedInstanceState);
        initViewPager();
    }

    private void initViewPager() {
        for (int i = 0; i < tabLabels.length; i++) {
            mFragments.add(FragmentFirst.newInstance());
        }
        mViewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), tabLabels, mFragments));
        mViewPager.setOffscreenPageLimit(tabLabels.length);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
