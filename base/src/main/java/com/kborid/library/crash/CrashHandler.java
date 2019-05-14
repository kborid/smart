package com.kborid.library.crash;

import android.content.Context;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

import com.kborid.library.base.BaseApplication;
import com.kborid.library.common.UIHandler;
import com.kborid.library.util.CrashInfoUtils;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * Created by kborid on 2016/10/27.
 * <p>
 * UncaughtException处理类,当程序发生Uncaught异常的时候,有该类来接管程序,并记录发送错误报告.
 * </p>
 *
 * @author kborid
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
     *
     * @param context
     */
    public void init(Context context) {
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
        if (!handleException(e) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, e);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                Log.e(TAG, "error : ", ex);
                CrashInfoUtils.collectDeviceInfo(ex);
            }

            // 退出程序
            Process.killProcess(Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 自定义错误处理，收集错误信息，发送错误报告等操作均在此完成
     *
     * @param e
     * @return true：如果处理了该异常信息；否则返回 false
     */
    private boolean handleException(Throwable e) {
        if (e == null) {
            return false;
        }

        UIHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseApplication.getInstance(), "很抱歉，程序出现异常，即将退出。", Toast.LENGTH_LONG).show();
            }
        });
        CrashInfoUtils.collectDeviceInfo(e);
        return true;
    }

}