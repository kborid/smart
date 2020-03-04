package com.kborid.smart.ui.tab.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.kborid.smart.R;
import com.kborid.smart.entity.PhotoGirl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PicViewHolder> {

    private Context mContext;
    private List<PhotoGirl> girls = new ArrayList<>();
    private RequestOptions requestOptions;

    public PhotoAdapter(Context context) {
        this.mContext = context;
        this.requestOptions = new RequestOptions()
                .placeholder(R.mipmap.ic_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .override(1024, 1024 / 3 * 4)
                .centerCrop();
    }

    @NonNull
    @Override
    public PicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_photo, parent, false);
        return new PicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PicViewHolder holder, int position) {
        PhotoGirl girl = girls.get(position);
        Glide.with(mContext).load(girl.getUrl()).apply(requestOptions).into(holder.iconIV);
    }

    @Override
    public int getItemCount() {
        return girls.size();
    }

    public void setPicList(List<PhotoGirl> girls) {
        this.girls = girls;
        notifyDataSetChanged();
    }

    public List<PhotoGirl> getData() {
        return girls;
    }

    public class PicViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_pic)
        ImageView iconIV;

        public PicViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
