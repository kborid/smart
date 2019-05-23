package com.kborid.smart.ui.test.presenter.contract;

import com.kborid.library.base.BasePresenter;
import com.kborid.library.base.BaseView;

public class TestContract {
    public interface View extends BaseView {
        void endLoad();
    }

    public interface Presenter extends BasePresenter<View> {
        void loadData();
        String getString();
    }
}
