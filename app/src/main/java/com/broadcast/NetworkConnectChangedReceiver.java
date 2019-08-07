package com.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;

import com.kborid.library.util.NetworkUtil;
import com.kborid.smart.PRJApplication;
import com.kborid.smart.util.ToastUtils;
import com.orhanobut.logger.Logger;

public class NetworkConnectChangedReceiver extends BroadcastReceiver {

    private static final String TAG = NetworkConnectChangedReceiver.class.getSimpleName();

    private static final int MSG_NETWORK_CHANGED = 0x01;

    // 当前手机网络连接状态
    private static boolean isConnected = NetworkUtil.isNetworkAvailable();
    private InnerHandler innerHandler = new InnerHandler();

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Logger.t(TAG).i("onReceiver() action=%s", action);

        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Network[] networks = connMgr.getAllNetworks();

        boolean tmp = false;
        for (Network network : networks) {
            NetworkInfo networkInfo = connMgr.getNetworkInfo(network);
            System.out.println(networkInfo.toString());
            tmp = tmp || networkInfo.isConnected();
        }

        boolean hasMsg1 = innerHandler.hasMessages(MSG_NETWORK_CHANGED);
        if (hasMsg1) {
            Logger.t(TAG).i("onReceiver() hasMessages(MSG_NETWORK_CHANGED)=%s", hasMsg1);
            innerHandler.removeMessages(MSG_NETWORK_CHANGED);
        }
        Message msg = innerHandler.obtainMessage();
        msg.what = MSG_NETWORK_CHANGED;
        msg.getData().putBoolean("connected", tmp);
        innerHandler.sendMessageDelayed(msg, 500);
        Logger.t(TAG).i("onReceiver() sendMessageDelayed(MSG_NETWORK_CHANGED)");
    }

    private static class InnerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Logger.t(TAG).i("InnerHandler#handleMessage() msg=%d", msg.what);
            if (msg.what == MSG_NETWORK_CHANGED) {
                boolean tmp = msg.getData().getBoolean("connected");
                if (isConnected == tmp) {
                    Logger.t(TAG).i("innerHandler MSG_NETWORK_CHANGED isConnected=%s, no change, return~~~", isConnected);
                    return;
                }

                isConnected = tmp;
                Logger.t(TAG).i("innerHandler MSG_NETWORK_CHANGED isConnected=%s, changed~~~", isConnected);
                if (isConnected) {
                    if (PRJApplication.isForeground()) {
                        ToastUtils.showToast("网络已连接");
                    }
                } else {
                    if (PRJApplication.isForeground()) {
                        ToastUtils.showToast("网络已断开");
                    }
                }
            }
        }
    }
}
