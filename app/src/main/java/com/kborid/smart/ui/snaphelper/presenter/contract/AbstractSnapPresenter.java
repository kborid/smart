package com.kborid.smart.ui.snaphelper.presenter.contract;

import com.kborid.library.base.RxPresenter;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSnapPresenter extends RxPresenter<SnapContract.View> implements SnapContract.Presenter {

    protected List<String> mData = new ArrayList<>();

    @Override
    public void request() {
        mView.updateData(mData);
    }

    @Override
    public void remove(int index) {
        mView.updateData(mData);
    }
}
