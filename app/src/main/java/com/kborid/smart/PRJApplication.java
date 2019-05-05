package com.kborid.smart;

import android.app.Application;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDex;

import com.kborid.library.pm.PackageManagerImpl;
import com.kborid.library.util.LogUtils;
import com.kborid.smart.service.LocationService;
import com.kborid.smart.util.ScreenUtils;
import com.liulishuo.filedownloader.FileDownloader;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

public class PRJApplication extends Application {
    private static final String TAG = PRJApplication.class.getSimpleName();
    private static PRJApplication instance = null;

    public static PRJApplication getInstance() {
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        instance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.init();
        LogUtils.d(TAG, "onCreate()");
        initLogger();
        ScreenUtils.init();
        LocationService.startLocationService(this);
        FileDownloader.init(PRJApplication.getInstance());
        PackageManagerImpl.init(this);
    }

    private void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(2)         // (Optional) How many method line to show. Default 2
                .methodOffset(3)        // (Optional) Hides internal method calls up to offset. Default 5
                .tag("Smart")              // (Optional) Global tag for every log. Default PRETTY_LOGGER
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
