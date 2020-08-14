package com.kborid.library.listener;

import android.view.View;
import android.view.ViewGroup;

public interface OnItemClickListener<T> {

    void onItemClick(ViewGroup parent, View view, T entity, int position);

    boolean onItemLongClick(ViewGroup parent, View view, T entity, int position);
}