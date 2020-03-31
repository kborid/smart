package com.kborid.smart.presenter;

import com.kborid.library.base.RxPresenter;
import com.kborid.smart.entity.VideoData;
import com.kborid.smart.network.ApiManager;
import com.kborid.smart.presenter.contract.VideoContract;
import com.thunisoft.common.network.callback.ResponseCallback;
import com.thunisoft.common.util.ToastUtils;

import java.util.List;

import javax.inject.Inject;

public class VideoPresenter extends RxPresenter<VideoContract.View> implements VideoContract.Presenter {
    @Inject
    public VideoPresenter() {
    }

    @Override
    public void getVideoList(String type, int start) {
        ApiManager.getVideoList(type, start, new ResponseCallback<List<VideoData>>() {
            @Override
            public void failure(Throwable throwable) {
                ToastUtils.showToast(throwable.getMessage());
            }

            @Override
            public void success(List<VideoData> videoDatas) {
                if (null != mView) {
                    mView.refreshVideoList(videoDatas);
                }
            }
        });
    }
}
