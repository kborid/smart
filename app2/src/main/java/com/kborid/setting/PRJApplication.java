package com.kborid.setting;

import android.content.IntentFilter;
import com.kborid.demo.t_flutter.FlutterTest;
import com.kborid.library.base.BaseApplication;
import com.kborid.setting.broadcast.LaunchLockerBroadcastReceiver;
import com.kborid.setting.constant.Constants;
import com.thunisoft.ThunisoftLogger;
import com.thunisoft.common.ThunisoftCommon;
import com.thunisoft.common.util.SystemInfoUtils;
import com.thunisoft.logger.LoggerConfig;
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
//        RxJavaPlugins.setOnObservableSubscribe(new BiFunction<Observable, Observer, Observer>() {
//            @Override
//            public Observer apply(Observable observable, Observer observer) throws Exception {
//                return null;
//            }
//        });

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

        initRegisterBroadcast();
    }

    private void initRegisterBroadcast() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.BROADCAST_LOCKED_LAUNCHER);
        registerReceiver(new LaunchLockerBroadcastReceiver(), intentFilter);

        System.out.println("devicesId:" + SystemInfoUtils.getDeviceId(this));
        System.out.println("Imei:" + SystemInfoUtils.getIMEI(this));
    }
}