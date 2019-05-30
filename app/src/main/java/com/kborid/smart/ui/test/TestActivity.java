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
import com.kborid.smart.di.DaggerCommonComponent;
import com.kborid.smart.event.TestEvent;
import com.kborid.smart.ui.test.presenter.TestPresenter;
import com.kborid.smart.ui.test.presenter.contract.TestContract;
import com.kborid.smart.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class TestActivity extends BaseActivity<TestPresenter> implements TestContract.View {

    @BindView(R.id.pb)
    ProgressBar progressBar;
    @BindView(R.id.test)
    TextView test;

    @Override
    protected void initInject() {
        DaggerCommonComponent.builder()
                .commonModule(getCommonModule("test"))
                .build()
                .inject(this);
    }

    @OnClick(R.id.test)
    public void testOnClick() {
        LogUtils.d("==>thread", "post:" + Thread.currentThread().getName());
        EventBusss.getDefault().post(new TestEvent("one", "two"));
        startLoad();
        UIHandler.post(new Runnable() {
            @Override
            public void run() {
                mPresenter.loadData();
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_test;
    }

    @Override
    protected void initEventAndData(@Nullable Bundle savedInstanceState) {
        test.setText("点击加载");
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void startLoad() {
        if (!progressBar.isShown()) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void endLoad() {
        progressBar.setVisibility(View.INVISIBLE);
        test.setText("加载完成");
        ToastUtils.showToast(mPresenter.getString());
    }
}
