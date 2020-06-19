package com.kborid.setting.ui;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.kborid.setting.R;
import com.thunisoft.common.base.BaseActivity;
import com.thunisoft.ui.util.ScreenUtils;

public class FragmentActivity extends BaseActivity {

    private float offset = 0;
    private int y = 0;
    private View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        boolean isLocked = keyguardManager.inKeyguardRestrictedInputMode();
        System.out.println(isLocked);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_fragment;
    }

    @Override
    protected void initDataAndEvent(@Nullable Bundle bundle) {
        rootView = findViewById(R.id.root);
        findViewById(R.id.cancel_disp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private boolean isTouchIn(MotionEvent event) {
        System.out.println(rootView.getTop());
        System.out.println(rootView.getBottom());
        return event.getY() >= rootView.getTop() + y
                && event.getY() <= rootView.getBottom() + y;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int height = rootView.getMeasuredHeight();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!isTouchIn(event)) {
                    System.out.println("touch out of range");
                    return false;
                }
                // getRawX是触摸位置相对于屏幕的坐标，getX是相对于按钮的坐标
                offset = event.getRawY() - y - height / 2 - ScreenUtils.mStateBarHeight;
                break;
            case MotionEvent.ACTION_MOVE:
                if (!isTouchIn(event)) {
                    System.out.println("touch out of range");
                    return false;
                }
                y = (int) (event.getRawY() - height / 2 - ScreenUtils.mStateBarHeight - offset);
                if (y > 0 && y < ScreenUtils.mScreenHeight) {
                    rootView.setTranslationY(y);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                break;
        }
        return false; // 此处必须返回false，否则OnClickListener获取不到监听
    }
}
