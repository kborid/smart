package com.kborid.setting;

import android.content.IntentFilter;
import android.os.Debug;
import com.kborid.demo.t_flutter.FlutterTest;
import com.kborid.library.base.BaseApplication;
import com.kborid.setting.broadcast.LaunchLockerBroadcastReceiver;
import com.kborid.setting.constant.Constants;
import com.thunisoft.ThunisoftLogger;
import com.thunisoft.logger.LoggerConfig;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import io.realm.Realm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PRJApplication extends BaseApplication {

    private static final Logger logger = LoggerFactory.getLogger(PRJApplication.class);

    @Override
    public void onCreate() {
        super.onCreate();
        ThunisoftLogger.initLogger(this,
                LoggerConfig.createLoggerConfig(BuildConfig.APPLICATION_ID, BuildConfig.DEBUG));
        FlutterTest.init();

//        RxJavaPlugins.setOnObservableSubscribe(PRJApplication::apply);
        RxJavaPlugins.setOnObservableAssembly(new Function<Observable, Observable>() {
            @Override
            public Observable apply(Observable observable) throws Exception {
                System.out.println("只为测试");
                return observable;
            }
        });

        initRegisterBroadcast();
    }

    private void initRegisterBroadcast() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.BROADCAST_LOCKED_LAUNCHER);
        registerReceiver(new LaunchLockerBroadcastReceiver(), intentFilter);
    }

    protected static Observer<String> apply(Observable<String> observable,
            Observer<String> observer) {
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                logger.info("小马卧槽，onSubscribe thread:【{}】", Thread.currentThread().getName());
                observer.onSubscribe(d);
            }

            @Override
            public void onNext(String o) {
                logger.info("小马卧槽，onNext thread:【{}】", Thread.currentThread().getName());
                observer.onNext(o);
            }

            @Override
            public void onError(Throwable e) {
                logger.info("小马卧槽，onError thread:【{}】", Thread.currentThread().getName());
                observer.onError(e);
            }

            @Override
            public void onComplete() {
                logger.info("小马卧槽，onComplete thread:【{}】", Thread.currentThread().getName());
                observer.onComplete();
            }
        };
    }
}