package com.kborid.kotlin.demo.pojo

open class BaseInfo {
    var lv: Int = 10
    val addr: String by lazy {
        lv += 2
        lv *= 3
        address = "四川省成都市123社区"
        "呆未厶"
    }

    lateinit var address: String

    open fun getCount(): Int {
        return 1
    }
}
