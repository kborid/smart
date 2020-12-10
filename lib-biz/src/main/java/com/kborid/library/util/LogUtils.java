package com.kborid.library.util;

import android.util.Log;

import com.kborid.library.BuildConfig;

public class LogUtils {
    private static final boolean isDebug = BuildConfig.DEBUG;
    private static final String TAG = "DWSmart";

    public static void init() {
        Log.i(TAG, "init() debug=" + isDebug);
    }

    public static void i(String msg) {
        i(TAG, msg);
    }

    public static void i(String tag, String msg) {
        if (isDebug) {
            TagInfo tagInfo = getMethodNames(new Throwable().getStackTrace());
            msg = createLogWithoutFileName(tagInfo, msg);
            Log.i(tag, msg);
        }
    }

    public static void d(String msg) {
        d(TAG, msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug) {
            TagInfo tagInfo = getMethodNames(new Throwable().getStackTrace());
            msg = createLogWithoutFileName(tagInfo, msg);
            Log.d(tag, msg);
        }
    }

    public static void e(Throwable e) {
        e(TAG, e);
    }

    public static void e(String tag, Throwable e) {
        e(tag, e.getClass().getName() + " : " + e.getMessage(), e);
    }

    public static void e(String tag, String msg, Throwable throwable) {
        if (isDebug) {
            TagInfo tagInfo = getMethodNames(new Throwable().getStackTrace());
            msg = createLogWithoutFileName(tagInfo, msg);
            Log.e(tag, msg, throwable);
        }
    }

    public static void w(String msg) {
        w(TAG, msg);
    }

    public static void w(String tag, String msg) {
        if (isDebug) {
            TagInfo tagInfo = getMethodNames(new Throwable().getStackTrace());
            msg = createLogWithoutFileName(tagInfo, msg);
            Log.w(tag, msg);
        }
    }

    private static String createLogWithoutFileName(TagInfo tagInfo, String log, Object... args) {
        return "[" +
                tagInfo.fileName +
                "(line:" + tagInfo.lineNumber + ")" + " " +
                tagInfo.methodName + "()" +
                "]" +
                " " +
                formatString(log, args);
    }

    private static TagInfo getMethodNames(StackTraceElement[] sElements) {
        TagInfo info = new TagInfo();
        if (sElements.length > 1) {
            info.fileName = sElements[1].getFileName();
//            if (info.fileName.endsWith(".java")) {
//                info.fileName = info.fileName.substring(0, info.fileName.length() - 5);
//            }
            info.methodName = sElements[1].getMethodName();
            info.lineNumber = sElements[1].getLineNumber();
        }
        return info;
    }

    private static String formatString(String message, Object... args) {
        return args.length == 0 ? message : String.format(message, args);
    }

    static class TagInfo {
        String fileName;
        String methodName;
        int lineNumber;
    }
}
