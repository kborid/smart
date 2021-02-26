package com.kborid.setting.ui.main.demo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.kborid.demo.IntentServiceDemo
import com.kborid.setting.R
import com.kborid.setting.constant.CodeType
import com.kborid.setting.constant.CodeTypeConst
import com.kborid.setting.ui.LifeCycleActivity
import com.kborid.setting.widget.CustomThread
import com.thunisoft.common.base.BaseSimpleFragment
import org.slf4j.LoggerFactory

/**
 * DemoFragment.kt
 *
 * @description: dddd
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 * @date: 2021/2/26
 */
class DemoFragment : BaseSimpleFragment(), View.OnClickListener {
    var mCustomThread: CustomThread? = null
    var mHandlerThread: HandlerThread? = null
    var mCustomThreadHandler: Handler? = null
    var mHandlerThreadHandler: Handler? = null
    override fun getLayoutResId(): Int {
        return R.layout.frag_demo
    }

    override fun initEventAndData(savedInstanceState: Bundle?) {
        mCustomThread = CustomThread("CustomThread-Demo")
        mCustomThread!!.start()
        mCustomThreadHandler = object : Handler(mCustomThread!!.looper) {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                logger.info("Thread:【{}】, handleMessage()", Thread.currentThread().name)
                logger.info(msg.toString())
            }
        }
        mHandlerThread = HandlerThread("HandlerThread-Demo")
        mHandlerThread!!.start()
        mHandlerThreadHandler = object : Handler(mHandlerThread!!.looper) {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                logger.info("Thread:【{}】, handleMessage()", Thread.currentThread().name)
                logger.info(msg.toString())
            }
        }
        mRootView.findViewById<View>(R.id.btn_handler).setOnClickListener(this)
        mRootView.findViewById<View>(R.id.btn_lifeCycle).setOnClickListener(this)
        mRootView.findViewById<View>(R.id.btn_intent).setOnClickListener(this)
        mRootView.findViewById<View>(R.id.btn_other).setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_handler -> {
                mCustomThreadHandler!!.post { logger.info("我在执行。。。") }
                mCustomThreadHandler!!.sendEmptyMessage(1)
                mHandlerThreadHandler!!.sendEmptyMessageDelayed(3, 2000)
                val msg = Message.obtain()
                msg.what = 2
                msg.obj = "我是任务2"
                mHandlerThreadHandler!!.sendMessage(msg)
            }
            R.id.btn_intent -> {
                val intent = Intent(context, IntentServiceDemo::class.java)
                intent.putExtra("time", 2000)
                context?.startService(intent)
            }
            R.id.btn_lifeCycle -> startActivity(Intent(context, LifeCycleActivity::class.java))
            else -> {
//                DemoManager.getWsListByMbbh("111").subscribe(RxUtil.createDefaultSubscriber { objects -> println(objects) })
                Glide.with(this)
                        .load("")
                        .into(mRootView.findViewById<ImageView>(R.id.iv_glide))
            }
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(DemoFragment::class.java.simpleName)
        fun getCodeTest(@CodeType code: Int) {
            when (code) {
                CodeTypeConst.CODE_TYPE1 -> {
                    println("1")
                }
                CodeTypeConst.CODE_TYPE2 -> {
                    println("2")
                }
                4 -> {
                    println("3")
                }
                else -> {
                    println("else")
                }
            }
        }
    }
}
