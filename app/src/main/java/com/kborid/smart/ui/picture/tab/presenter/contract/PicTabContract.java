package com.kborid.smart.ui.picture.tab.presenter.contract;

import com.kborid.library.base.BasePresenter;
import com.kborid.library.base.BaseView;
import com.kborid.smart.entity.PhotoGirl;

import java.util.List;

public class PicTabContract {
    public interface View extends BaseView {
        void refreshPhotoList(List<PhotoGirl> girls);
    }

    public interface Presenter extends BasePresenter<View> {
        void getPhotoList(int size, int page);
    }
}
