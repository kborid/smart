package com.kborid.kotlin

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.thunisoft.common.util.ToastUtils
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        tv_name.text = "click"
        tv_name.setOnClickListener {
            ToastUtils.showToast(tv_name.text.toString(), Toast.LENGTH_LONG)
        }
    }
}