package com.kborid.smart.ui.test.presenter;

import com.kborid.library.base.RxPresenter;
import com.kborid.smart.ui.test.presenter.contract.TestContract;

import javax.inject.Inject;

public class TestPresenter extends RxPresenter<TestContract.View> implements TestContract.Presenter {

    private String name;

    @Inject
    public TestPresenter(String name) {
        this.name = name;
    }

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

    @Override
    public String getString() {
        return name + " by " + TestPresenter.class.getSimpleName();
    }
}
