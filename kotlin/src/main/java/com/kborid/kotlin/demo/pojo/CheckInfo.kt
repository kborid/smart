package com.kborid.kotlin.demo.pojo

class CheckInfo : BaseInfo() {

    override fun getCount(): Int {
        return lv * 2
    }
}