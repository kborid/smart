package com.kborid.smart.constant;


import android.app.Activity;

import com.alibaba.fastjson.JSON;
import com.kborid.smart.PRJApplication;
import com.kborid.smart.entity.TokenInfo;
import com.kborid.smart.entity.UserInfo;
import com.kborid.smart.util.ToastDrawableUtil;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * GlobalThridManager
 *
 * @description: 三方登录全局共享
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 * @version: 1.0.0
 * @date: 2020/12/2
 */
public class GlobalThirdManager {

    private static Logger logger = LoggerFactory.getLogger(GlobalThirdManager.class);

    private static TokenInfo mToken;
    private static UserInfo mUser;

    // qq登录
    private static Tencent mTencent;

    public static void init() {
        mTencent = Tencent.createInstance("101916533", PRJApplication.getInstance(), "com.kborid.smart.fileProvider");
    }

    public static TokenInfo getToken() {
        return mToken;
    }

    public static void setToken(TokenInfo token) {
        mToken = token;
    }

    public static UserInfo getUser() {
        return mUser;
    }

    public static void setUser(UserInfo user) {
        mUser = user;
    }

    public static boolean isLogin() {
        return Objects.nonNull(mToken) && Objects.nonNull(mUser);
    }

    public static void logout() {
        mToken = null;
        mUser = null;
        mTencent.logout(PRJApplication.getInstance());
    }

    public static void login(Activity activity, IUiListener listener) {
        if (!mTencent.isSessionValid()) {
            mTencent.login(activity, "all", new IUiListener() {
                @Override
                public void onComplete(Object o) {
                    ToastDrawableUtil.showImgToast("登录成功");
                    logger.info("token = {}", o.toString());
                    TokenInfo tokenInfo = JSON.parseObject(o.toString(), TokenInfo.class);
                    GlobalThirdManager.setToken(tokenInfo);
                    if (null != listener)
                        listener.onComplete(o);
                }

                @Override
                public void onError(UiError uiError) {
                    ToastDrawableUtil.showImgToast("登录错误");
                }

                @Override
                public void onCancel() {
                    ToastDrawableUtil.showImgToast("登录取消");
                }

                @Override
                public void onWarning(int i) {
                    ToastDrawableUtil.showImgToast("登录警告");
                }
            });
        }
    }

    public static void getUserInfo(IUiListener listener) {
        mTencent.setOpenId(mToken.getOpenid());
        mTencent.setAccessToken(mToken.getAccess_token(), mToken.getExpires_in());
        com.tencent.connect.UserInfo info = new com.tencent.connect.UserInfo(PRJApplication.getInstance(), mTencent.getQQToken());
        info.getUserInfo(new IUiListener() {
            @Override
            public void onComplete(Object o) {
                ToastDrawableUtil.showImgToast("获取用户信息成功");
                logger.info("userinfo = {}", o.toString());
                UserInfo userInfo = JSON.parseObject(o.toString(), UserInfo.class);
                GlobalThirdManager.setUser(userInfo);
                if (null != listener)
                    listener.onComplete(o);
            }

            @Override
            public void onError(UiError uiError) {
                ToastDrawableUtil.showImgToast("获取用户信息成功");
            }

            @Override
            public void onCancel() {
                ToastDrawableUtil.showImgToast("获取用户信息成功");
            }

            @Override
            public void onWarning(int i) {
                ToastDrawableUtil.showImgToast("获取用户信息成功");
            }
        });
    }
}