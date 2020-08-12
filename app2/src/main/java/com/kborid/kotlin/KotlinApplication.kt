package com.kborid.kotlin

import android.content.Context
import com.kborid.library.BuildConfig
import com.kborid.library.base.BaseApplication
import com.kborid.setting.PRJApplication
import com.thunisoft.ThunisoftLogger
import com.thunisoft.common.ThunisoftCommon
import com.thunisoft.logger.LoggerConfig
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.functions.BiFunction
import io.reactivex.plugins.RxJavaPlugins

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
        RxJavaPlugins.setOnObservableSubscribe { t1, t2 -> apply(t1 as Observable<String>?, t2 as Observer<String>?) }
    }
}