package com.kborid.smart.listener;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.kborid.library.util.LogUtils;
import com.kborid.smart.ui.picture.tab.PicTabFragment;

public class RecyclerItemClickListener extends RecyclerView.SimpleOnItemTouchListener {
    private GestureDetector mGestureDetector;
    private View childView;
    private RecyclerView recyclerView;

    public RecyclerItemClickListener(Context context, final OnItemClickListener mListener) {
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent ev) {
                if (childView != null && mListener != null) {
                    mListener.onItemClick(childView, recyclerView.getChildPosition(childView));
                }
                return true;
            }

            @Override
            public void onLongPress(MotionEvent ev) {
                if (childView != null && mListener != null) {
                    mListener.onLongClick(childView, recyclerView.getChildPosition(childView));
                }
            }
        });
    }

    public RecyclerItemClickListener(PicTabFragment picTabFragment, PicTabFragment mListener) {
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onLongClick(View view, int position);
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
        childView = rv.findChildViewUnder(e.getX(), e.getY());
        recyclerView = rv;
        return false;
    }
}