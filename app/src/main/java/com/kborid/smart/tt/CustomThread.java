package com.kborid.smart.tt;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

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
}
