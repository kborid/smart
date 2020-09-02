package com.kborid.smart.ui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kborid.library.listener.RecyclerItemClickListener;
import com.kborid.smart.R;
import com.kborid.smart.base.AppFragment;
import com.kborid.smart.constant.AppConstant;
import com.kborid.smart.entity.NewsSummary;
import com.kborid.smart.ui.activity.NewsDetailActivity;
import com.kborid.smart.ui.adapter.NewsAdapter;
import com.kborid.smart.ui.presenter.NewsPresenter;
import com.kborid.smart.ui.presenter.contract.NewsContract;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import butterknife.BindView;

public class NewsFragment extends AppFragment<NewsPresenter> implements NewsContract.View {

    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.recycleView)
    RecyclerView recyclerView;

    private String mNewsId;
    private String mNewsType;
    private int mStartPage = 0;

    private NewsAdapter mNewsAdapter;

    @Override
    protected void initInject() {
        getComponent().inject(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.frag_news;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        if (getArguments() != null) {
            mNewsId = getArguments().getString(AppConstant.NEWS_ID);
            mNewsType = getArguments().getString(AppConstant.NEWS_TYPE);
        }
        mPresenter.getNewsList(mNewsType, mNewsId, mStartPage);
        mNewsAdapter = new NewsAdapter(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3, RecyclerView.HORIZONTAL, false));
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, RecyclerView.VERTICAL));
//        recyclerView.setLayoutManager(new RecyclerView.LayoutManager() {
//            @Override
//            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
//                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            }
//        });
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), RecyclerView.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutAnimationListener(null);
        recyclerView.setAdapter(mNewsAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener<>(getContext(), new RecyclerItemClickListener.OnItemClickListener<NewsSummary>() {
            @Override
            public void onItemClick(View view, NewsSummary entity, int position) {
                if (null == entity) {
                    return;
                }
                String postId = entity.getPostid();
                List<NewsSummary.ImgextraBean> imageExtra = entity.getImgextra();
                if (StringUtils.isNotBlank(postId) && (null == imageExtra || imageExtra.size() == 0)) {
                    NewsDetailActivity.startAction(getContext(), view.findViewById(R.id.iv_icon), entity.getPostid(), entity.getImgsrc());
                }
            }

            @Override
            public void onItemLongClick(View view, NewsSummary entity, int position) {

            }
        }));

        smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mStartPage++;
                mPresenter.getNewsList(mNewsType, mNewsId, mStartPage);
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mStartPage = 0;
                mPresenter.getNewsList(mNewsType, mNewsId, mStartPage);
            }
        });
    }

    @Override
    public void refreshNewsList(List<NewsSummary> newsSummaryList) {
        smartRefreshLayout.finishRefresh();
        smartRefreshLayout.finishLoadMore();
        if (null != newsSummaryList) {
            mNewsAdapter.setNewsData(newsSummaryList);
        }
    }
}
