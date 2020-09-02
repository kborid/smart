package com.kborid.smart.ui.presenter.contract;

import com.kborid.library.base.BasePresenter;
import com.kborid.library.base.BaseView;
import com.kborid.smart.entity.NewsSummary;

import java.util.List;

public class NewsContract {

    public interface View extends BaseView {
        void refreshNewsList(List<NewsSummary> newsSummaryList, boolean refresh);
    }

    public interface Presenter extends BasePresenter<View> {
        void getNewsList(String type, String id, int start, boolean refresh);
    }
}