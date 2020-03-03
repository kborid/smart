package com.kborid.smart.ui.news.detail.presenter;

import com.kborid.library.base.RxPresenter;
import com.kborid.smart.entity.NewsDetail;
import com.kborid.smart.network.ApiManager;
import com.kborid.smart.ui.news.detail.presenter.contract.NewsDetailContract;
import com.thunisoft.common.network.callback.ResponseCallback;
import com.thunisoft.common.util.ToastUtils;

import javax.inject.Inject;

public class NewsDetailPresenter extends RxPresenter<NewsDetailContract.View> implements NewsDetailContract.Presenter {

    @Inject
    public NewsDetailPresenter() {
    }

    @Override
    public void getNewsDetail(String postId) {
        ApiManager.getNewsDetail(postId, new ResponseCallback<NewsDetail>() {
            @Override
            public void failure(Throwable throwable) {
                ToastUtils.showToast(throwable.getMessage());
            }

            @Override
            public void success(NewsDetail newsDetail) {
                mView.refreshInfo(newsDetail);
            }
        });
    }
}
