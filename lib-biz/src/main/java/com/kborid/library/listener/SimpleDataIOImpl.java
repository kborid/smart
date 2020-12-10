package com.kborid.library.listener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SimpleDataIOImpl<T, R extends RecyclerView.ViewHolder> implements IDataIO<T> {

    RecyclerView.Adapter<R> mAdapter;
    List<T> mDatas;

    public SimpleDataIOImpl(@NonNull List<T> mDatas, @NonNull RecyclerView.Adapter<R> adapter) {
        this.mDatas = mDatas;
        this.mAdapter = adapter;
    }

    @Override
    public void add(T element) {
        mDatas.add(element);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void addAt(int position, T element) {
        mDatas.add(position, element);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void addAll(List<T> elements) {
        mDatas.addAll(elements);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void addAllAt(int position, List<T> elements) {
        mDatas.addAll(position, elements);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void remove(T element) {
        mDatas.remove(element);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void removeAt(int index) {
        mDatas.remove(index);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void removeAll(List<T> elements) {
        mDatas.removeAll(elements);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void clear() {
        if (mDatas != null && mDatas.size() > 0) {
            mDatas.clear();
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void replace(T oldElem, T newElem) {
        replaceAt(mDatas.indexOf(oldElem), newElem);
    }

    @Override
    public void replaceAt(int index, T element) {
        mDatas.set(index, element);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void replaceAll(List<T> elements) {
        if (mDatas.size() > 0) {
            mDatas.clear();
        }
        mDatas.addAll(elements);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public T get(int position) {
        if (position >= mDatas.size())
            return null;
        return mDatas.get(position);
    }

    @Override
    public List<T> getAll() {
        return mDatas;
    }

    @Override
    public int getSize() {
        return null == mDatas ? 0 : mDatas.size();
    }

    @Override
    public boolean contains(T element) {
        return mDatas.contains(element);
    }

    @Override
    public void set(List<T> elements) {
        mDatas = elements;
        mAdapter.notifyDataSetChanged();
    }
}
