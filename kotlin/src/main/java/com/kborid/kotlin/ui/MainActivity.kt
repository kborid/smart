package com.kborid.kotlin.ui

import android.os.Bundle
import com.kborid.kotlin.R
import com.thunisoft.common.base.BaseSimpleActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : BaseSimpleActivity() {

    val TAG: String = MainActivity::class.java.simpleName

    override fun getLayoutResId(): Int {
        return R.layout.activity_main
    }

    override fun initDataAndEvent(savedInstanceState: Bundle?) {
        tv_name.text = "世界，你好"
        runOnUiThread { toast(tv_name.text) }

        tv_name.setOnClickListener {
            tv_name.text = "了解世界"
            toast(tv_name.text)
        }


        var count = 0;
        tv_name.setOnLongClickListener {
            tv_name.text = when (count) {
                1, 3, 5, 7, 9 -> "凉风有信"
                in 13..19 -> tv_name.text as String + "\n秋月无边"
                else -> tv_name.text as String + "\n好诗！"
            }
            count = (count + 1) % 3
            true
        }
    }
}
