package com.kborid.setting.t_flutter;

import com.kborid.setting.PRJApplication;

import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterEngineCache;
import io.flutter.embedding.engine.dart.DartExecutor;

public class FlutterTest {

    public static void init() {
        FlutterEngine flutterEngine = new FlutterEngine(PRJApplication.getInstance());
        flutterEngine.getDartExecutor().executeDartEntrypoint(DartExecutor.DartEntrypoint.createDefault());
        FlutterEngineCache.getInstance().put("flutter_engine", flutterEngine);
    }

}
