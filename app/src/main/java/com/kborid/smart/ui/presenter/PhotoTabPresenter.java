package com.kborid.smart.ui.presenter;

import com.kborid.library.base.RxPresenter;
import com.kborid.smart.network.ApiManager;
import com.kborid.smart.ui.presenter.contract.PhotoTabContract;
import com.thunisoft.common.network.util.RxUtil;

import javax.inject.Inject;

public class PhotoTabPresenter extends RxPresenter<PhotoTabContract.View> implements PhotoTabContract.Presenter {

    @Inject
    public PhotoTabPresenter() {
    }

    @Override
    public void getPhotoList(int size, int page) {
        ApiManager.getPhotoList(size, page)
                .subscribe(RxUtil.createDefaultSubscriber(photoGirls -> mView.refreshPhotoList(photoGirls)));
    }
}
