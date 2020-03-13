package com.kborid.smart.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.kborid.smart.R;
import com.kborid.smart.activity.MainActivity;
import com.thunisoft.common.base.BaseSimpleActivity;
import com.thunisoft.common.tool.UIHandler;

import butterknife.OnClick;

public class SplashActivity extends BaseSimpleActivity {

    private static final long DELAY_TIME = 0;

    private boolean isGotoTest = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(null);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.act_splash;
    }

    @Override
    protected void initEventAndData(Bundle bundle) {
        UIHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                go();
                finish();
            }
        }, DELAY_TIME);
    }

    @OnClick(R.id.test)
    void onClickBtn(View view) {
        isGotoTest = true;
    }

    private void go() {
        if (isGotoTest) {
            startActivity(new Intent(this, MainActivity.class));
        } else {
            startActivity(new Intent(this, MainFragmentActivity.class));
        }
    }
}
