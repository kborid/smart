package com.kborid.library.base;

import android.app.Application;
import android.content.Context;

import com.kborid.library.di.component.AppComponent;
import com.kborid.library.di.component.DaggerAppComponent;
import com.kborid.library.di.module.AppModule;
import com.kborid.library.util.LogUtils;
import com.thunisoft.common.ThunisoftCommon;
import com.thunisoft.common.tool.CrashHandler;
import com.thunisoft.ui.ThunisoftUI;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

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

//        if (LeakCanary.isInAnalyzerProcess(this)) {
        // This process is dedicated to LeakCanary for heap analysis.
        // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);

        ThunisoftCommon.init(this);
        ThunisoftUI.init(this);
//        PackageManagerImpl.init(this);
    }

    public static AppComponent getAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(instance))
                .build();
    }

    protected static Observer<String> apply(Observable<String> observable, Observer<String> observer) {
        System.out.println("小马卧槽，apply()");
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("小马卧槽，onSubscribe()");
                System.out.println(observable);
                observer.onSubscribe(d);
            }

            @Override
            public void onNext(String o) {
                System.out.println("小马卧槽，onNext()");
                observer.onNext(o);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("小马卧槽，onError)");
                observer.onError(e);
            }

            @Override
            public void onComplete() {
                System.out.println("小马卧槽，onComplete)");
                observer.onComplete();
            }
        };
    }
}
