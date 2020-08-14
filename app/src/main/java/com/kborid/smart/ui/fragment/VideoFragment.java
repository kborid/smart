package com.kborid.smart.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kborid.library.adapter.CommRVAdapter;
import com.kborid.library.adapter.RViewHolder;
import com.kborid.library.base.BaseFragment;
import com.kborid.library.util.ImageLoaderUtils;
import com.kborid.smart.R;
import com.kborid.smart.constant.AppConstant;
import com.kborid.smart.di.DaggerCommonComponent;
import com.kborid.smart.entity.VideoData;
import com.kborid.smart.ui.presenter.VideoPresenter;
import com.kborid.smart.ui.presenter.contract.VideoContract;

import java.util.List;

import butterknife.BindView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class VideoFragment extends BaseFragment<VideoPresenter> implements VideoContract.View {

    @BindView(R.id.recycleView)
    RecyclerView recyclerView;

    private String mVideoType;
    private int mStartPage = 0;

    private CommRVAdapter<VideoData> adapter;

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
        adapter = new CommRVAdapter<VideoData>(getContext(), R.layout.item_video_layout) {
            @Override
            protected void convert(RViewHolder viewHolder, VideoData videoData) {
                viewHolder.setImageUrl(R.id.iv_logo, videoData.getTopicImg());
                viewHolder.setText(R.id.tv_from, videoData.getTopicName());
                viewHolder.setText(R.id.tv_play_time, String.format("%1$s次播放", String.valueOf(videoData.getPlayCount())));
                JCVideoPlayerStandard vp = viewHolder.getView(R.id.videoplayer);
                vp.setUp(
                        videoData.getMp4_url(), JCVideoPlayer.SCREEN_LAYOUT_LIST,
                        TextUtils.isEmpty(videoData.getDescription()) ? videoData.getTitle() : videoData.getDescription());
                ImageLoaderUtils.display(getContext(), vp.thumbImageView, videoData.getCover());
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void refreshVideoList(List<VideoData> videoDatas) {
        if (null != adapter) {
            adapter.getDataIO().set(videoDatas);
        }
    }
}
