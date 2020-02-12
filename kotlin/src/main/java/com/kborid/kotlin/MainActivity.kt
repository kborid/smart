package com.kborid.kotlin

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.thunisoft.common.base.BaseActivity
import com.thunisoft.common.util.ToastUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override fun getLayoutResId(): Int {
        return R.layout.activity_main
    }

    @SuppressLint("SetTextI18n")
    override fun initDataAndEvent(bundle: Bundle?) {
        tv_name.text = "perfecter invoked"
        tv_name.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                ToastUtils.showToast(tv_name.text.toString(), Toast.LENGTH_SHORT)
            }
        })
    }
}
