package com.kborid.smart.event;

import com.kborid.smart.entity.UserInfo;

/**
 * UpdateUserInfoEvent
 *
 * @description: 更新用户信息event
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 * @version: 1.0.0
 * @date: 2020/12/2
 */
public class UpdateUserInfoEvent {
    private UserInfo userInfo;

    public UpdateUserInfoEvent(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}