package com.kborid.setting;

import android.content.Context;

import com.kborid.library.base.BaseApplication;
import com.kborid.setting.demo.t_flutter.FlutterTest;
import com.thunisoft.ThunisoftLogger;
import com.thunisoft.common.ThunisoftCommon;
import com.thunisoft.logger.LoggerConfig;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.plugins.RxJavaPlugins;
import io.realm.Realm;

public class PRJApplication extends BaseApplication {

    private static PRJApplication instance;

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
        ThunisoftCommon.init(this);
        ThunisoftLogger.initLogger(this, new LoggerConfig() {
            @Override
            public String getTag() {
                return BuildConfig.APPLICATION_ID;
            }

            @Override
            public boolean isDebug() {
                return BuildConfig.DEBUG;
            }
        });
        FlutterTest.init();
        Realm.init(this);

        RxJavaPlugins.setOnObservableSubscribe((observable, observer) -> new Observer() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("小马卧槽，Log onSubscribe()");
                System.out.println(observable);
                observer.onSubscribe(d);
            }

            @Override
            public void onNext(Object o) {
                System.out.println("小马卧槽，Log onNext()");
                observer.onNext(o);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("小马卧槽，Log onError)");
                observer.onError(e);
            }

            @Override
            public void onComplete() {
                System.out.println("小马卧槽，Log onComplete)");
                observer.onComplete();
            }
        });
    }
}