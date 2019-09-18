package com.kborid.smart;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.multidex.MultiDex;

import com.kborid.library.base.BaseApplication;
import com.kborid.library.util.ConfigUtils;
import com.kborid.library.util.LogUtils;
import com.kborid.library.util.ScreenUtils;
import com.kborid.smart.service.LocationService;
import com.liulishuo.filedownloader.FileDownloader;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.tencent.bugly.Bugly;
import com.tencent.smtt.sdk.QbSdk;

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
        QbSdk.initX5Environment(this, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                System.out.println("onCoreInitFinished()");
            }

            @Override
            public void onViewInitFinished(boolean b) {
                System.out.println("onViewInitFinished()");
            }
        });
        initImageLoaderConfig();
    }

    private void initImageLoaderConfig() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnFail(R.mipmap.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
//                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .build();
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(options)
                .memoryCache(new LruMemoryCache(1024 * 1024 * 4))
                .writeDebugLogs()
                .threadPoolSize(5)
                .build();
        ImageLoader.getInstance().init(configuration);
    }
}

