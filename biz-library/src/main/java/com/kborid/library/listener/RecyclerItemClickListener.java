package com.kborid.library.listener;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * RecyclerItemClickListener
 *
 * @description: 通过touch event模拟recycleView item的点击事件
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 * @version: 1.0.0
 * @date: 2020/8/14
 */
public class RecyclerItemClickListener<T> extends RecyclerView.SimpleOnItemTouchListener {
    private GestureDetector mGestureDetector;
    private View childView;
    private RecyclerView recyclerView;
    private IDataAdapter<T> adapter;

    public RecyclerItemClickListener(Context context, final OnItemClickListener<T> mListener) {
        this(context, null, mListener);
    }

    public RecyclerItemClickListener(Context context, IDataAdapter<T> adapter, final OnItemClickListener<T> mListener) {
        this.adapter = adapter;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent ev) {
                if (childView != null && mListener != null) {
                    int position = recyclerView.getChildAdapterPosition(childView);
                    mListener.onItemClick(childView, getDataByPosition(position), position);
                }
                return true;
            }

            @Override
            public void onLongPress(MotionEvent ev) {
                if (childView != null && mListener != null) {
                    int position = recyclerView.getChildAdapterPosition(childView);
                    mListener.onItemLongClick(childView, getDataByPosition(position), position);
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, @NonNull MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
        recyclerView = rv;
        childView = rv.findChildViewUnder(e.getX(), e.getY());
        return false;
    }

    private T getDataByPosition(int position) {
        if (null == adapter) {
            return null;
        }
        return (T) adapter.getData().get(position);
    }

    /**
     * OnItemClickListener
     *
     * @description: click item接口
     * @author: duanwei
     * @email: duanwei@thunisoft.com
     * @version: 1.0.0
     * @date: 2020/8/14
     */
    public interface OnItemClickListener<E> {
        void onItemClick(View view, E entity, int position);

        void onItemLongClick(View view, E entity, int position);
    }
}