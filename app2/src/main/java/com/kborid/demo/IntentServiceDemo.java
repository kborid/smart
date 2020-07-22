package com.kborid.demo;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

/**
 * IntentServiceDemo
 *
 * @description: IntentServer演示
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 * @version: 1.0.0
 * @date: 2020/6/12
 */
public class IntentServiceDemo extends IntentService {
    public IntentServiceDemo() {
        super("Kborid Intent Service");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        System.out.println("onHandleIntent() start");
        if (null == intent) {
            return;
        }

        int time = intent.getIntExtra("time", 0);
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("onHandleIntent() end");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("this Intent service destroy");
    }
}
