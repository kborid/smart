package com.kborid.smart.ui.snaphelper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kborid.smart.R;
import com.kborid.smart.entity.PhotoGirl;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SnapAdapter extends RecyclerView.Adapter<SnapAdapter.SnapViewHolder> {

    private Context context;
    private List<PhotoGirl> mData;

    public SnapAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public SnapViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SnapViewHolder(LayoutInflater.from(context).inflate(R.layout.lv_recycle_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SnapViewHolder holder, int position) {
        Glide.with(context).load(mData.get(position).getUrl()).into(holder.iv_pic);
    }

    @Override
    public int getItemCount() {
        if (null != mData) {
            return mData.size();
        }
        return 0;
    }

    public void updateData(List<PhotoGirl> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public class SnapViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_pic)
        ImageView iv_pic;

        public SnapViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
