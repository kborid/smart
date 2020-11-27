package com.kborid.setting.tool

object TestDataHelper {
    @JvmStatic
    fun getTestStringData(): ArrayList<String> {
        val data = ArrayList<String>()
        for (i in 1..50) {
            data.add("我是测试数据-$i")
        }
        return data
    }
}