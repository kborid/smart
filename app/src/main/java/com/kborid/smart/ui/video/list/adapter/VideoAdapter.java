package com.kborid.smart.ui.video.list.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.kborid.smart.R;
import com.kborid.smart.entity.VideoData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private Context mContext;
    private List<VideoData> videoDatas = new ArrayList<>();
    private RequestOptions mRequestOptions;

    public VideoAdapter(Context context) {
        this.mContext = context;
        mRequestOptions = new RequestOptions().placeholder(R.mipmap.ic_placeholder).override(Target.SIZE_ORIGINAL).centerCrop();
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideoViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_video_layout, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        int type = getItemViewType(position);
        VideoData videoData = videoDatas.get(position);
        Glide.with(mContext).load(videoData.getTopicImg()).into(holder.logoIV);
        holder.fromTV.setText(videoData.getTopicName());
        holder.playTimeTV.setText(String.format("%1$s次播放", String.valueOf(videoData.getPlayCount())));
        holder.videoplayer.setUp(
                videoData.getMp4_url(), JCVideoPlayer.SCREEN_LAYOUT_LIST,
                TextUtils.isEmpty(videoData.getDescription()) ? videoData.getTitle() + "" : videoData.getDescription());
        Glide.with(mContext).load(videoData.getCover())
                .apply(mRequestOptions)
                .into(holder.videoplayer.thumbImageView);
    }

    @Override
    public int getItemCount() {
        return videoDatas.size();
    }

    public void setData(List<VideoData> videoDatas) {
        this.videoDatas = videoDatas;
        notifyDataSetChanged();
    }

    public List<VideoData> getData() {
        return videoDatas;
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.videoplayer)
        JCVideoPlayerStandard videoplayer;
        @BindView(R.id.iv_logo)
        ImageView logoIV;
        @BindView(R.id.tv_from)
        TextView fromTV;
        @BindView(R.id.tv_play_time)
        TextView playTimeTV;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
