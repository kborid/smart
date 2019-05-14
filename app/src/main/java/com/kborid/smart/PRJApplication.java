package com.kborid.smart;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.kborid.library.base.BaseApplication;
import com.kborid.library.util.ConfigUtils;
import com.kborid.smart.util.ScreenUtils;
import com.liulishuo.filedownloader.FileDownloader;

public class PRJApplication extends BaseApplication {
    private static final String TAG = PRJApplication.class.getSimpleName();


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ScreenUtils.init();
//        LocationService.startLocationService(this);
        FileDownloader.init(PRJApplication.getInstance());
        System.out.println("=======================" + ConfigUtils.getConfigX());
        System.out.println("=======================" + ConfigUtils.getConfigY());
    }
}

