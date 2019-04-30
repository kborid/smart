package com.kborid.smart.activity;

import android.content.Context;

import com.smart.jsbridge.WVJBWebViewClient;

/**
 * 注册处理程序
 */
public class RegisterHandler {

    private WVJBWebViewClient mWVJBWebViewClient;
    private Context mContext;

    /**
     * 构造函数
     *
     * @param mContext
     */
    public RegisterHandler(WVJBWebViewClient mWVJBWebViewClient, Context mContext) {
        this.mWVJBWebViewClient = mWVJBWebViewClient;
        this.mContext = mContext;
    }

    /**
     * 初始化，注册处理程序
     */
    public void init() {
        mWVJBWebViewClient.registerHandler("test", new test());
    }
}
