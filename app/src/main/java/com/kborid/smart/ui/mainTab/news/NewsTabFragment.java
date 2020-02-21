package com.kborid.smart.ui.mainTab.news;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.kborid.library.base.BaseFragment;
import com.kborid.smart.R;
import com.kborid.smart.activity.FragmentAdapter;
import com.kborid.smart.contant.AppConstant;
import com.kborid.smart.di.DaggerCommonComponent;
import com.kborid.smart.entity.NewsChannelBean;
import com.kborid.smart.ui.mainTab.comm.NewsFragment;
import com.kborid.smart.ui.mainTab.news.presenter.NewsTabPresenter;
import com.kborid.smart.ui.mainTab.news.presenter.contract.NewsTabContract;
import com.thunisoft.ui.util.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class NewsTabFragment extends BaseFragment<NewsTabPresenter> implements NewsTabContract.View {

    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    private FragmentAdapter adapter;

    @Override
    protected void initInject() {
        DaggerCommonComponent.builder()
                .commonModule(getCommonModule("newsTab"))
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_tab_news;
    }

    public static Fragment newInstance(String type) {
        Fragment fragment = new NewsTabFragment();
        Bundle args = new Bundle();
        args.putString("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        mPresenter.loadMainChannel();
    }

    @Override
    public void updateMainChannel(List<NewsChannelBean> data) {
        if (null != data) {
            List<String> channelNames = new ArrayList<>();
            List<Fragment> mNewsFragmentList = new ArrayList<>();
            for (int i = 0; i < data.size(); i++) {
                channelNames.add(data.get(i).getNewsChannelName());
                mNewsFragmentList.add(createListFragments(data.get(i)));
            }

            if (null == adapter) {
                adapter = new FragmentAdapter(getChildFragmentManager(), channelNames, mNewsFragmentList);
            } else {
                //刷新fragment
                adapter.setFragments(channelNames, mNewsFragmentList);
            }
            viewPager.setAdapter(adapter);
            tabs.setupWithViewPager(viewPager);
            dynamicSetTabLayoutMode(tabs);
        }
    }

    private Fragment createListFragments(NewsChannelBean newsChannel) {
        Fragment fragment = new NewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstant.NEWS_ID, newsChannel.getNewsChannelId());
        bundle.putString(AppConstant.NEWS_TYPE, newsChannel.getNewsChannelType());
        bundle.putInt(AppConstant.CHANNEL_POSITION, newsChannel.getNewsChannelIndex());
        fragment.setArguments(bundle);
        return fragment;
    }

    private void dynamicSetTabLayoutMode(TabLayout tabLayout) {
        int tabWidth = calculateTabWidth(tabLayout);
        int screenWidth = ScreenUtils.mScreenWidth;

        if (tabWidth <= screenWidth) {
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
        } else {
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
    }

    private int calculateTabWidth(TabLayout tabLayout) {
        int tabWidth = 0;
        for (int i = 0; i < tabLayout.getChildCount(); i++) {
            final View view = tabLayout.getChildAt(i);
            view.measure(0, 0); // 通知父view测量，以便于能够保证获取到宽高
            tabWidth += view.getMeasuredWidth();
        }
        return tabWidth;
    }
}
