package com.kborid.smart.ui.mainTab.comm;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.kborid.library.base.BaseFragment;
import com.kborid.smart.R;
import com.kborid.smart.di.DaggerCommonComponent;
import com.kborid.smart.ui.mainTab.comm.presenter.NewsPresenter;
import com.kborid.smart.ui.mainTab.comm.presenter.contract.NewsContract;

import butterknife.BindView;

public class NewsFragment extends BaseFragment<NewsPresenter> implements NewsContract.View {

    @BindView(R.id.recycleView)
    RecyclerView recyclerView;

    @Override
    protected void initInject() {
        DaggerCommonComponent.builder()
                .commonModule(getCommonModule("news"))
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_news;
    }

    public static Fragment newInstance(String type) {
        Fragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        String title = getArguments().getString("type");
    }
}
