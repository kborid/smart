package com.kborid.smart.ui.presenter;

import com.kborid.library.base.RxPresenter;
import com.kborid.smart.entity.PhotoGirl;
import com.kborid.smart.network.ApiManager;
import com.kborid.smart.ui.presenter.contract.PhotoTabContract;
import com.thunisoft.common.network.callback.ResponseCallback;
import com.thunisoft.common.util.ToastUtils;

import java.util.List;

import javax.inject.Inject;

public class PhotoTabPresenter extends RxPresenter<PhotoTabContract.View> implements PhotoTabContract.Presenter {

    @Inject
    public PhotoTabPresenter() {
    }

    @Override
    public void getPhotoList(int size, int page) {
        ApiManager.getPhotoList(size, page, new ResponseCallback<List<PhotoGirl>>() {
            @Override
            public void failure(Throwable throwable) {
                ToastUtils.showToast(throwable.getMessage());
            }

            @Override
            public void success(List<PhotoGirl> girls) {
                if (null != mView) {
                    mView.refreshPhotoList(girls);
                }
            }
        });
    }
}
