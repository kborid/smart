package com.kborid.kotlin

import com.alibaba.fastjson.JSONObject
import com.kborid.kotlin.pojo.CheckInfo

class TestUtil {
    val TAG = "test";
    fun printJson(json: String?): String? {
        println(json)
        return json
    }

    companion object {
        fun parseJson(json: String?): Any {
            return JSONObject.parseObject(json, CheckInfo::class.java)
        }
    }
}

object TestStaticUtil {
    fun printJson(json: String): String {
        println(json)
        return json
    }
}