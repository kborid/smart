package com.kborid.smart.ui.video.list;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kborid.library.base.BaseFragment;
import com.kborid.smart.R;
import com.kborid.smart.contant.AppConstant;
import com.kborid.smart.di.DaggerCommonComponent;
import com.kborid.smart.entity.VideoData;
import com.kborid.smart.listener.RecyclerItemClickListener;
import com.kborid.smart.ui.video.list.adapter.VideoAdapter;
import com.kborid.smart.ui.video.list.presenter.VideoPresenter;
import com.kborid.smart.ui.video.list.presenter.contract.VideoContract;

import java.util.List;

import butterknife.BindView;

public class VideoFragment extends BaseFragment<VideoPresenter> implements VideoContract.View {

    @BindView(R.id.recycleView)
    RecyclerView recyclerView;

    private String mVideoType;
    private int mStartPage = 0;

    private VideoAdapter mVideoAdapter;

    @Override
    protected void initInject() {
        DaggerCommonComponent.builder()
                .commonModule(getCommonModule("video"))
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.frag_news;
    }

    @Override
    protected void initEventAndData(Bundle bundle) {
        if (getArguments() != null) {
            mVideoType = getArguments().getString(AppConstant.VIDEO_TYPE);
        }
        mPresenter.getVideoList(mVideoType, mStartPage);
        mVideoAdapter = new VideoAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mVideoAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }

    @Override
    public void refreshVideoList(List<VideoData> videoDatas) {
        if (null != mVideoAdapter) {
            mVideoAdapter.setData(videoDatas);
        }
    }
}
