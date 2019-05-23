package com.kborid.smart.activity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.kborid.library.common.MultiTaskHandler;
import com.kborid.library.common.UIHandler;
import com.kborid.library.hand2eventbus.EventBusss;
import com.kborid.library.hand2eventbus.Subscribe;
import com.kborid.library.hand2eventbus.ThreadMode;
import com.kborid.library.sample.TestSettings;
import com.kborid.library.util.LogUtils;
import com.kborid.library.util.ReflectUtil;
import com.kborid.smart.PRJApplication;
import com.kborid.smart.R;
import com.kborid.smart.event.TestEvent;
import com.kborid.smart.imageloader.PictureActivity;
import com.kborid.smart.imageloader.PictureAdapter;
import com.kborid.smart.service.SmartCounterServiceConnection;
import com.kborid.smart.test.CustomThread;
import com.kborid.smart.test.SingletonTest;
import com.kborid.smart.ui.test.TestActivity;
import com.kborid.smart.util.ToastUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends SimpleActivity {

    private SmartCounterServiceConnection counterConn = null;
    private static Drawable mDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.d("onCreate()");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        EventBusss.getDefault().register(this);
    }

    @Override
    protected boolean needStatusBarImmersive() {
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtils.d("onStart()");
        /*
         *  内存泄漏
         *  原因：mDrawable是一个静态对象，常驻内存，通过ImageView方法setImageDrawable时，会导致mDrawable引用ImageView（Drawable.setCallBack），
         *  同时，ImageView又对activity有引用，所以导致mDrawable间接引用activity，使activity无法被回收。
         */
        ImageView iv = new ImageView(this);
        mDrawable = getResources().getDrawable(R.mipmap.ic_launcher);
        iv.setImageDrawable(mDrawable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.d("onResume()");
    }

    public void onShare(View v) {
        Intent targetIntent = new Intent(Intent.ACTION_SEND);
        targetIntent.setType("text/plain");
        targetIntent.putExtra(Intent.EXTRA_TEXT, "Hello World!!!");
        targetIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        List<Intent> targetedShareIntents = new ArrayList<Intent>();
        targetedShareIntents.add(targetIntent);
        Intent resultIntent = null;
        if (targetedShareIntents.size() > 0) {
            resultIntent = Intent.createChooser(targetedShareIntents.remove(0), null);
            LabeledIntent[] li = targetedShareIntents.toArray(new LabeledIntent[targetedShareIntents.size()]);
            resultIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, li);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        try {
            startActivity(resultIntent);
        } catch (ActivityNotFoundException ex) {
            ToastUtils.showToast("Can't find share component to share");
        }
        counterConn.stopCount();
    }

    public void onTest(View v) {

    }

    public void onJump(View v) {
        startActivity(new Intent(this, FragmentContainerActivity.class));
        counterConn.pauseCount();
    }

    public void onReflect(View v) {
        reflectInvokeTest();
        counterConn.startCount();

        CustomThread customThread = new CustomThread();
        customThread.start();
        sendBroadcast(new Intent());
    }

    public void onPrintContext(View v) {
        printContextType(getBaseContext());         //Context
        printContextType(getApplicationContext());  //ContextWrapper
        printContextType(this);     //ContextThemeWrapper
        startActivity(new Intent(this, TestActivity.class));
    }

    public void onUniversal(View v) {
        Intent intent = new Intent(this, PictureActivity.class);
        intent.putExtra("imageType", PictureAdapter.TYPE_UNIVERSAL);
        startActivity(intent);
    }

    public void onPicasso(View v) {
        Intent intent = new Intent(this, PictureActivity.class);
        intent.putExtra("imageType", PictureAdapter.TYPE_PICASSO);
        startActivity(intent);
    }

    public void onGlide(View v) {
        Intent intent = new Intent(this, PictureActivity.class);
        intent.putExtra("imageType", PictureAdapter.TYPE_GLIDE);
        startActivity(intent);
    }

    private void printContextType(Context context) {
        outputConsoleTextView("Type is [" + context + "]");
        outputConsoleTextView("It is Context!");
        if (context instanceof ContextWrapper) {
            outputConsoleTextView("It is ContextWrapper too!");
        }
        if (context instanceof ContextThemeWrapper) {
            outputConsoleTextView("It is ContextThemeWrapper too!");
        }
    }

    private void idleHandler() {
        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                outputConsoleTextView("IdleHandler queueIdle()");
                return false;
            }
        });

        UIHandler.post(new Runnable() {
            @Override
            public void run() {
                outputConsoleTextView("normal handler post");
            }
        });

        UIHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                outputConsoleTextView("delay 200 handler post");
            }
        }, 200);
    }

    public void onWebView(View v) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra("from", "main");
        startActivity(intent);
    }

    private void printTest() {
        TestSettings.instance.reset();
        outputConsoleTextView("\nnothing set===========================");
        TestSettings.instance.getFlag();
        outputConsoleTextView("isFlagOne = " + TestSettings.instance.isFlagOne());
        outputConsoleTextView("isFlagTwo = " + TestSettings.instance.isFlagTwo());
        outputConsoleTextView("isFlagThr = " + TestSettings.instance.isFlagThr());

        outputConsoleTextView("\none set===========================");
        TestSettings.instance.setSettingOne(true);
        TestSettings.instance.getFlag();
        outputConsoleTextView("isFlagOne = " + TestSettings.instance.isFlagOne());
        outputConsoleTextView("isFlagTwo = " + TestSettings.instance.isFlagTwo());
        outputConsoleTextView("isFlagThr = " + TestSettings.instance.isFlagThr());

        outputConsoleTextView("\none two set===========================");
        TestSettings.instance.setSettingTwo(true);
        TestSettings.instance.getFlag();
        outputConsoleTextView("isFlagOne = " + TestSettings.instance.isFlagOne());
        outputConsoleTextView("isFlagTwo = " + TestSettings.instance.isFlagTwo());
        outputConsoleTextView("isFlagThr = " + TestSettings.instance.isFlagThr());

        outputConsoleTextView("\none two thr set===========================");
        TestSettings.instance.setSettingThr(true);
        TestSettings.instance.getFlag();
        outputConsoleTextView("isFlagOne = " + TestSettings.instance.isFlagOne());
        outputConsoleTextView("isFlagTwo = " + TestSettings.instance.isFlagTwo());
        outputConsoleTextView("isFlagThr = " + TestSettings.instance.isFlagThr());

        outputConsoleTextView("lists = " + TestSettings.instance.getTestList());

        outputConsoleTextView(getResources().getQuantityString(R.plurals.test_plurals, 0, 0));
        outputConsoleTextView(getResources().getQuantityString(R.plurals.test_plurals, 1, 1));
        outputConsoleTextView(getResources().getQuantityString(R.plurals.test_plurals, 2, 2));
        outputConsoleTextView(getResources().getQuantityString(R.plurals.test_plurals, 3, 3));
        outputConsoleTextView(getResources().getQuantityString(R.plurals.test_plurals, 4, 4));
    }

    private void bindServiceInner() {
        if (null == counterConn) {
            counterConn = new SmartCounterServiceConnection();
        }
        counterConn.bindService();
        counterConn.setCountChangedListener(countChangedListener);
    }

    private void unBindServiceInner() {
        if (null != counterConn) {
            counterConn.unBindService();
            counterConn = null;
        }
    }

    private SmartCounterServiceConnection.CountChangedListener countChangedListener = new SmartCounterServiceConnection.CountChangedListener() {
        @Override
        public void onCountChanged(int value) {
            ((TextView) findViewById(R.id.tv_counter)).setText(String.valueOf(value));
        }
    };

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void getMessage(TestEvent testEvent) {
        LogUtils.d("==>thread", "recv:" + Thread.currentThread().getName());
        LogUtils.d("==>recv", testEvent.toString());
    }

    @Subscribe
    public void getMessage1(TestEvent testEvent) {
        LogUtils.d("==>thread", "recv:" + Thread.currentThread().getName());
        LogUtils.d("==>recv", testEvent.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.d("onDestroy()");
        unBindServiceInner();
        EventBusss.getDefault().unRegister(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        printTest();
        bindServiceInner();
    }

    private void reflectInvokeTest() {
        MultiTaskHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
//                    Class<?> clazz = Class.forName("com.kborid.smart.PRJApplication"); // 1
//                    Class<?> clazz = PRJApplication.class; // 2
                    Class<?> clazz = PRJApplication.getInstance().getClass(); // 3
                    Object obj = clazz.newInstance();
                    Method method = clazz.getDeclaredMethod("testReflect");
                    method.setAccessible(true);
                    method.invoke(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String className = "com.kborid.smart.PRJApplication";
                int ret = (int) ReflectUtil.invokeStaticMethod(className, "testReflect1", null, null);
                System.out.println(String.valueOf(ret));
                ReflectUtil.invokeMethod(className, "testReflect", null, null);
            }
        });
    }

    public static void main(String[] arg) {
        String className = "com.kborid.smart.PRJApplication";
        int ret = (int) ReflectUtil.invokeStaticMethod(className, "testReflect1", null, null);
        System.out.println(String.valueOf(ret));
        ReflectUtil.invokeMethod(className, "testReflect", null, null);
    }

    private void outputConsoleTextView(String string) {
        LogUtils.d("outputConsoleTextView() " + string);
    }

    public static void scroll2Bottom(final ScrollView scroll, final View inner) {
        UIHandler.post(new Runnable() {
            @Override
            public void run() {
                if (scroll == null || inner == null) {
                    return;
                }
                // 内层高度超过外层
                int offset = inner.getMeasuredHeight() - scroll.getMeasuredHeight();
                if (offset < 0) {
                    offset = 0;
                }
                Thread.yield();
                String name = SingletonTest.Singleton5.instance.getTestName();
                SingletonTest.Singleton4.getInstance();
                scroll.smoothScrollTo(0, offset);
            }
        });
    }
}
