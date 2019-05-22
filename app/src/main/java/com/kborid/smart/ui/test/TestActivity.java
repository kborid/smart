package com.kborid.smart.ui.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kborid.library.base.BaseActivity;
import com.kborid.library.common.UIHandler;
import com.kborid.smart.R;
import com.kborid.smart.ui.test.presenter.TestPresenter;
import com.kborid.smart.ui.test.presenter.contract.TestContract;

import butterknife.BindView;

public class TestActivity extends BaseActivity<TestPresenter> implements TestContract.View {

    @BindView(R.id.pb)
    ProgressBar progressBar;
    @BindView(R.id.test)
    TextView test;

    @Override
    protected void initInject() {
        mPresenter = new TestPresenter();
        test.setText("空");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_test;
    }

    @Override
    protected void initEventAndData(@Nullable Bundle savedInstanceState) {
        UIHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.loadData();
            }
        }, 500);
    }

    @Override
    public void endLoad() {
        progressBar.setVisibility(View.GONE);
        test.setText("OK");
    }
}
