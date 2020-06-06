package com.kborid.kotlin.ui

import android.os.Bundle
import com.alibaba.fastjson.JSONObject
import com.kborid.kotlin.R
import com.kborid.kotlin.TestStaticUtil
import com.kborid.kotlin.TestUtil
import com.kborid.kotlin.pojo.CheckInfo
import com.thunisoft.common.base.BaseActivity
import kotlinx.android.synthetic.main.activity_test.*
import org.jetbrains.anko.toast

const val TTT: String = "TTT"

class TestActivity : BaseActivity() {

    override fun getLayoutResId(): Int {
        return R.layout.activity_test;
    }

    override fun initDataAndEvent(savedInstanceState: Bundle?) {
        tv_name.text = "测试"
        tv_name.setOnClickListener {
            parseJson("{}")
            toast(tv_name.text)
        }
    }

    private fun parseJson(json: String) {
        println(json)
        JSONObject.parseObject(json, CheckInfo::class.java)
        TestUtil().printJson(json)
        TestUtil.parseJson(json)
        TestStaticUtil.printJson(json)
    }
}