package com.kborid.setting;

import android.content.IntentFilter;

import com.kborid.demo.t_flutter.FlutterTest;
import com.kborid.library.base.BaseApplication;
import com.kborid.setting.broadcast.LaunchLockerBroadcastReceiver;
import com.kborid.setting.constant.Constants;
import com.thunisoft.ThunisoftLogger;
import com.thunisoft.common.ThunisoftCommon;
import com.thunisoft.logger.LoggerConfig;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.plugins.RxJavaPlugins;
import io.realm.Realm;

public class PRJApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        ThunisoftCommon.init(this);
        ThunisoftLogger.initLogger(this, LoggerConfig.createLoggerConfig(BuildConfig.APPLICATION_ID, BuildConfig.DEBUG));
        FlutterTest.init();
        Realm.init(this);

        RxJavaPlugins.setOnObservableSubscribe(PRJApplication::apply);

        initRegisterBroadcast();
    }

    private void initRegisterBroadcast() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.BROADCAST_LOCKED_LAUNCHER);
        registerReceiver(new LaunchLockerBroadcastReceiver(), intentFilter);
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