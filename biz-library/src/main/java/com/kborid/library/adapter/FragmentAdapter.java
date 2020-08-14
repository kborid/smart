package com.kborid.library.adapter;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

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
        this(fm, Arrays.asList(title), fragments);
    }

    public FragmentAdapter(FragmentManager fm, List<String> title, List<Fragment> fragments) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mTitles = title;
        this.mFragments = fragments;
    }

    @NonNull
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
