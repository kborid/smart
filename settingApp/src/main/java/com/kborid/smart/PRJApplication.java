package com.kborid.smart;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.kborid.library.base.BaseApplication;
import com.kborid.library.util.ConfigUtils;
import com.kborid.library.util.LogUtils;
import com.kborid.smart.service.LocationService;
import com.kborid.library.util.ScreenUtils;
import com.liulishuo.filedownloader.FileDownloader;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;

public class PRJApplication extends BaseApplication {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ScreenUtils.init(this);
        LocationService.startLocationService(this);
        FileDownloader.init(PRJApplication.getInstance());
        LogUtils.d("=======================" + ConfigUtils.getConfigX());
        LogUtils.d("=======================" + ConfigUtils.getConfigY());
        Bugly.init(getApplicationContext(), "6b298e7c56", BuildConfig.DEBUG);
    }

    private void testReflect() {
        LogUtils.d("testReflect");
    }

    private static int testReflect1() {
        LogUtils.d("testReflect1");
        return 1;
    }
}

