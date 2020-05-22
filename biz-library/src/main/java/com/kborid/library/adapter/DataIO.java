package com.kborid.library.adapter;

import java.util.List;

public interface DataIO<T> {

    void set(List<T> elements);

    void add(T elements);

    void addAt(int position, T elements);

    void addAll(List<T> elements);

    void addAllAt(int position, List<T> elements);

    void remove(T elements);

    void removeAll(List<T> elements);

    void removeAt(int index);

    void clear();

    void replace(T oldElem, T newElem);

    void replaceAt(int index, T elements);

    void replaceAll(List<T> elements);

    List<T> getAll();

    T get(int position);

    int getSize();

    boolean contains(T elements);
}
