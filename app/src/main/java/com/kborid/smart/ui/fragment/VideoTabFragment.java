package com.kborid.smart.ui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.kborid.library.adapter.FragmentAdapter;
import com.kborid.smart.R;
import com.kborid.smart.base.AppFragment;
import com.kborid.smart.constant.AppConstant;
import com.kborid.smart.entity.VideoChannelBean;
import com.kborid.smart.ui.presenter.VideoTabPresenter;
import com.kborid.smart.ui.presenter.contract.VideoTabContract;
import com.thunisoft.ui.util.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class VideoTabFragment extends AppFragment<VideoTabPresenter> implements VideoTabContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    private FragmentAdapter adapter;

    @Override
    protected void initInject() {
        getComponent().inject(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.frag_tab_video;
    }

    public static Fragment newInstance(String type) {
        Fragment fragment = new VideoTabFragment();
        Bundle args = new Bundle();
        args.putString("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        toolbar.setTitle(getArguments().getString("type"));
        mPresenter.loadVideoChannel();
    }

    @Override
    public void updateVideoChannel(List<VideoChannelBean> data) {
        if (null != data) {
            List<String> channelNames = new ArrayList<>();
            List<Fragment> mNewsFragmentList = new ArrayList<>();
            for (int i = 0; i < data.size(); i++) {
                channelNames.add(data.get(i).getChannelName());
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

    private Fragment createListFragments(VideoChannelBean newsChannel) {
        Fragment fragment = new VideoFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstant.VIDEO_TYPE, newsChannel.getChannelId());
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
