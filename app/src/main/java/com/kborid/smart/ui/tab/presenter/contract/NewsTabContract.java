package com.kborid.smart.ui.tab.presenter.contract;

import com.kborid.library.base.BasePresenter;
import com.kborid.library.base.BaseView;
import com.kborid.smart.entity.NewsChannelBean;

import java.util.List;

public class NewsTabContract {
    public interface View extends BaseView {
        void updateMainChannel(List<NewsChannelBean> data);
    }

    public interface Presenter extends BasePresenter<View> {
        void loadMainChannel();
    }
}
