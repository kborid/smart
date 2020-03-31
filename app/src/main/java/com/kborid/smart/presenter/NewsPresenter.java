package com.kborid.smart.presenter;

import com.kborid.library.base.RxPresenter;
import com.kborid.smart.entity.NewsSummary;
import com.kborid.smart.network.ApiManager;
import com.kborid.smart.presenter.contract.NewsContract;
import com.thunisoft.common.network.callback.ResponseCallback;
import com.thunisoft.common.util.ToastUtils;

import java.util.List;

import javax.inject.Inject;

public class NewsPresenter extends RxPresenter<NewsContract.View> implements NewsContract.Presenter {
    @Inject
    public NewsPresenter() {
    }

    @Override
    public void getNewsList(String type, String id, int start) {
        ApiManager.getNewsList(type, id, start, new ResponseCallback<List<NewsSummary>>() {
            @Override
            public void failure(Throwable throwable) {
                ToastUtils.showToast(throwable.getMessage());
            }

            @Override
            public void success(List<NewsSummary> newsSummaryList) {
                if (null != mView) {
                    mView.refreshNewsList(newsSummaryList);
                }
            }
        });
    }
}
