package com.smart.jsbridge.function;

import com.alibaba.fastjson.JSONObject;
import com.orhanobut.logger.Logger;
import com.smart.jsbridge.wvjb.handler.WVJBHandler;
import com.smart.jsbridge.wvjb.WVJBResponseCallback;

public class test implements WVJBHandler {

    private static final String TAG = test.class.getSimpleName();

    @Override
    public void request(Object data, WVJBResponseCallback callback) {
        Logger.t(TAG).d("test data");
        if (callback != null) {
            JSONObject mJson = new JSONObject();
            mJson.put("test", "test data");
            callback.callback(mJson.toString());
        }
    }
}
