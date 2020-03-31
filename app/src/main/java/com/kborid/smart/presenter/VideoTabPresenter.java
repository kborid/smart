package com.kborid.smart.presenter;

import com.kborid.library.base.RxPresenter;
import com.kborid.smart.entity.VideoChannelBean;
import com.kborid.smart.network.ChannelLoader;
import com.kborid.smart.presenter.contract.VideoTabContract;
import com.thunisoft.common.network.callback.ResponseCallback;
import com.thunisoft.common.util.ToastUtils;

import java.util.List;

import javax.inject.Inject;

public class VideoTabPresenter extends RxPresenter<VideoTabContract.View> implements VideoTabContract.Presenter {

    @Inject
    public VideoTabPresenter() {
    }

    @Override
    public void loadVideoChannel() {
        ChannelLoader.loadVideoChannel(new ResponseCallback<List<VideoChannelBean>>() {
            @Override
            public void failure(Throwable throwable) {
                ToastUtils.showToast(throwable.getMessage());
            }

            @Override
            public void success(List<VideoChannelBean> videoChannelBeans) {
                if (null != mView) {
                    mView.updateVideoChannel(videoChannelBeans);
                }
            }
        });
    }
}
