package com.kborid.smart.ui.presenter.contract;

import com.kborid.library.base.BasePresenter;
import com.kborid.library.base.BaseView;
import com.kborid.smart.entity.NewsDetail;

public class NewsDetailContract {
    public interface View extends BaseView {
        void refreshInfo(NewsDetail newsDetail);
    }

    public interface Presenter extends BasePresenter<View> {
        void getNewsDetail(String postId);
    }
}
