package com.thunisoft.common;

import android.app.Application;
import android.content.Context;

import com.thunisoft.common.tool.CrashHandler;
import com.thunisoft.common.util.SystemParamUtil;

public class ThunisoftCommon {

    private static Context mContext;

    /**
     * library初始化方法，invoke in application onCreate
     *
     * @param context
     */
    public static void init(Context context) {
        if (!(context instanceof Application)) {
            throw new IllegalArgumentException("must be use application context init it");
        }
        mContext = context;

        SystemParamUtil.init(context);
        CrashHandler.getInstance().init();
    }

    public static Context getContext() {
        if (null == mContext) {
            throw new NullPointerException("must be invoked init() in your application before use it!");
        }
        return mContext;
    }
}
