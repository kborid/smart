package com.kborid.smart.ui.snaphelper.presenter;

import com.kborid.smart.ui.snaphelper.model.FirstModel;
import com.kborid.smart.ui.snaphelper.view.IFirstView;

public class FirstPresenter {

    private static final int SIZE = 20;

    private IFirstView mFirstView;
    private FirstModel mFirstModel;

    public FirstPresenter(IFirstView firstView) {
        this.mFirstView = firstView;
        this.mFirstModel = new FirstModel();
    }

    public void request() {
        request(SIZE);
    }

    public void request(int size) {
        mFirstModel.requestData(size);
        mFirstView.updateData(mFirstModel.getData());
    }

    public void remove(int index) {
        mFirstModel.removeData(index);
        mFirstView.updateData(mFirstModel.getData());
    }
}
