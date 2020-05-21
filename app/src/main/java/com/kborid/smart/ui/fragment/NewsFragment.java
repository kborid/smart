package com.kborid.smart.ui.fragment;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.*;

import com.kborid.library.base.BaseFragment;
import com.kborid.smart.R;
import com.kborid.smart.contant.AppConstant;
import com.kborid.smart.di.DaggerCommonComponent;
import com.kborid.smart.entity.NewsSummary;
import com.kborid.smart.listener.RecyclerItemClickListener;
import com.kborid.smart.ui.activity.NewsDetailActivity;
import com.kborid.smart.ui.adapter.NewsAdapter;
import com.kborid.smart.presenter.NewsPresenter;
import com.kborid.smart.presenter.contract.NewsContract;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import butterknife.BindView;

public class NewsFragment extends BaseFragment<NewsPresenter> implements NewsContract.View {

    @BindView(R.id.recycleView)
    RecyclerView recyclerView;

    private String mNewsId;
    private String mNewsType;
    private int mStartPage = 0;

    private NewsAdapter mNewsAdapter;

    @Override
    protected void initInject() {
        DaggerCommonComponent.builder()
                .commonModule(getCommonModule("news"))
                .build()
                .inject(this);
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
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.onDraw(c, parent, state);
            }

            @Override
            public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.onDrawOver(c, parent, state);
            }

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
            }
        });
        recyclerView.setAdapter(mNewsAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                NewsSummary newsSummary = mNewsAdapter.getData().get(position);
                String postId = newsSummary.getPostid();
                List<NewsSummary.ImgextraBean> imageExtra = newsSummary.getImgextra();
                if (StringUtils.isNotBlank(postId) && (null == imageExtra || imageExtra.size() == 0)) {
                    NewsDetailActivity.startAction(getContext(), view.findViewById(R.id.iv_icon), newsSummary.getPostid(), newsSummary.getImgsrc());
                }
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }

    @Override
    public void refreshNewsList(List<NewsSummary> newsSummaryList) {
        if (null != newsSummaryList) {
            mNewsAdapter.setNewsData(newsSummaryList);
        }
    }
}
