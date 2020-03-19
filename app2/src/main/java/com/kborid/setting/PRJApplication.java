package com.kborid.setting;

import android.content.Context;

import com.kborid.library.base.BaseApplication;
import com.kborid.setting.t_flutter.FlutterTest;
import com.thunisoft.ThunisoftLogger;
import com.thunisoft.common.ThunisoftCommon;
import com.thunisoft.logger.LoggerConfig;

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
    }
}