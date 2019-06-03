package com.kborid.smart;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.kborid.library.base.BaseApplication;
import com.kborid.library.util.ConfigUtils;
import com.kborid.library.util.LogUtils;
import com.kborid.smart.service.LocationService;
import com.kborid.smart.util.ScreenUtils;
import com.liulishuo.filedownloader.FileDownloader;

public class PRJApplication extends BaseApplication {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ScreenUtils.init();
        LocationService.startLocationService(this);
        FileDownloader.init(PRJApplication.getInstance());
        LogUtils.d("=======================" + ConfigUtils.getConfigX());
        LogUtils.d("=======================" + ConfigUtils.getConfigY());
    }

    private void testReflect() {
        LogUtils.d("testReflect");
    }

    private static int testReflect1() {
        LogUtils.d("testReflect1");
        return 1;
    }
}

