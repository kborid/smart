package com.kborid.setting.test;

import android.app.IntentService;
import android.content.Intent;
import androidx.annotation.Nullable;

public class TestIntentService extends IntentService {
    public TestIntentService() {
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
