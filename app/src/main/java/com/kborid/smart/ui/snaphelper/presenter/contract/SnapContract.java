package com.kborid.smart.ui.snaphelper.presenter.contract;

import com.kborid.library.base.BasePresenter;
import com.kborid.library.base.BaseView;

import java.util.List;

public class SnapContract {

    public interface View extends BaseView {
        void updateData(List<String> data);
    }

    public interface Presenter extends BasePresenter<View> {
        void request();

        void remove(int index);
    }
}
