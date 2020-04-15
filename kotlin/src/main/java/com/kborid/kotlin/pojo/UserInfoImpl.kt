package com.kborid.kotlin.pojo

import com.kborid.kotlin.IUserInfo

class UserInfoImpl : BaseInfo(), IUserInfo {
    override fun getName(): String {
        TODO("Not yet implemented")
    }

    override fun getAge(): Int {
        return lv * 10
    }
}