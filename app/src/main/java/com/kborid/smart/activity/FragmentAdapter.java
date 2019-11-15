package com.kborid.smart.activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

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
    private List<Fragment> mList;

    public FragmentAdapter(FragmentManager fm, String[] title, List<Fragment> list) {
        super(fm);
        mTitles = Arrays.asList(title);
        this.mList = list;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
