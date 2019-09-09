package com.kborid.library.base;

import android.app.Application;
import android.content.Context;
import android.support.annotation.Nullable;

import com.kborid.library.BuildConfig;
import com.kborid.library.crash.CrashHandler;
import com.kborid.library.pm.PackageManagerImpl;
import com.kborid.library.util.LogUtils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

public class BaseApplication extends Application {

    private static final String TAG = BaseApplication.class.getSimpleName();

    private static BaseApplication instance;
    // 应用是否在前台显示
    private static boolean isForeground = false;

    public static BaseApplication getInstance() {
        return instance;
    }

    public static boolean isForeground() {
        return isForeground;
    }

    public static void setForeground(boolean isFore) {
        isForeground = isFore;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        instance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.init();
        LogUtils.d(TAG, "onCreate()");
        initLogger();
//        PackageManagerImpl.init(this);
        CrashHandler.getInstance().init(this);
    }

    private void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)   // (Optional) Whether to show thread info or not. Default true
                .methodCount(2)         // (Optional) How many method line to show. Default 2
                .methodOffset(0)        // (Optional) Hides internal method calls up to offset. Default 5
                .tag("Smart")           // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return BuildConfig.DEBUG;
            }
        });
        Logger.addLogAdapter(new DiskLogAdapter());
    }
}
