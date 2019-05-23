package com.kborid.smart.ui.test.presenter;

import com.kborid.library.base.RxPresenter;
import com.kborid.smart.ui.test.presenter.contract.TestContract;

public class TestPresenter extends RxPresenter<TestContract.View> implements TestContract.Presenter {

    @Override
    public void loadData() {
        for (int i = 0; i < 5; i++) {
            System.out.println("loadData...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        mView.endLoad();
    }
}
