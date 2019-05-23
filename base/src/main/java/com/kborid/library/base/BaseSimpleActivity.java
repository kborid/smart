package com.kborid.library.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseSimpleActivity extends AppCompatActivity {

    private Unbinder mUnBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        mUnBinder = ButterKnife.bind(this);
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
