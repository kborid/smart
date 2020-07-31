package com.kborid.smart.util;

import com.kborid.smart.PRJApplication;

public class StringResUtil {
    public static String getString(int strId) {
        if (strId <= -1) {
            return "";
        }
        return PRJApplication.getInstance().getResources().getString(strId);
    }

    public static String[] getStringArray(int strId) {
        if (strId <= -1) {
            return null;
        }
        return PRJApplication.getInstance().getResources().getStringArray(strId);
    }
}
