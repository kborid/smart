package com.kborid.smart.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.kborid.smart.constant.AppConstant;
import com.kborid.smart.ui.login.LoginActivity;

/**
 * UnLoginBroadcastReceiver
 *
 * @description: 登录跳转广播
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 * @version: 1.0.0
 * @date: 2020/12/2
 */
public class UnLoginBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (!AppConstant.UNLOGIN_ACTION.equals(action)) {
            return;
        }

        intentLogin(context);
    }

    /**
     * 跳转登录页面
     */
    private void intentLogin(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
