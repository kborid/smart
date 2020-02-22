package com.kborid.smart.ui.mainTab.news.newslist.presenter.contract;

import com.kborid.library.base.BasePresenter;
import com.kborid.library.base.BaseView;

public class NewsContract {

    public interface View extends BaseView {

    }

    public interface Presenter extends BasePresenter<View> {
        void getNewsList(String type, String id, int start);
    }
}