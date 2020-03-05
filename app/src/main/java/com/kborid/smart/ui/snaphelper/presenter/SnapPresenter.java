package com.kborid.smart.ui.snaphelper.presenter;

import com.kborid.library.base.RxPresenter;
import com.kborid.smart.entity.PhotoGirl;
import com.kborid.smart.network.ApiManager;
import com.kborid.smart.ui.snaphelper.presenter.contract.SnapContract;
import com.thunisoft.common.network.callback.ResponseCallback;
import com.thunisoft.common.util.ToastUtils;

import java.util.List;

import javax.inject.Inject;

public class SnapPresenter extends RxPresenter<SnapContract.View> implements SnapContract.Presenter {

    @Inject
    public SnapPresenter(String name) {
        System.out.println(name);
    }

    @Override
    public void request() {
        ApiManager.getPhotoList(20, 2, new ResponseCallback<List<PhotoGirl>>() {
            @Override
            public void failure(Throwable throwable) {
                ToastUtils.showToast(throwable.getMessage());
            }

            @Override
            public void success(List<PhotoGirl> girls) {
                if (null != mView) {
                    mView.updateData(girls);
                }
            }
        });
    }
}
