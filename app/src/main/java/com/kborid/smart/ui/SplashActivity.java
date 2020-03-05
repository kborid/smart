package com.kborid.smart.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.kborid.smart.R;
import com.kborid.smart.activity.MainActivity;
import com.thunisoft.common.base.BaseSimpleActivity;
import com.thunisoft.common.tool.UIHandler;

import butterknife.OnClick;

public class SplashActivity extends BaseSimpleActivity {

    private static final long DELAY_TIME = 2000;

    private boolean isGotoTest = false;

    @Override
    protected int getLayoutResId() {
        return R.layout.act_splash;
    }

    @Override
    protected void initEventAndData(Bundle bundle) {
        UIHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isGotoTest) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, MainFragmentActivity.class));
                }
                finish();
            }
        }, DELAY_TIME);
    }

    @OnClick(R.id.test)
    void onClickBtn(View view) {
        isGotoTest = true;
    }
}
