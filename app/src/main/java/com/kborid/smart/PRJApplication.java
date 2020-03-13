package com.kborid.smart;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import androidx.multidex.MultiDex;

import com.kborid.library.base.BaseApplication;
import com.kborid.library.tools.MainThreadWatchDog;
import com.kborid.library.util.ConfigUtils;
import com.kborid.library.util.LogUtils;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.WebView;
import com.thunisoft.ThunisoftLogger;
import com.thunisoft.logger.LoggerConfig;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;

public class PRJApplication extends BaseApplication {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MainThreadWatchDog.defaultInstance().startWatch();
        ThunisoftLogger.initLogger(this, new LoggerConfig() {
            @Override
            public String getTag() {
                return getString(R.string.app_name);
            }

            @Override
            public boolean isDebug() {
                return com.kborid.library.BuildConfig.DEBUG;
            }
        });
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
        CrashReport.UserStrategy userStrategy = new CrashReport.UserStrategy(this);
        userStrategy.setCrashHandleCallback(new CrashReport.CrashHandleCallback() {
            @Override
            public synchronized byte[] onCrashHandleStart2GetExtraDatas(int crashType, String errorType, String errorMessage, String errorStack) {
                try {
                    return "Extra data.".getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public synchronized Map<String, String> onCrashHandleStart(int crashType, String errorType, String errorMessage, String errorStack) {
                LinkedHashMap<String, String> map = new LinkedHashMap<>();
                map.put("X5CrashInfo", WebView.getCrashExtraMessage(PRJApplication.getInstance()));
                return map;
            }
        });
        CrashReport.initCrashReport(this, "6b298e7c56", BuildConfig.DEBUG, userStrategy);
        initImageLoaderConfig();
        registerActivityLifecycleCallbacks(LifeCycleCallback.activityLifecycleCallbacks);
        MainThreadWatchDog.defaultInstance().stopWatch();
    }

    private void initImageLoaderConfig() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .build();
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(options)
                .memoryCache(new LruMemoryCache(1024 * 1024 * 4))
                .diskCache(new UnlimitedDiskCache(new File(Environment.getExternalStorageDirectory() + File.separator + "cache/")))
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .writeDebugLogs()
                .threadPoolSize(5)
                .build();
        ImageLoader.getInstance().init(configuration);
    }
}

