package com.kborid.smart;

import android.app.Application;
import android.content.Context;

import com.juma.jumaidapi.JumaIdApi;
import com.kborid.library.pm.PackageManagerImpl;
import com.kborid.library.util.LogUtils;
import com.kborid.smart.util.ScreenUtils;
import com.liulishuo.filedownloader.FileDownloader;
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
        instance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.d(TAG, "onCreate()");
        initLog();
        ScreenUtils.init();
//        LocationService.startLocationService(this);
        FileDownloader.init(PRJApplication.getInstance());
        JumaIdApi.init(this);
        PackageManagerImpl.init(this);
    }

    private void initLog() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(0)         // (Optional) How many method line to show. Default 2
                .methodOffset(0)        // (Optional) Hides internal method calls up to offset. Default 5
                .tag("Smart")              // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new DiskLogAdapter(formatStrategy));
    }
}
