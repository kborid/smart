package com.kborid.setting

import android.content.Context
import com.kborid.library.BuildConfig
import com.kborid.library.base.BaseApplication
import com.thunisoft.ThunisoftLogger
import com.thunisoft.logger.LoggerConfig
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor

class PRJApplication : BaseApplication() {
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        val flutterEngine = FlutterEngine(this)
        flutterEngine.dartExecutor.executeDartEntrypoint(DartExecutor.DartEntrypoint.createDefault())
        FlutterEngineCache.getInstance().put("flutter_engine", flutterEngine)
        ThunisoftLogger.initLogger(this, object : LoggerConfig() {
            override fun getTag(): String {
                return "Smart-Setting"
            }

            override fun isDebug(): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }

    companion object {
        @JvmStatic
        var instance: PRJApplication? = null
            private set
    }
}