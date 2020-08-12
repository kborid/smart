package com.kborid.setting;

import com.kborid.demo.t_flutter.FlutterTest;
import com.kborid.library.base.BaseApplication;
import com.thunisoft.ThunisoftLogger;
import com.thunisoft.common.ThunisoftCommon;
import com.thunisoft.logger.LoggerConfig;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.plugins.RxJavaPlugins;
import io.realm.Realm;

public class PRJApplication extends BaseApplication {

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

        RxJavaPlugins.setOnObservableSubscribe(PRJApplication::apply);
//        RxJavaPlugins.setOnObservableSubscribe(new BiFunction<Observable, Observer, Observer>() {
//            @Override
//            public Observer apply(Observable observable, Observer observer) throws Exception {
//                return null;
//            }
//        });
    }
}