package com.kborid.setting.widget;

import android.os.Looper;

/**
 * CustomThread
 *
 * @description: 自定义HandlerThread
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 * @version: 1.0.0
 * @date: 2020/12/10
 */
public class CustomThread extends Thread {

    private Looper mLooper;

    public CustomThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        super.run();
        System.out.println("looper prepared...and...loop...");
        Looper.prepare();
        synchronized (this) {
            mLooper = Looper.myLooper();
            notifyAll();
        }
        Looper.loop();
    }

    public Looper getLooper() {
        synchronized (this) {
            while (mLooper == null) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    // ignore
                }
            }
        }
        return mLooper;
    }
}
