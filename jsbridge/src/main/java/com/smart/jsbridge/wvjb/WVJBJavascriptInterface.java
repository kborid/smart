package com.smart.jsbridge.wvjb;

import android.webkit.JavascriptInterface;

import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;

public class WVJBJavascriptInterface {

    private Map<String, WVJBJavascriptCallback> map = new HashMap<String, WVJBJavascriptCallback>();

    public void addCallback(String key, WVJBJavascriptCallback callback) {
        map.put(key, callback);
    }

    @JavascriptInterface
    public void onResultForScript(String key, String value) {
        Logger.t(WVJBUtils.TAG).d("WVJBJavascriptInterface::onResultForScript: " + value);
        WVJBJavascriptCallback callback = map.remove(key);
        if (callback != null)
            callback.onReceiveValue(value);
    }
}
