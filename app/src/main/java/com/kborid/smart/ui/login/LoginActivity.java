package com.kborid.smart.ui.login;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.kborid.smart.R;
import com.kborid.smart.constant.GlobalThirdManager;
import com.kborid.smart.event.UpdateUserInfoEvent;
import com.kborid.smart.util.ToastDrawableUtil;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.DefaultUiListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.thunisoft.common.base.BaseSimpleActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.OnClick;

public class LoginActivity extends BaseSimpleActivity {

    IUiListener mQQLoginListener = null;

    @Override
    protected int getLayoutResId() {
        return R.layout.act_login;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        mQQLoginListener = new DefaultUiListener() {
            @Override
            public void onComplete(Object o) {
                super.onComplete(o);
                AsyncTask.THREAD_POOL_EXECUTOR.execute(() -> GlobalThirdManager.getUserInfo(new DefaultUiListener() {
                    @Override
                    public void onComplete(Object o) {
                        super.onComplete(o);
                        EventBus.getDefault().post(new UpdateUserInfoEvent(GlobalThirdManager.getUser()));
                        finish();
                    }
                }));
            }
        };
    }

    @OnClick(R.id.iv_qq)
    public void qqLogin() {
        GlobalThirdManager.login(this, mQQLoginListener);
    }

    @OnClick(R.id.iv_wx)
    public void wxLogin() {
        ToastDrawableUtil.showImgToast("敬请期待");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Constants.REQUEST_LOGIN) {
            Tencent.onActivityResultData(requestCode, resultCode, data, mQQLoginListener);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}