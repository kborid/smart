package com.kborid.setting.ui

import android.os.Bundle
import android.view.View
import com.kborid.setting.R
import com.thunisoft.common.base.BaseSimpleActivity

class TestActivity : BaseSimpleActivity() {
    override fun getLayoutResId(): Int {
        return R.layout.activity_trans
    }

    override fun initDataAndEvent(savedInstanceState: Bundle?) {
    }

    fun onClick(view: View) {
        this.finish()
    }
}