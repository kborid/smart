package com.kborid.smart.ui.presenter.contract;

import com.kborid.library.base.BasePresenter;
import com.kborid.library.base.BaseView;
import com.kborid.smart.entity.VideoData;

import java.util.List;

public class VideoContract {

    public interface View extends BaseView {
        void refreshVideoList(List<VideoData> videoDatas);
    }

    public interface Presenter extends BasePresenter<View> {
        void getVideoList(String type, int start);
    }
}