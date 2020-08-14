package com.kborid.library.recycle;

public interface IRVItemView<T> {
    int getItemLayout();

    boolean canClickable();

    boolean isItemView(T entity, int position);

    void convert(RViewHolder viewHolder, T entity, int position);
}
