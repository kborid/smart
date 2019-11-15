package com.kborid.setting;

import com.kborid.library.BuildConfig;
import com.kborid.library.base.BaseApplication;
import com.thunisoft.ThunisoftLogger;
import com.thunisoft.logger.LoggerConfig;

import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterEngineCache;
import io.flutter.embedding.engine.dart.DartExecutor;

public class PRJApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        FlutterEngine flutterEngine = new FlutterEngine(this);
        flutterEngine.getDartExecutor().executeDartEntrypoint(DartExecutor.DartEntrypoint.createDefault());
        FlutterEngineCache.getInstance().put("flutter_engine", flutterEngine);

        ThunisoftLogger.initLogger(this, new LoggerConfig() {
            @Override
            public String getTag() {
                return "Smart-Setting";
            }

            @Override
            public boolean isDebug() {
                return BuildConfig.DEBUG;
            }
        });
    }
}
