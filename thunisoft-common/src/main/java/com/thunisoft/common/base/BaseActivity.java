package com.thunisoft.common.base;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        initDataAndEvent(savedInstanceState);
    }

    protected abstract int getLayoutResId();

    protected abstract void initDataAndEvent(@Nullable Bundle savedInstanceState);
}
