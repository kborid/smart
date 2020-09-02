package com.kborid.smart.ui.presenter;

import com.kborid.library.base.RxPresenter;
import com.kborid.smart.entity.PhotoGirl;
import com.kborid.smart.network.ApiManager;
import com.kborid.smart.ui.presenter.contract.PhotoTabContract;
import com.thunisoft.common.network.util.RxUtil;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class PhotoTabPresenter extends RxPresenter<PhotoTabContract.View> implements PhotoTabContract.Presenter {

    @Inject
    public PhotoTabPresenter() {
    }

    @Override
    public void getPhotoList(int size, int page) {
        System.out.println("getPhotoList();");
        ApiManager.getPhotoList(size, page)
                .subscribe(RxUtil.createDefaultSubscriber(new Consumer<List<PhotoGirl>>() {
                    @Override
                    public void accept(List<PhotoGirl> photoGirls) throws Exception {
                        System.out.println("photo reset");
                        mView.refreshPhotoList(photoGirls);
                    }
                }));
    }
}
