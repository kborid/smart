package com.smart.jsbridge.function;

import com.alibaba.fastjson.JSONObject;
import com.orhanobut.logger.Logger;
import com.smart.jsbridge.wvjb.WVJBResponseCallback;
import com.smart.jsbridge.wvjb.handler.WVJBHandler;

public class TestWithCallback implements WVJBHandler {

    private static final String TAG = TestWithCallback.class.getSimpleName();

    @Override
    public void request(Object data, WVJBResponseCallback callback) {
        Logger.t(TAG).i("TestWithCallback data");
        if (callback != null) {
            JSONObject mJson = new JSONObject();
            mJson.put("TestWithCallback", "TestWithCallback data");
            callback.callback(mJson.toString());
        }
    }
}
