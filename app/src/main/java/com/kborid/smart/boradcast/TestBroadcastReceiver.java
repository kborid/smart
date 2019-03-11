package com.kborid.smart.boradcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.orhanobut.logger.Logger;

public class TestBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = TestBroadcastReceiver.class.getSimpleName();
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Logger.t(TAG).d("onReceiver() action = " + action);
    }
}
