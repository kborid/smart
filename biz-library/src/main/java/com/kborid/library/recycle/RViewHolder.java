package com.kborid.library.recycle;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

public class RViewHolder extends RecyclerView.ViewHolder {
    SparseArray<View> mViews;
    View mCurrentView;

    private RViewHolder(View itemView) {
        super(itemView);
        mCurrentView = itemView;
        mViews = new SparseArray<>();
    }

    public static RViewHolder get(Context context, ViewGroup parent, int layoutId) {
        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new RViewHolder(view);
    }

    /**
     * 通过viewId获取布局子控件
     *
     * @param viewId 控件id
     * @return 子控件
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mCurrentView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getCurrentView() {
        return mCurrentView;
    }
}
