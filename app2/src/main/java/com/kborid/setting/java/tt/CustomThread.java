package com.kborid.setting.java.tt;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import static com.kborid.setting.java.tt.CodeTypeConst.CODE_TYPE1;
import static com.kborid.setting.java.tt.CodeTypeConst.CODE_TYPE2;

@TargetApi(Build.VERSION_CODES.KITKAT)
public class CustomThread extends Thread {

    private static Handler innerHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            System.out.println("Handler's callback");
            return false;
        }
    }) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            System.out.println("Handler's handleMessage()");
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
        }
    }
}
