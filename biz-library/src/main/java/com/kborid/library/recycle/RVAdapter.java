package com.kborid.library.recycle;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class RVAdapter<T> extends RecyclerView.Adapter<RViewHolder> {

    Context mContext;
    int mLayoutId;

    @NonNull
    @Override
    public RViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return RViewHolder.get(mContext, parent, mLayoutId);
    }

    @Override
    public void onBindViewHolder(@NonNull RViewHolder holder, int position) {
        convert(holder, null);
    }

    protected abstract void convert(RViewHolder viewHolder, T t);

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
