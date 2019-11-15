package com.kborid.smart.ui.snaphelper.presenter;

import com.kborid.smart.helper.ImageResourceHelper;
import com.kborid.smart.ui.snaphelper.presenter.contract.AbstractSnapPresenter;

import java.util.List;

import javax.inject.Inject;

public class SnapPresenter extends AbstractSnapPresenter {

    @Inject
    public SnapPresenter(String name) {
        System.out.println(name);
    }

    public List<String> getData() {
        return mData;
    }

    @Override
    public void request() {
        mData.addAll(ImageResourceHelper.getImages());
        super.request();
    }

    @Override
    public void remove(int index) {
        if (mData.size() >= index) {
            mData.remove(index);
        }
        super.remove(index);
    }
}
