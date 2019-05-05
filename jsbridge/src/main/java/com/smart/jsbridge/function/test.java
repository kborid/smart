package com.smart.jsbridge.function;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.smart.jsbridge.wvjb.WVJBResponseCallback;
import com.smart.jsbridge.wvjb.handler.WVJBHandler;

public class test implements WVJBHandler {

    private static final String TAG = test.class.getSimpleName();

    @Override
    public void request(Object data, WVJBResponseCallback callback) {
        Log.i(TAG, "test data");
        if (callback != null) {
            JSONObject mJson = new JSONObject();
            mJson.put("test", "test data");
            callback.callback(mJson.toString());
        }
    }
}
