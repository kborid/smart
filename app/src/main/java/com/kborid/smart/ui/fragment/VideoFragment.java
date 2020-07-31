package com.kborid.smart.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kborid.library.adapter.CommRVAdapter;
import com.kborid.library.adapter.ViewHolderHelper;
import com.kborid.library.base.BaseFragment;
import com.kborid.library.util.ImageLoaderUtils;
import com.kborid.smart.R;
import com.kborid.smart.constant.AppConstant;
import com.kborid.smart.di.DaggerCommonComponent;
import com.kborid.smart.entity.VideoData;
import com.kborid.smart.listener.RecyclerItemClickListener;
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

    private CommRVAdapter<VideoData> mVideoAdapter;

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
        mVideoAdapter = new CommRVAdapter<VideoData>(getContext(), R.layout.item_video_layout) {
            @Override
            protected void convert(ViewHolderHelper helper, VideoData videoData) {
                helper.setImageUrl(R.id.iv_logo, videoData.getTopicImg());
                helper.setText(R.id.tv_from, videoData.getTopicName());
                helper.setText(R.id.tv_play_time, String.format("%1$s次播放", String.valueOf(videoData.getPlayCount())));
                JCVideoPlayerStandard videoplayer = helper.getView(R.id.videoplayer);
                videoplayer.setUp(
                        videoData.getMp4_url(), JCVideoPlayer.SCREEN_LAYOUT_LIST,
                        TextUtils.isEmpty(videoData.getDescription()) ? videoData.getTitle() : videoData.getDescription());
                ImageLoaderUtils.display(getContext(), videoplayer.thumbImageView, videoData.getCover());
            }
        };
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
            mVideoAdapter.set(videoDatas);
        }
    }
}
