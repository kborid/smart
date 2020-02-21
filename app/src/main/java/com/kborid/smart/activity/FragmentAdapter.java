package com.kborid.smart.activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @description: fragment适配器
 * @date: 2019/7/1
 * @time: 20:13
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 */
public class FragmentAdapter extends FragmentPagerAdapter {
    private List<String> mTitles;
    private List<Fragment> mFragments;

    public FragmentAdapter(FragmentManager fm, String[] title, List<Fragment> fragments) {
        super(fm);
        this.mTitles = Arrays.asList(title);
        this.mFragments = fragments;
    }

    public FragmentAdapter(FragmentManager fm, List<String> title, List<Fragment> fragments) {
        super(fm);
        this.mTitles = title;
        this.mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    public void setFragments(List<String> title, List<Fragment> fragments) {
        this.mTitles = title;
        this.mFragments = fragments;
        notifyDataSetChanged();
    }
}
