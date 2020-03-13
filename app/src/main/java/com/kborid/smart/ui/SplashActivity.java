package com.kborid.smart.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
        SplashActivityPermissionsDispatcher.dynamicObtainPermissionWithPermissionCheck(this);
    }

    @OnClick(R.id.test)
    void onClickBtn(View view) {
        isGotoTest = true;
    }

    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_FINE_LOCATION})
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

    @OnPermissionDenied({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE})
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
