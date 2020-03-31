package com.kborid.smart.presenter;

import com.kborid.library.base.RxPresenter;
import com.kborid.smart.entity.NewsChannelBean;
import com.kborid.smart.network.ChannelLoader;
import com.kborid.smart.presenter.contract.NewsTabContract;
import com.thunisoft.common.network.callback.ResponseCallback;
import com.thunisoft.common.util.ToastUtils;

import java.util.List;

import javax.inject.Inject;

public class NewsTabPresenter extends RxPresenter<NewsTabContract.View> implements NewsTabContract.Presenter {

    @Inject
    public NewsTabPresenter() {
    }

    @Override
    public void loadNewsChannel() {
        ChannelLoader.loadNewsChannel(new ResponseCallback<List<NewsChannelBean>>() {
            @Override
            public void failure(Throwable throwable) {
                ToastUtils.showToast(throwable.getMessage());
            }

            @Override
            public void success(List<NewsChannelBean> newsChannelBeans) {
                if (null != mView) {
                    mView.updateNewsChannel(newsChannelBeans);
                }
            }
        });
    }
}
