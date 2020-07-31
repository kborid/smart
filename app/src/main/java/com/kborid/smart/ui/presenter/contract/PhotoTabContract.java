package com.kborid.smart.ui.presenter.contract;

import com.kborid.library.base.BasePresenter;
import com.kborid.library.base.BaseView;
import com.kborid.smart.entity.PhotoGirl;

import java.util.List;

public class PhotoTabContract {
    public interface View extends BaseView {
        void refreshPhotoList(List<PhotoGirl> girls);
    }

    public interface Presenter extends BasePresenter<View> {
        void getPhotoList(int size, int page);
    }
}
