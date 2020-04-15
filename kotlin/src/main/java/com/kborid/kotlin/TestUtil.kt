package com.kborid.kotlin

import com.alibaba.fastjson.JSONObject
import com.kborid.kotlin.pojo.CheckInfo

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

object TestStaticUtil {
    const val TAG: String = "TestStaticUtil"
    fun printJson(json: String): String {
        println(json)
        return json
    }
}