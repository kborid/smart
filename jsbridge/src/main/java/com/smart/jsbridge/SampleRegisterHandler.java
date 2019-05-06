package com.smart.jsbridge;

import android.content.Context;

import com.smart.jsbridge.function.TestWithCallback;
import com.smart.jsbridge.function.TestWithoutCallback;

/**
 * 样例
 */
public class SampleRegisterHandler {

    private WVJBWebViewClient mWVJBWebViewClient;
    private Context mContext;

    /**
     * 构造函数
     *
     * @param context
     */
    public SampleRegisterHandler(WVJBWebViewClient mWVJBWebViewClient, Context context) {
        this.mWVJBWebViewClient = mWVJBWebViewClient;
        if (null == context) {
            throw new NullPointerException("context is not init");
        }
        this.mContext = context.getApplicationContext();
    }

    /**
     * 初始化，注册处理程序
     */
    public void init() {
        mWVJBWebViewClient.registerHandler("TestWithCallback", new TestWithCallback());
        mWVJBWebViewClient.registerHandler("TestWithoutCallback", new TestWithoutCallback(mContext));
    }
}
