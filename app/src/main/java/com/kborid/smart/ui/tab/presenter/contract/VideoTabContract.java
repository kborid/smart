package com.kborid.smart.ui.tab.presenter.contract;

import com.kborid.library.base.BasePresenter;
import com.kborid.library.base.BaseView;
import com.kborid.smart.entity.NewsChannelBean;
import com.kborid.smart.entity.VideoChannelBean;

import java.util.List;

public class VideoTabContract {
    public interface View extends BaseView {
        void updateVideoChannel(List<VideoChannelBean> data);
    }

    public interface Presenter extends BasePresenter<View> {
        void loadVideoChannel();
    }
}
