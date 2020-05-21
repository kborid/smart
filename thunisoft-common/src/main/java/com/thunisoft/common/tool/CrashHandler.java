package com.thunisoft.common.tool;

import android.os.Looper;
import android.os.Process;
import com.thunisoft.common.util.CrashInfoUtils;
import com.thunisoft.common.util.ToastUtils;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * @description: crash捕获类
 * @date: 2019/6/27
 * @time: 10:04
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 */
public class CrashHandler implements UncaughtExceptionHandler {

    public static final String TAG = CrashHandler.class.getSimpleName();

    private static CrashHandler INSTANCE = new CrashHandler();

    // 系统默认的 UncaughtException 处理类
    private UncaughtExceptionHandler mDefaultHandler;

    private CrashHandler() {
    }

    /**
     * 获取 CrashHandler 实例 ,单例模式
     */
    public static CrashHandler getInstance() {
        return INSTANCE;
    }

    /**
     * 初始化
     */
    public void init() {
        // 获取系统默认的 UncaughtException 处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该 CrashHandler 为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当 UncaughtException 发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable e) {
        showCrashToast();

        if (!handleException(e) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, e);
        } else {
            // 等待处理完成
            // TODO 为什么要等？ 等什么？
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            // 退出程序
            System.exit(1);
            Process.killProcess(Process.myPid());
        }
    }

    /**
     * 自定义错误处理，收集错误信息，发送错误报告等操作均在此完成
     *
     * @param e 异常
     * @return true：如果处理了该异常信息；否则返回 false
     */
    private boolean handleException(Throwable e) {
        BundleNavi.getInstance().putInt("testKey", 123);
        BundleNavi.getInstance().getInt("testKey");
        if (null != e) {
            CrashInfoUtils.collectDeviceInfo(e);
            return true;
        }
        return false;
    }

    private void showCrashToast() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                ToastUtils.showToast("很抱歉，程序发生异常，即将退出！");
                Looper.loop();
            }
        }).start();
    }
}