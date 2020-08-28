package com.kborid.setting.ui.lock;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.kborid.setting.R;
import com.kborid.setting.ui.TestActivity;
import com.thunisoft.common.base.BaseSimpleActivity;
import com.thunisoft.common.network.api.SampleApiManager;
import com.thunisoft.common.network.util.RxUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.reactivex.functions.Consumer;

public class LockDemoActivity extends BaseSimpleActivity {

    private static final Logger logger = LoggerFactory.getLogger(LockDemoActivity.class.getSimpleName());

    private static final int SHOW_TOP_OF_SCREEN_FLAGS = WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        overridePendingTransition(0, 0);
        super.onCreate(savedInstanceState);
        logger.info("onCreate()");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | SHOW_TOP_OF_SCREEN_FLAGS
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        boolean isLocked = null != keyguardManager && keyguardManager.isKeyguardLocked();
        logger.debug("锁屏状态：{}", isLocked);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        logger.info("onNewIntent()");
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        if ((params.flags & SHOW_TOP_OF_SCREEN_FLAGS) == 0) {
            getWindow().addFlags(SHOW_TOP_OF_SCREEN_FLAGS);
            window.setAttributes(params);
        }

        setIntent(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.act_locked;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
    }

    public void onClick(View view) {
        SampleApiManager.testGet("1")
                .subscribe(RxUtil.createDefaultSubscriber(new Consumer<Object>() {
                    @Override
                    public void accept(Object t) throws Exception {
                    }
                }));
        startActivity(new Intent(this, TestActivity.class));
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }
}
