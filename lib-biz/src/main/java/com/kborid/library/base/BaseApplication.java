package com.kborid.library.base;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.kborid.library.BuildConfig;
import com.kborid.library.di.component.AppComponent;
import com.kborid.library.di.component.DaggerAppComponent;
import com.kborid.library.di.module.AppModule;
import com.kborid.library.util.LogUtils;
import com.thunisoft.common.ThunisoftCommon;
import com.thunisoft.common.tool.CrashHandler;
import com.thunisoft.ui.ThunisoftUI;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

public class BaseApplication extends Application {

    private static final String TAG = BaseApplication.class.getSimpleName();

    protected static BaseApplication instance;

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
        CrashHandler.getInstance().init();
        LogUtils.init();
        LogUtils.d(TAG, "onCreate()");

        ThunisoftCommon.init(this);
        ThunisoftUI.init(this);

        if (BuildConfig.DEBUG) {
            Stetho.initialize(Stetho.newInitializerBuilder(this)
                    .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                    .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).withMetaTables().build())
                    .build());
        }

//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);
//        PackageManagerImpl.init(this);
//        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
//                .detectAll()
//                .penaltyLog()
//                .build());
//        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
//                .detectLeakedSqlLiteObjects()
//                .detectLeakedClosableObjects()
//                .penaltyLog()
//                .penaltyDeath()
//                .build());
    }

    public static AppComponent getAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(instance))
                .build();
    }
}
