package com.kborid.library.base;

import android.app.Application;
import android.content.Context;

import com.kborid.library.util.LogUtils;
import com.thunisoft.common.ThunisoftCommon;
import com.thunisoft.common.tool.CrashHandler;
import com.thunisoft.ui.ThunisoftUI;

public class BaseApplication extends Application {

    private static final String TAG = BaseApplication.class.getSimpleName();

    private static BaseApplication instance;

    public static BaseApplication getInstance() {
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        instance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(this);
        LogUtils.init();
        LogUtils.d(TAG, "onCreate()");
        ThunisoftCommon.init(this);
        ThunisoftUI.init(this);
//        PackageManagerImpl.init(this);
    }
}
