package com.smart.jsbridge;

import android.content.Context;

/**
 * 樣例：注册处理程序
 */
public class SampleRegisterHandler {

    private WVJBWebViewClient mWVJBWebViewClient;
    private Context mContext;

    /**
     * 构造函数
     *
     * @param mContext
     */
    public SampleRegisterHandler(WVJBWebViewClient mWVJBWebViewClient, Context mContext) {
        this.mWVJBWebViewClient = mWVJBWebViewClient;
        this.mContext = mContext.getApplicationContext();
    }

    /**
     * 初始化，注册处理程序
     */
    public void init() {
//        mWVJBWebViewClient.registerHandler("getUserId", new getUserId());
//        mWVJBWebViewClient.registerHandler("getDeviceId", new getDeviceId());
//        mWVJBWebViewClient.registerHandler("showException", new showException(mContext));
//        mWVJBWebViewClient.registerHandler("getIDCardPic", new getIDCardPic());
//        mWVJBWebViewClient.registerHandler("getSelfPic", new getSelfPic());
    }
}
