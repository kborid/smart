package com.kborid.setting.ui

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.kborid.setting.PRJApplication
import com.kborid.setting.R
import com.kborid.setting.constant.LifecycleState
import org.slf4j.LoggerFactory
import java.lang.reflect.Field
import java.util.*

class LifeCycleActivity : Activity() {
    @LifecycleState
    var state = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_lifecycle)
        logger.info("执行 onCreate() 方法")
        logger.info("获取state的值：{}", state)
        (findViewById<View>(R.id.tv_content) as TextView).text = printSystemInfo()
    }

    override fun onStart() {
        super.onStart()
        logger.info("执行 onStart() 方法")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        logger.info("执行 onConfigurationChanged() 方法")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        logger.info("执行 onRestoreInstanceState() 方法")
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        logger.info("执行 onPostCreate() 方法")
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        logger.info("执行 onNewIntent() 方法")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        logger.info("执行 onSaveInstanceState() 方法")
    }

    override fun onLowMemory() {
        super.onLowMemory()
        logger.info("执行 onLowMemory() 方法")
    }

    override fun onRestart() {
        super.onRestart()
        logger.info("执行 onRestart() 方法")
    }

    override fun onResume() {
        super.onResume()
        logger.info("执行 onResume() 方法")
    }

    override fun onPause() {
        super.onPause()
        logger.info("执行 onPause() 方法")
    }

    override fun onStop() {
        super.onStop()
        logger.info("执行 onStop() 方法")
    }

    override fun onDestroy() {
        super.onDestroy()
        logger.info("执行 onDestroy() 方法")
    }

    private fun printSystemInfo(): String {
        val sb = StringBuilder()
        sb.append("当前系统API Level：").append(Build.VERSION.SDK_INT).append("\n")
        sb.append("当前APP targetSDK：").append(PRJApplication.getInstance().applicationInfo.targetSdkVersion).append("\n")
        try {
            val clazz = Class.forName("android.os.Build")
            val fields = clazz.declaredFields
            Arrays.stream(fields).forEach { field: Field ->
                field.isAccessible = true
                try {
                    sb.append(field.name).append("：").append(field[null]).append("\n")
                } catch (e: IllegalAccessException) {
                    logger.info("反射获取字段{}失败", field.name)
                }
            }
        } catch (e: Exception) {
            logger.info("反射加载系统Build类失败")
        }
        return sb.toString()
    }

    companion object {
        private val logger = LoggerFactory.getLogger(LifeCycleActivity::class.java)
    }
}