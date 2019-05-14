package com.kborid.library.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
    }

    /**
     * 设置布局资源id
     *
     * @return
     */
    protected abstract int getLayoutResId();

    protected void initParams() {
        dealIntent();
    }

    protected void dealIntent() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        BaseApplication.setForeground(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        BaseApplication.setForeground(false);
    }
}
