package com.kborid

import android.content.Context
import com.kborid.library.BuildConfig
import com.kborid.library.base.BaseApplication
import com.thunisoft.ThunisoftLogger
import com.thunisoft.common.ThunisoftCommon
import com.thunisoft.logger.LoggerConfig

class KotlinApplication : BaseApplication() {

    companion object {
        @JvmStatic
        var instance: KotlinApplication? = null
            private set
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        instance = this;
    }

    override fun onCreate() {
        super.onCreate()
        ThunisoftCommon.init(this)
        ThunisoftLogger.initLogger(this, object : LoggerConfig() {
            override fun getTag(): String {
                return "Smart-kotlin"
            }

            override fun isDebug(): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }
}