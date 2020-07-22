package com.kborid.smart.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.LayoutInflaterCompat;

import com.kborid.smart.PRJApplication;
import com.kborid.smart.R;
import com.kborid.smart.activity.MainActivity;
import com.kborid.smart.service.LocationService;
import com.thunisoft.common.base.BaseSimpleActivity;
import com.thunisoft.common.tool.UIHandler;

import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class SplashActivity extends BaseSimpleActivity {

    private static final long DELAY_TIME = 0;

    private boolean isGotoTest = false;

    private static long sInflateViewTime = 0L;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory2(LayoutInflater.from(this), new LayoutInflater.Factory2() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                long startTime = System.currentTimeMillis();
                View v = getDelegate().createView(parent, name, context, attrs);
                long spend = System.currentTimeMillis() - startTime;
                sInflateViewTime += spend;
                System.out.println("创建" + name + "\n花费时间：" + spend + "ms," + "total:" + sInflateViewTime + "ms");
                return v;
            }

            @Override
            public View onCreateView(String name, Context context, AttributeSet attrs) {
                return null;
            }
        });
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(null);

        new Thread("leak-thread") {
            @Override
            public void run() {
                super.run();
                while (true) {
                    // TODO
                }
            }
        }.start();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.act_splash;
    }

    @Override
    protected void initDataAndEvent(Bundle bundle) {
        SplashActivityPermissionsDispatcher.dynamicObtainPermissionWithPermissionCheck(this);
    }

    @OnClick(R.id.test)
    void onClickBtn(View view) {
        isGotoTest = true;
    }

    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION})
    void dynamicObtainPermission() {
        LocationService.startLocationService(PRJApplication.getInstance());
        UIHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                go();
                finish();
            }
        }, DELAY_TIME);
    }

    @OnPermissionDenied({Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION})
    void showDenied() {
        finish();
    }

    @SuppressLint("NeedOnRequestPermissionsResult")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        SplashActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    private void go() {
        if (isGotoTest) {
            startActivity(new Intent(this, MainActivity.class));
        } else {
            startActivity(new Intent(this, MainFragmentActivity.class));
        }
    }
}
