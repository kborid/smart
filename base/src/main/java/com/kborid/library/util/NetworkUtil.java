package com.kborid.library.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.kborid.library.base.BaseApplication;

/**
 * @description: 网络状态判断类
 * @date: 2019/7/15
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 */
public class NetworkUtil {

    private static String LOG_TAG = "NetworkUtil";

    /**
     * 网络是否可用
     *
     * @return
     */
    @SuppressLint("MissingPermission")
    public static boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) BaseApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = cm.getActiveNetworkInfo();
        if (network != null) {
            return network.isAvailable();
        }
        return false;
    }

    /**
     * 判断当前网络是否是wifi网络
     */
    @SuppressLint("MissingPermission")
    public static boolean isWifi() {
        ConnectivityManager connectivityManager = (ConnectivityManager) BaseApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    /**
     * 判断当前网络是否是3G网络
     */
    @SuppressLint("MissingPermission")
    public static boolean is3G() {
        ConnectivityManager connectivityManager = (ConnectivityManager) BaseApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            return true;
        }
        return false;
    }

    /**
     * 判断网络是否为漫游
     */
    @SuppressLint("MissingPermission")
    public static boolean isNetworkRoaming() {
        ConnectivityManager connectivity = (ConnectivityManager) BaseApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            Log.w(LOG_TAG, "couldn't get connectivity manager");
        } else {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.getType() == ConnectivityManager.TYPE_MOBILE) {
                TelephonyManager tm = (TelephonyManager) BaseApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
                if (tm != null && tm.isNetworkRoaming()) {
                    Log.d(LOG_TAG, "network is roaming");
                    return true;
                } else {
                    Log.d(LOG_TAG, "network is not roaming");
                }
            } else {
                Log.d(LOG_TAG, "not using mobile network");
            }
        }
        return false;
    }

    /**
     * 判断MOBILE网络是否可用
     *
     * @return
     */
    @SuppressLint("MissingPermission")
    public static boolean isMobileDataEnable() throws Exception {
        ConnectivityManager connectivityManager = (ConnectivityManager) BaseApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
    }

}
