package com.kborid.smart.ui.snaphelper.presenter.contract;

import com.kborid.library.base.BasePresenter;
import com.kborid.library.base.BaseView;
import com.kborid.smart.entity.PhotoGirl;

import java.util.List;

public class SnapContract {

    public interface View<T> extends BaseView {
        void updateData(List<T> data);
    }

    public interface Presenter extends BasePresenter<View> {
        void request();
    }
}
