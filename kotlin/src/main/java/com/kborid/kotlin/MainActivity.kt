package com.kborid.kotlin

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import com.alibaba.fastjson.JSONObject
import com.kborid.kotlin.pojo.CheckInfo
import com.thunisoft.common.base.BaseActivity
import com.thunisoft.common.util.ToastUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override fun getLayoutResId(): Int {
        return R.layout.activity_main
    }

    override fun initDataAndEvent(bundle: Bundle?) {
        tv_name.text = "测试"
        tv_name.setOnClickListener {
            parseJson("{}")
            ToastUtils.showToast(tv_name.text.toString(), Toast.LENGTH_SHORT)
        }
    }

    private fun parseJson(json: String?) {
        println(json)
        JSONObject.parseObject(json, CheckInfo::class.java)
        TestUtil().printJson(json)
        TestUtil.parseJson(json)
        if (json != null) {
            TTest.instance?.test(json)
        }
    }
}
