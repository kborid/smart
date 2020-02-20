package com.kborid.setting;

import android.content.Context;

import com.kborid.library.base.BaseApplication;
import com.thunisoft.ThunisoftLogger;
import com.thunisoft.common.ThunisoftCommon;
import com.thunisoft.logger.LoggerConfig;

import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterEngineCache;
import io.flutter.embedding.engine.dart.DartExecutor;

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
        FlutterEngine flutterEngine = new FlutterEngine(this);
        flutterEngine.getDartExecutor().executeDartEntrypoint(DartExecutor.DartEntrypoint.createDefault());
        FlutterEngineCache.getInstance().put("flutter_engine", flutterEngine);
        ThunisoftCommon.init(this);
        ThunisoftLogger.initLogger(this, new LoggerConfig() {
            @Override
            public String getTag() {
                return "Smart-setting";
            }

            @Override
            public boolean isDebug() {
                return BuildConfig.DEBUG;
            }
        });
    }
}