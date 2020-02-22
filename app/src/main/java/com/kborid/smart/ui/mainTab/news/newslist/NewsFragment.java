package com.kborid.smart.ui.mainTab.news.newslist;

import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import com.kborid.library.base.BaseFragment;
import com.kborid.smart.R;
import com.kborid.smart.contant.AppConstant;
import com.kborid.smart.di.DaggerCommonComponent;
import com.kborid.smart.ui.mainTab.news.newslist.presenter.NewsPresenter;
import com.kborid.smart.ui.mainTab.news.newslist.presenter.contract.NewsContract;

import butterknife.BindView;

public class NewsFragment extends BaseFragment<NewsPresenter> implements NewsContract.View {

    @BindView(R.id.recycleView)
    RecyclerView recyclerView;

    private String mNewsId;
    private String mNewsType;
    private int mStartPage = 0;

    @Override
    protected void initInject() {
        DaggerCommonComponent.builder()
                .commonModule(getCommonModule("news"))
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        if (getArguments() != null) {
            mNewsId = getArguments().getString(AppConstant.NEWS_ID);
            mNewsType = getArguments().getString(AppConstant.NEWS_TYPE);
        }
        mPresenter.getNewsList(mNewsType, mNewsId, mStartPage);
    }
}
