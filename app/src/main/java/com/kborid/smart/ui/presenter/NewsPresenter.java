package com.kborid.smart.ui.presenter;

import com.kborid.library.base.RxPresenter;
import com.kborid.smart.network.ApiManager;
import com.kborid.smart.ui.presenter.contract.NewsContract;
import io.reactivex.disposables.Disposable;

import javax.inject.Inject;

public class NewsPresenter extends RxPresenter<NewsContract.View> implements NewsContract.Presenter {
    @Inject
    public NewsPresenter() {
    }

    @Override
    public void getNewsList(String type, String id, int start, boolean refresh) {
        Disposable disposable = ApiManager.getNewsList(type, id, start).subscribe(newsSummaryList -> mView.refreshNewsList(newsSummaryList, refresh));
        addSubscribe(disposable);
    }
}
