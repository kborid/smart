package com.kborid.smart.ui.mainTab.presenter;

import com.kborid.library.base.RxPresenter;
import com.kborid.smart.ui.mainTab.presenter.contract.MainTabContract;

import javax.inject.Inject;

public class MainTabPresenter extends RxPresenter<MainTabContract.View> implements MainTabContract.Presenter {
    @Inject
    public MainTabPresenter() {
    }
}
