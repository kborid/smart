package com.kborid.library.base;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;

import com.kborid.library.di.module.CommonModule;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public abstract class BaseActivity<T extends BasePresenter> extends SwipeBackActivity implements BaseView {
    @Inject
    protected T mPresenter;
    protected Activity mContext;
    private Unbinder mUnBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        mUnBinder = ButterKnife.bind(this);
        mContext = this;
        initInject();
        if (null != mPresenter) {
            mPresenter.attachView(this);
        }
        initEventAndData(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mPresenter) {
            mPresenter.detachView();
        }
        mUnBinder.unbind();
    }

    protected CommonModule getCommonModule(String name) {
        return new CommonModule(name);
    }

    protected abstract void initInject();

    protected abstract int getLayoutResId();

    protected abstract void initEventAndData(@Nullable Bundle savedInstanceState);
}
