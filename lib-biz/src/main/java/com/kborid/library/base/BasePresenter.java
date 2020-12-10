package com.kborid.library.base;

public interface BasePresenter<T extends BaseView> {
    void attachView(T view);

    void detachView();
}
