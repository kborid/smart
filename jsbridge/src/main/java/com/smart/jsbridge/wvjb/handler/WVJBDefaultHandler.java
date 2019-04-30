package com.smart.jsbridge.wvjb.handler;

import com.orhanobut.logger.Logger;
import com.smart.jsbridge.wvjb.WVJBResponseCallback;
import com.smart.jsbridge.wvjb.WVJBUtils;

/**
 * js默认请求处理
 */
public class WVJBDefaultHandler implements WVJBHandler {

    @Override
    public void request(Object data, WVJBResponseCallback callback) {
        Logger.t(WVJBUtils.TAG).d("default handler call...");
        if (callback != null) {
            callback.callback("DefaultHandler response data");
        }
    }
}
