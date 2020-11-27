package com.kborid.setting.widget;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.kborid.setting.constant.CodeType;

import static com.kborid.setting.constant.CodeTypeConst.CODE_TYPE1;
import static com.kborid.setting.constant.CodeTypeConst.CODE_TYPE2;

@TargetApi(Build.VERSION_CODES.KITKAT)
public class CustomThread extends Thread {

    private static Handler innerHandler = new Handler(Looper.myLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            System.out.println("我是Handler的callback，是我在处理消息" + ", 消息:" + msg.what);
            return false;
        }
    }) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            System.out.println("我是Handler的handleMessage，我在处理消息" + ", 消息:" + msg.what);
            getCodeTest(CODE_TYPE1);
        }
    };

    @Override
    public void run() {
        super.run();
        Looper.prepare();
        System.out.println("looper prepared...start loop...");
        Looper.loop();
    }

    public Handler getInnerHandler() {
        return innerHandler;
    }

    public static void getCodeTest(@CodeType int code) {
        switch (code) {
            case CODE_TYPE1:
                break;
            case CODE_TYPE2:
                break;
            default:
                break;
        }
    }
}
