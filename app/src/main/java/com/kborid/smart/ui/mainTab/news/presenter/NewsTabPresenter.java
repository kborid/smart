package com.kborid.smart.ui.mainTab.news.presenter;

import com.kborid.library.base.RxPresenter;
import com.kborid.smart.entity.NewsChannelBean;
import com.kborid.smart.network.Api;
import com.kborid.smart.ui.mainTab.news.presenter.contract.NewsTabContract;
import com.thunisoft.common.network.callback.ResponseCallback;

import java.util.List;

import javax.inject.Inject;

public class NewsTabPresenter extends RxPresenter<NewsTabContract.View> implements NewsTabContract.Presenter {

    @Inject
    public NewsTabPresenter() {
    }

    @Override
    public void loadMainChannel() {
        Api.loadMainChannel(new ResponseCallback<List<NewsChannelBean>>() {
            @Override
            public void failure(Throwable throwable) {
                System.out.println(throwable.getMessage());
            }

            @Override
            public void success(List<NewsChannelBean> newsChannelBeans) {
                System.out.println("succ");
                mView.updateMainChannel(newsChannelBeans);
            }
        });
    }
}
