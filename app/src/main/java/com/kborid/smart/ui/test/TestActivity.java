package com.kborid.smart.ui.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kborid.library.base.BaseActivity;
import com.kborid.library.common.UIHandler;
import com.kborid.library.hand2eventbus.EventBusss;
import com.kborid.library.util.LogUtils;
import com.kborid.smart.R;
import com.kborid.smart.event.TestEvent;
import com.kborid.smart.ui.test.presenter.TestPresenter;
import com.kborid.smart.ui.test.presenter.contract.TestContract;

import butterknife.BindView;
import butterknife.OnClick;

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

    @OnClick(R.id.test)
    public void testOnClick() {
        LogUtils.d("==>thread", "post:" + Thread.currentThread().getName());
        EventBusss.getDefault().post(new TestEvent("one", "two"));
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
