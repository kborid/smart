package com.kborid.kotlin.pojo

data class Address(val name: String) : SecretInfo(name) {

    var province: String? = null
    var city: String? = null
    var p: String? = null

    constructor(province: String, city: String) : this("地址1") {
        this.province = province
        this.city = city
        this.p = pinjie()
    }

    fun pinjie(): String {
        return province + "省, " + city + "市"
    }

    override fun toString(): String {
        return "AddressInfo(province=$province, city=$city)"
    }
}
