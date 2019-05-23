package com.kborid.library.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.kborid.library.di.module.CommonModule;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView {
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
