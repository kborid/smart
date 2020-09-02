package com.kborid.smart.ui.presenter;

import com.kborid.library.base.RxPresenter;
import com.kborid.smart.entity.NewsSummary;
import com.kborid.smart.network.ApiManager;
import com.kborid.smart.ui.presenter.contract.NewsContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class NewsPresenter extends RxPresenter<NewsContract.View> implements NewsContract.Presenter {
    @Inject
    public NewsPresenter() {
    }

    @Override
    public void getNewsList(String type, String id, int start, boolean refresh) {
        Disposable disposable = ApiManager.getNewsList(type, id, start).subscribe(new Consumer<List<NewsSummary>>() {
            @Override
            public void accept(List<NewsSummary> newsSummaryList) throws Exception {
                mView.refreshNewsList(newsSummaryList, refresh);
            }
        });
        addSubscribe(disposable);
    }
}
