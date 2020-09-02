package com.kborid.smart.ui.presenter;

import com.kborid.library.base.RxPresenter;
import com.kborid.smart.entity.PhotoGirl;
import com.kborid.smart.network.ApiManager;
import com.kborid.smart.ui.presenter.contract.SnapContract;
import com.thunisoft.common.network.util.RxUtil;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class SnapPresenter extends RxPresenter<SnapContract.View> implements SnapContract.Presenter {

    @Inject
    public SnapPresenter() {
    }

    @Override
    public void request() {
        ApiManager.getPhotoList(20, 2)
                .subscribe(RxUtil.createDefaultSubscriber(new Consumer<List<PhotoGirl>>() {
                    @Override
                    public void accept(List<PhotoGirl> photoGirls) throws Exception {
                        mView.updateData(photoGirls);
                    }
                }));
    }
}
