package com.kborid

import android.content.Context
import com.kborid.library.base.BaseApplication
import com.thunisoft.common.ThunisoftCommon

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
    }
}