package com.kborid.smart.ui.mainTab;

import android.os.Bundle;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.kborid.library.base.BaseFragment;
import com.kborid.smart.R;
import com.kborid.smart.di.DaggerCommonComponent;
import com.kborid.smart.ui.mainTab.presenter.MainTabPresenter;
import com.kborid.smart.ui.mainTab.presenter.contract.MainTabContract;

import butterknife.BindView;

public class MainTabFragment extends BaseFragment<MainTabPresenter> implements MainTabContract.View {

    @BindView(R.id.tv_title)
    TextView titleTV;

    @Override
    protected void initInject() {
        DaggerCommonComponent.builder()
                .commonModule(getCommonModule("mainTab"))
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_main_tab;
    }

    public static Fragment newInstance(String type) {
        Fragment fragment = new MainTabFragment();
        Bundle args = new Bundle();
        args.putString("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        String title = getArguments().getString("type");
        titleTV.setText(title);
    }
}
