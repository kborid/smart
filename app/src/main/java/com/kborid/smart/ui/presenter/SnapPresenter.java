package com.kborid.smart.ui.presenter;

import com.kborid.library.base.RxPresenter;
import com.kborid.smart.network.ApiManager;
import com.kborid.smart.ui.presenter.contract.SnapContract;
import com.thunisoft.common.network.util.RxUtil;

import javax.inject.Inject;

public class SnapPresenter extends RxPresenter<SnapContract.View> implements SnapContract.Presenter {

    @Inject
    public SnapPresenter() {
    }

    @Override
    public void request() {
        ApiManager.getPhotoList(50, 2)
                .subscribe(RxUtil.createDefaultSubscriber(photoGirls -> mView.updateData(photoGirls)));
    }
}
