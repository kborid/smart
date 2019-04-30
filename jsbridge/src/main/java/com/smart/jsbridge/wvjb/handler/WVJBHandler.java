package com.smart.jsbridge.wvjb.handler;

import com.smart.jsbridge.wvjb.WVJBResponseCallback;

/**
 * js请求处理函数
 */
public interface WVJBHandler {
    /**
     * @param data     请求参数
     * @param callback 回调接口
     */
    void request(Object data, WVJBResponseCallback callback);
}
