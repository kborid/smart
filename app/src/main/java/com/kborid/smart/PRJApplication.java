package com.kborid.smart;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.kborid.library.util.LogUtils;
import com.kborid.smart.service.LocationService;
import com.kborid.smart.util.ScreenUtils;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.squareup.leakcanary.LeakCanary;

public class PRJApplication extends Application {
    private static final String TAG = PRJApplication.class.getSimpleName();
    private static PRJApplication instance = null;

    public static PRJApplication getInstance(){
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
        ScreenUtils.init();
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

        LocationService.startLocationService(this);

        initLeakCanary();
    }

    private void initLeakCanary() {
        if (BuildConfig.DEBUG) {
            if (LeakCanary.isInAnalyzerProcess(this)) {
                return;
            }
            LeakCanary.install(this);
        }
    }
}
