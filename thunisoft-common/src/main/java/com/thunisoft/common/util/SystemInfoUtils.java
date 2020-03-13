package com.thunisoft.common.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

/**
 * @description: 系统信息工具类
 * @date: 2019/8/1
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 * @version: 1.0.0
 */
public class SystemInfoUtils {

    /**
     * 获取设备IMEI号
     *
     * @param context
     * @return
     */
    @SuppressLint({"MissingPermission", "HardwareIds"})
    public static String getDeviceId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return tm.getImei();
        } else {
            return tm.getDeviceId();
        }
    }
}
