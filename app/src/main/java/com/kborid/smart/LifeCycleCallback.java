package com.kborid.smart;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.thunisoft.common.util.ToastUtils;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public class LifeCycleCallback {

    private static int mForeActCounter = 0;

    public static Application.ActivityLifecycleCallbacks activityLifecycleCallbacks = new EmptyActivityLifeCycleCallback() {
        @Override
        public void onActivityStarted(Activity activity) {
            super.onActivityStarted(activity);
            mForeActCounter++;
        }

        @Override
        public void onActivityStopped(Activity activity) {
            super.onActivityStopped(activity);
            mForeActCounter--;
            if (!isForeground()) {
                ToastUtils.showToast("应用进入后台");
            }
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            super.onActivityDestroyed(activity);
            ReferenceQueue<Activity> referenceQueue = new ReferenceQueue<>();
            WeakReference<Activity> weak = new WeakReference<Activity>(activity, referenceQueue);
            new Thread("WatchDoorDog") {
                @Override
                public void run() {
                    super.run();
                    System.out.println(Thread.currentThread().getName() + " is running");
                    while (true) {
                        try {
                            Activity act = weak.get();
                            Reference<? extends Activity> refer = referenceQueue.poll();
                            if (act == null && refer == null) {
                                return;
                            }

                            System.out.println("weak = " + act + " " + refer);
                            Thread.sleep(1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        }
    };

    /**
     * 判断应用是否在前台
     *
     * @return
     */
    public static boolean isForeground() {
        return mForeActCounter > 0;
    }

    public static class EmptyActivityLifeCycleCallback implements Application.ActivityLifecycleCallbacks {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    }
}
