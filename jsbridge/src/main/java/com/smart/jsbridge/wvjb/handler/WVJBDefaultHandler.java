package com.smart.jsbridge.wvjb.handler;

import android.util.Log;

import com.smart.jsbridge.wvjb.WVJBResponseCallback;

/**
 * js默认请求处理
 */
public class WVJBDefaultHandler implements WVJBHandler {

    private static final String TAG = WVJBDefaultHandler.class.getSimpleName();

    @Override
    public void request(Object data, WVJBResponseCallback callback) {
        Log.i(TAG, "default handler call...");
        if (callback != null) {
            callback.callback("DefaultHandler response data");
        }
    }
}
