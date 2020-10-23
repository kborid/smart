package com.kborid.smart.ui.presenter;

import com.kborid.library.base.RxPresenter;
import com.kborid.smart.network.ChannelLoader;
import com.kborid.smart.ui.presenter.contract.VideoTabContract;
import com.thunisoft.common.network.util.RxUtil;

import javax.inject.Inject;

public class VideoTabPresenter extends RxPresenter<VideoTabContract.View> implements VideoTabContract.Presenter {

    @Inject
    public VideoTabPresenter() {
    }

    @Override
    public void loadVideoChannel() {
        ChannelLoader.loadVideoChannel()
                .subscribe(RxUtil.createDefaultSubscriber(videoChannelBeans -> mView.updateVideoChannel(videoChannelBeans)));
    }
}
