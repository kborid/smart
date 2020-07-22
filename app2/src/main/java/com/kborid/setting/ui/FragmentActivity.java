package com.kborid.setting.ui;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleObserver;

import com.kborid.demo.SimpleLifecycleObserver;
import com.kborid.setting.R;
import com.thunisoft.common.base.BaseSimpleActivity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FragmentActivity extends BaseSimpleActivity {

    private final static Logger logger = LoggerFactory.getLogger(FragmentActivity.class);

    private LifecycleObserver lifecycleObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        boolean isLocked = keyguardManager.inKeyguardRestrictedInputMode();
        logger.info("锁屏状态：{}", isLocked);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_fragment;
    }

    @Override
    protected void initDataAndEvent(@Nullable Bundle bundle) {
        lifecycleObserver = new SimpleLifecycleObserver(this.getClass().getSimpleName());
        getLifecycle().addObserver(lifecycleObserver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getLifecycle().removeObserver(lifecycleObserver);
    }
}
