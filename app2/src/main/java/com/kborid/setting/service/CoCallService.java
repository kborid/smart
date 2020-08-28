package com.kborid.setting.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;

import androidx.annotation.Nullable;

import com.thunisoft.common.util.DateUtils;

public class CoCallService extends Service {
    private static final int COCALL_SERVICE_ID = 518;

    Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("CoCallService launch...");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) { // API < 18 ，此方法能有效隐藏Notification上的图标
            startForeground(COCALL_SERVICE_ID, new Notification());
        } else {
            Intent innerIntent = new Intent(this, CoCallInnerService.class);
            startService(innerIntent);
            startForeground(COCALL_SERVICE_ID, new Notification());
        }
        handler.postDelayed(monitorRunnable, 2000);
        return super.onStartCommand(intent, flags, startId);
    }

    private Runnable monitorRunnable = new Runnable() {
        @Override
        public void run() {
            System.out.println("我是后台服务检测线程: " + DateUtils.formatDate(System.currentTimeMillis()));
            handler.postDelayed(monitorRunnable, 2000);
        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("CoCallService stop...");
    }

    /**
     * API >= 18，同时启动两个id相同的前台Service，然后再将后启动的Service做stop处理
     */
    public static class CoCallInnerService extends Service {

        @Override
        public void onCreate() {
            super.onCreate();
            System.out.println("CoCallInnerService launch...");
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            System.out.println("CoCallInnerService stop...");
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        /**
         * 给 API >= 18 的平台上用的灰色保活手段
         *
         * @param intent
         * @param flags
         * @param startId
         * @return
         */
        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            startForeground(COCALL_SERVICE_ID, new Notification());
            stopForeground(true);
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }
    }
}
