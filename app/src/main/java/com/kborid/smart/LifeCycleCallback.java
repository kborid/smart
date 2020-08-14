package com.kborid.smart;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.kborid.library.util.LogUtils;
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
            WeakReference<Activity> weak = new WeakReference<>(activity, referenceQueue);
            new Thread("WatchDoorDog") {
                @Override
                public void run() {
                    super.run();
                    System.out.println(Thread.currentThread().getName() + " is running");
                    while (true) {
                        try {
                            Thread.sleep(30000);
                            // 1 30s之后进行第一次手动gc
                            System.out.println("doing 1 gc");
                            System.gc();

                            // 2 获取Act是否在引用队列中，如果在，则说明被回收；如果不在，则进行第二次手动gc
                            Thread.sleep(5000);
                            Reference<? extends Activity> refer = referenceQueue.poll();
                            // 3 第二次手动gc
                            if (refer == null) {
                                System.out.println("doing 2 gc");
                                System.gc();

                                Thread.sleep(5000);

                                refer = referenceQueue.poll();
                                if (refer == null) {
                                    System.out.println("leaked?");
                                    continue;
                                }
                            }
                            Activity act = weak.get();
                            System.out.println("weak = " + act + " " + refer);
                        } catch (Exception e) {
                            LogUtils.e(e);
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
