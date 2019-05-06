package com.smart.jsbridge.function;

import android.content.Context;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.smart.jsbridge.wvjb.WVJBResponseCallback;
import com.smart.jsbridge.wvjb.handler.WVJBHandler;

public class TestWithoutCallback implements WVJBHandler {

    private Context context;

    public TestWithoutCallback(Context context) {
        this.context = context;
    }

    @Override
    public void request(Object data, WVJBResponseCallback callback) {
        Logger.t("TestWithoutCallback").d("callback=" + callback);
        Toast.makeText(context, "test without callback", Toast.LENGTH_SHORT).show();
    }
}
