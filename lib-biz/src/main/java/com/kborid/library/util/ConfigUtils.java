package com.kborid.library.util;

import android.content.res.Resources;

import com.kborid.library.R;
import com.kborid.library.base.BaseApplication;

public class ConfigUtils {

    private static Resources getResource() {
        return BaseApplication.getInstance().getResources();
    }

    public static int getConfigX() {
        return getResource().getInteger(R.integer.x);
    }

    public static int getConfigY() {
        return getResource().getInteger(R.integer.y);
    }

    public static String getConfigName() {
        return getResource().getString(R.string.z);
    }
}
