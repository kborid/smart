package com.kborid.kotlin.pojo

class UserInfoImpl : BaseInfo(), IUserInfo {
    override fun getName(): String {
        TODO("Not yet implemented")
    }

    override fun getAge(): Int {
        return lv * 10
    }
}