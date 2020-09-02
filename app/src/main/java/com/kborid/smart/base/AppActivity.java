package com.kborid.smart.base;

import com.kborid.library.base.BaseActivity;
import com.kborid.library.base.BasePresenter;
import com.kborid.smart.PRJApplication;
import com.kborid.smart.di.CommActivityComponent;
import com.kborid.smart.di.DaggerCommActivityComponent;

public abstract class AppActivity<T extends BasePresenter> extends BaseActivity<T> {

    @Override
    protected CommActivityComponent getComponent() {
        return DaggerCommActivityComponent.builder()
                .appComponent(PRJApplication.getAppComponent())
                .activityModule(getModule())
                .build();
    }
}