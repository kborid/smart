package com.kborid.smart.ui.presenter;

import com.kborid.library.base.RxPresenter;
import com.kborid.smart.network.ChannelLoader;
import com.kborid.smart.ui.presenter.contract.NewsTabContract;
import com.thunisoft.common.network.util.RxUtil;

import javax.inject.Inject;

public class NewsTabPresenter extends RxPresenter<NewsTabContract.View> implements NewsTabContract.Presenter {

    @Inject
    public NewsTabPresenter() {
    }

    @Override
    public void loadNewsChannel() {
        ChannelLoader.loadNewsChannel()
                .subscribe(RxUtil.createDefaultSubscriber(newsChannelBeans -> mView.updateNewsChannel(newsChannelBeans)));
    }
}
