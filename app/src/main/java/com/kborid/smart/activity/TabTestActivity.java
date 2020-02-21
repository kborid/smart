package com.kborid.smart.activity;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.kborid.smart.R;
import com.kborid.smart.ui.image.ImageFragment;
import com.kborid.smart.ui.image.adapter.PictureAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TabTestActivity extends SimpleActivity {

    private static final String TAG = TabTestActivity.class.getSimpleName();

    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.vp_msg)
    ViewPager mViewPager;

    private static final String[] tabLabels = {"Universal", "Picasso", "Glide"};
    private List<Fragment> tabFragments = new ArrayList<>();

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
        tabFragments.add(ImageFragment.newInstance(PictureAdapter.TYPE_UNIVERSAL));
        tabFragments.add(ImageFragment.newInstance(PictureAdapter.TYPE_PICASSO));
        tabFragments.add(ImageFragment.newInstance(PictureAdapter.TYPE_GLIDE));
        mViewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), tabLabels, tabFragments));
        mViewPager.setOffscreenPageLimit(tabLabels.length);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
