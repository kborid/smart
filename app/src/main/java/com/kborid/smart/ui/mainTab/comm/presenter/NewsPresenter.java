package com.kborid.smart.ui.mainTab.comm.presenter;

import com.kborid.library.base.RxPresenter;
import com.kborid.smart.ui.mainTab.comm.presenter.contract.NewsContract;

import javax.inject.Inject;

public class NewsPresenter extends RxPresenter<NewsContract.View> implements NewsContract.Presenter {
    @Inject
    public NewsPresenter() {
    }
}
