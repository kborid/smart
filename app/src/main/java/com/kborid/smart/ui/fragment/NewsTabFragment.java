package com.kborid.smart.ui.fragment;

import android.database.Cursor;
import android.net.Uri;
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
import com.kborid.smart.entity.NewsChannelBean;
import com.kborid.smart.ui.presenter.NewsTabPresenter;
import com.kborid.smart.ui.presenter.contract.NewsTabContract;
import com.kborid.smart.util.DownloadHelper;
import com.kborid.smart.util.ToastDrawableUtil;
import com.liulishuo.filedownloader.FileDownloader;
import com.thunisoft.ui.util.ScreenUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class NewsTabFragment extends AppFragment<NewsTabPresenter> implements NewsTabContract.View {

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
        return R.layout.frag_tab_news;
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
        toolbar.setTitle(getArguments().getString("type"));
        mPresenter.loadNewsChannel();
    }

    @Override
    public void updateNewsChannel(List<NewsChannelBean> data) {
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

    @OnClick(R.id.add_channel_iv)
    public void add() {
        ToastDrawableUtil.showImgToast("测试完成");
        FileDownloader.setup(mContext);
//        DownloadHelper.getInstance().download3("TT.apk", "https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk");
        DownloadHelper.getInstance().download3("TT.apk", "https://cocall.thunisfot.com:8443/update/download");
//        Intent intent = new Intent(Settings.ACTION_APN_SETTINGS);
//        startActivity(intent);
    }

    public Map<String, String> checkAPN() {
        Map<String, String> map = new HashMap<String, String>();
        Cursor cr = mContext.getContentResolver().query(Uri.parse("content://telephony/carriers"), null, null, null, null);
        int i = 0;
        while (cr != null && cr.moveToNext()) {
            String id = cr.getString(cr.getColumnIndex("_id"));
            map.put("id" + i, id);
            String apn = cr.getString(cr.getColumnIndex("apn"));
            map.put("apn" + i, apn);
        }
        return map;
    }
}
