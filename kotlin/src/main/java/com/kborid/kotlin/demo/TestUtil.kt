package com.kborid.kotlin.demo

import com.alibaba.fastjson.JSONObject
import com.kborid.kotlin.demo.pojo.CheckInfo

class TestUtil {
    fun printJson(json: String?): String {
        println(json)
        return json!!
    }

    // TestUtil伴生对象
    companion object {
        const val TAG: String = "TestUtil"

        fun parseJson(json: String?): Any {
            return JSONObject.parseObject(json, CheckInfo::class.java)
        }
    }
}

fun main() {
    val json = "{}"
    println(json)
    JSONObject.parseObject(json, CheckInfo::class.java)
    TestUtil().printJson(json)
    TestUtil.parseJson(json)
    TestStaticUtil.printJson(json)
}

object TestStaticUtil {
    const val TAG: String = "TestStaticUtil"
    fun printJson(json: String): String {
        println(json)
        return json
    }
}