package com.kborid.library.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class SimpleActivity extends AppCompatActivity {

    protected Activity mContext;
    private Unbinder mUnBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        mUnBinder = ButterKnife.bind(this);
        mContext = this;
        initEventAndData(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnBinder.unbind();
    }

    protected abstract int getLayoutResId();

    protected abstract void initEventAndData(Bundle savedInstanceState);
}
