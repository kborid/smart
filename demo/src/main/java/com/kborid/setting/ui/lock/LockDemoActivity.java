package com.kborid.setting.ui.lock;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.LayoutInflaterCompat;
import com.kborid.setting.R;
import com.thunisoft.common.base.BaseSimpleActivity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LockDemoActivity extends BaseSimpleActivity {

    private static final Logger logger = LoggerFactory.getLogger(LockDemoActivity.class.getSimpleName());

    private static final int SHOW_TOP_OF_SCREEN_FLAGS = WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;

    private Context mRemoteContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory2(LayoutInflater.from(this), new LayoutInflater.Factory2() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                logger.info("【Name = {}】", name);
                int n = attrs.getAttributeCount();
                for (int i = 0; i < n; i++) {
                    logger.info("【Attr = {}】:{}", attrs.getAttributeName(i), attrs.getAttributeValue(i));
                }
                if ("ImageView".equals(name)) {
                    AppCompatDelegate delegate = getDelegate();
                    ImageView view = (ImageView) delegate.createView(parent, name, context, attrs);
                    view.setImageResource(R.mipmap.logo_small);
                    return view;
                }
                return null;
            }

            @Override
            public View onCreateView(String name, Context context, AttributeSet attrs) {
                return null;
            }
        });

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
        try {
            mRemoteContext = createPackageContext("com.kborid.app", CONTEXT_IGNORE_SECURITY);
            ImageView iv_icon = (ImageView) view;
            Resources remoteRes = mRemoteContext.getResources();
            iv_icon.setImageDrawable(remoteRes.getDrawable(remoteRes.getIdentifier("scan_light", "drawable", "com.kborid.app")));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }
}
