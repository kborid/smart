package com.kborid.setting.ui;//package com.kborid.setting.ui

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.kborid.setting.R;
import com.thunisoft.common.base.BaseActivity;

public class MainActivity extends BaseActivity {

    private LifecycleObserver lifecycleObserver = new LifecycleObserver() {
        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        void resume() {
            System.out.println("resume");
            getLayoutInflater().inflate(R.layout.activity_main, null);
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        void pause() {
            System.out.println("pause");
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        void destroy() {
            System.out.println("destroy");
        }
    };

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initDataAndEvent(@Nullable Bundle bundle) {
        getLifecycle().addObserver(lifecycleObserver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getLifecycle().removeObserver(lifecycleObserver);
    }
}