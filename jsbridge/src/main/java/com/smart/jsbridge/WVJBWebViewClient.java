package com.smart.jsbridge;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Looper;
import android.text.TextUtils;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.orhanobut.logger.Logger;
import com.smart.jsbridge.wvjb.WVJBJavascriptCallback;
import com.smart.jsbridge.wvjb.WVJBJavascriptInterface;
import com.smart.jsbridge.wvjb.WVJBMessage;
import com.smart.jsbridge.wvjb.WVJBResponseCallback;
import com.smart.jsbridge.wvjb.handler.WVJBDefaultHandler;
import com.smart.jsbridge.wvjb.handler.WVJBHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * webview 与js交互桥梁创建，实现数据的对接交互
 *
 * @author kborid
 */
@SuppressLint({"SetJavaScriptEnabled", "NewApi"})
public class WVJBWebViewClient extends WebViewClient {
    private static final String TAG = WVJBWebViewClient.class.getSimpleName();

    private static final String kInterface = "WVJBInterface";
    private static final String kCustomProtocolScheme = "wvjbscheme";
    private static final String kQueueHasMessage = "__WVJB_QUEUE_MESSAGE__";

    private boolean logging = true;

    private long uniqueId = 0;
    private ArrayList<WVJBMessage> startupMessageQueue = null;
    private Map<String, WVJBResponseCallback> responseCallbacks = null;
    private Map<String, WVJBHandler> messageHandlers = null;
    private WVJBHandler defaultHandler;
    private WVJBJavascriptInterface kJavascriptInterface = new WVJBJavascriptInterface();
    private String mNativeJS = null;

    private WebView webView;

    public WVJBWebViewClient(WebView webView) {
        log("WVJBWebViewClient", "WVJBWebViewClient construct()");
        this.webView = webView;
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.webView.getSettings().setSupportZoom(true);
        this.webView.getSettings().setBuiltInZoomControls(true);
        this.webView.getSettings().setDisplayZoomControls(false);
        this.webView.addJavascriptInterface(kJavascriptInterface, kInterface);
        this.responseCallbacks = new HashMap<String, WVJBResponseCallback>();
        this.messageHandlers = new HashMap<String, WVJBHandler>();
        this.startupMessageQueue = new ArrayList<WVJBMessage>();
        this.defaultHandler = new WVJBDefaultHandler();
    }

    private void doSend(String handlerName, Object data, WVJBResponseCallback responseCallback) {
        log("doSend", "sendData() name=" + handlerName);
        if (TextUtils.isEmpty(handlerName))
            return;
        WVJBMessage message = new WVJBMessage();
        message.setHandlerName(handlerName);
        message.setData(data);
        if (responseCallback != null) {
            String callbackId = "objc_cb_" + (++uniqueId);
            responseCallbacks.put(callbackId, responseCallback);
            message.setCallbackId(callbackId);
        }
        queueMessage(message);
    }

    private void queueMessage(WVJBMessage message) {
        log("queueMessage", "queueMessage() message=" + message);
        if (startupMessageQueue != null) {
            startupMessageQueue.add(message);
        } else {
            dispatchMessage(message);
        }
    }

    /**
     * 消息处理
     *
     * @param message
     */
    private void dispatchMessage(WVJBMessage message) {
        log("dispatchMessage", message.getResponseData());
        String messageJSON = WVJBMessage.message2JsonString(message).replaceAll("\\\\", "\\\\\\\\").replaceAll("\"", "\\\\\"").replaceAll("\'", "\\\\\'").replaceAll("\n", "\\\\\n")
                .replaceAll("\r", "\\\\\r").replaceAll("\f", "\\\\\f");
        log("dispatchMessage", messageJSON);
        executeJavascript("WebViewJavascriptBridge._handleMessageFromObjC('" + messageJSON + "');");
    }

    private void flushMessageQueue() {
        log("flushMessageQueue", "flushMessageQueue()");
        String script = "WebViewJavascriptBridge._fetchQueue()";
        executeJavascript(script, new WVJBJavascriptCallback() {
            public void onReceiveValue(String messageQueueString) {
                log("onReceiveValue", "messageQueueString = " + messageQueueString);
                if (TextUtils.isEmpty(messageQueueString)) {
                    return;
                }
                processQueueMessage(messageQueueString);
            }
        });
    }

    private void processQueueMessage(String messageQueueString) {
        log("processQueueMessage", "processQueueMessage() messageQueueString=" + messageQueueString);
        try {
            JSONArray messages = JSON.parseArray(messageQueueString);
            for (Object object : messages) {
                JSONObject jo = (JSONObject) object;
                log("processQueueMessage", jo);

                WVJBMessage message = WVJBMessage.JsonString2Message(jo.toString());
                if (message.getResponseId() != null) {
                    WVJBResponseCallback responseCallback = responseCallbacks.remove(message.getResponseId());
                    if (responseCallback != null) {
                        responseCallback.callback(message.getResponseData());
                    }
                } else {
                    WVJBResponseCallback responseCallback = null;
                    if (message.getCallbackId() != null) {
                        final String callbackId = message.getCallbackId();
                        responseCallback = new WVJBResponseCallback() {
                            @Override
                            public void callback(Object data) {
                                WVJBMessage msg = new WVJBMessage();
                                msg.setResponseId(callbackId);
                                msg.setResponseData(data);
                                queueMessage(msg);
                            }
                        };
                    }

                    WVJBHandler handler;
                    log("handler", "handlerName=" + message.getHandlerName());
                    if (!TextUtils.isEmpty(message.getHandlerName())) {
                        handler = messageHandlers.get(message.getHandlerName());
                    } else {
                        handler = defaultHandler;
                    }
                    if (handler != null) {
                        log("handler", "handler request()");
                        handler.request(message.getData(), responseCallback);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void log(String action, Object json) {
        if (!logging)
            return;
        Logger.t(TAG).d(action + ": " + json);
    }

    public void executeJavascript(String script) {
        executeJavascript(script, null);
    }

    /**
     * 执行Javascript
     *
     * @param script
     * @param callback
     */
    public void executeJavascript(final String script, final WVJBJavascriptCallback callback) {
        log("executeJavascript", "script=" + script);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
                webView.evaluateJavascript(script, new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        if (callback != null) {
                            if (value != null && value.startsWith("\"") && value.endsWith("\"")) {
                                value = value.substring(1, value.length() - 1).replace("\\\"", "\"").replace("\\\\", "\\");
                            }
                            callback.onReceiveValue(value);
                        }
                    }
                });
            }
        } else {
            if (callback != null) {
                kJavascriptInterface.addCallback(++uniqueId + "", callback);
                webView.post(new Runnable() {
                    @Override
                    public void run() {
                        webView.loadUrl("javascript:window." + kInterface + ".onResultForScript(" + uniqueId + "," + script + ")");
                    }
                });
            } else {
                webView.post(new Runnable() {
                    @Override
                    public void run() {
                        webView.loadUrl("javascript:" + script);
                    }
                });
            }
        }
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        log("onPageFinished", "url=" + url);
        try {
            if (mNativeJS == null) {
                InputStream is = webView.getContext().getAssets().open("WebViewJavascriptBridge.js");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                mNativeJS = new String(buffer);
            }
            executeJavascript(mNativeJS);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (startupMessageQueue != null) {
            for (WVJBMessage message : startupMessageQueue) {
                dispatchMessage(message);
            }
            startupMessageQueue = null;
        }
        super.onPageFinished(view, url);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        log("shouldOverrideUrlLoading", "url=" + url);
        if (url.startsWith(kCustomProtocolScheme)) {
            if (url.indexOf(kQueueHasMessage) > 0) {
                flushMessageQueue();
            }
            return true;
        }
        return super.shouldOverrideUrlLoading(view, url);
    }

    /**
     * 是否打印log
     *
     * @param enable
     */
    public void enableLogging(boolean enable) {
        logging = enable;
    }

    public void callHandler(String handlerName) {
        callHandler(handlerName, null);
    }

    public void callHandler(String handlerName, Object data) {
        callHandler(handlerName, data, null);
    }

    /**
     * 调用JavaScript方法
     *
     * @param handlerName
     * @param data
     * @param responseCallback
     */
    public void callHandler(String handlerName, Object data, WVJBResponseCallback responseCallback) {
        log("callHandler", "callHandler() name=" + handlerName);
        doSend(handlerName, data, responseCallback);
    }

    /**
     * 注册处理程序，使JavaScript可以调用
     *
     * @param handlerName
     * @param handler
     */
    public void registerHandler(String handlerName, WVJBHandler handler) {
        log("registerHandler", "registerHandler() name=" + handlerName);
        if (TextUtils.isEmpty(handlerName)) {
            return;
        }
        messageHandlers.put(handlerName, handler);
    }
}
