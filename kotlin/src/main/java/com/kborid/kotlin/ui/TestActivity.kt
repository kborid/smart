package com.kborid.kotlin.ui

import android.os.Bundle
import com.kborid.kotlin.R
import com.thunisoft.common.base.BaseSimpleActivity
import kotlinx.android.synthetic.main.activity_test.*
import org.jetbrains.anko.toast

class TestActivity : BaseSimpleActivity() {

    override fun getLayoutResId(): Int {
        return R.layout.activity_test;
    }

    override fun initDataAndEvent(savedInstanceState: Bundle?) {
        tv_name.text = "测试"
        tv_name.setOnClickListener {
            toast(tv_name.text)
        }
    }
}