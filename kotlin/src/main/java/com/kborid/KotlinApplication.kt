package com.kborid

import com.kborid.library.base.BaseApplication
import com.thunisoft.common.ThunisoftCommon

class KotlinApplication : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        ThunisoftCommon.init(this)
    }
}