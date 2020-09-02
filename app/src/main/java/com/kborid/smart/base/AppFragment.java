package com.kborid.smart.base;

import com.kborid.library.base.BaseFragment;
import com.kborid.library.base.BasePresenter;
import com.kborid.smart.PRJApplication;
import com.kborid.smart.di.CommFragmentComponent;
import com.kborid.smart.di.DaggerCommFragmentComponent;

public abstract class AppFragment<T extends BasePresenter> extends BaseFragment<T> {

    @Override
    protected CommFragmentComponent getComponent() {
        return DaggerCommFragmentComponent.builder()
                .appComponent(PRJApplication.getAppComponent())
                .fragmentModule(getModule())
                .build();
    }
}