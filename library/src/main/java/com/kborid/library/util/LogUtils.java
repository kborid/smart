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
        if (isDebug) {
            TagInfo tagInfo = getMethodNames(new Throwable().getStackTrace());
            msg = createLogWithoutFileName(tagInfo, msg);
            Log.i(TAG, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (isDebug) {
            TagInfo tagInfo = getMethodNames(new Throwable().getStackTrace());
            msg = createLogWithoutFileName(tagInfo, msg);
            Log.i(tag, msg);
        }
    }

    public static void d(String msg) {
        if (isDebug) {
            TagInfo tagInfo = getMethodNames(new Throwable().getStackTrace());
            msg = createLogWithoutFileName(tagInfo, msg);
            Log.d(TAG, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (isDebug) {
            TagInfo tagInfo = getMethodNames(new Throwable().getStackTrace());
            msg = createLogWithoutFileName(tagInfo, msg);
            Log.d(tag, msg);
        }
    }

    public static void e(String msg) {
        if (isDebug) {
            TagInfo tagInfo = getMethodNames(new Throwable().getStackTrace());
            msg = createLogWithoutFileName(tagInfo, msg);
            Log.e(TAG, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (isDebug) {
            TagInfo tagInfo = getMethodNames(new Throwable().getStackTrace());
            msg = createLogWithoutFileName(tagInfo, msg);
            Log.e(tag, msg);
        }
    }

    public static void e(Throwable e) {
        String msg = e.getClass().getName() + " : " + e.getMessage();
        TagInfo tagInfo = getMethodNames(new Throwable().getStackTrace());
        msg = createLogWithoutFileName(tagInfo, msg);
        Log.e(TAG, msg, e);
    }

    public static void e(String tag, Throwable e) {
        String msg = e.getClass().getName() + " : " + e.getMessage();
        TagInfo tagInfo = getMethodNames(new Throwable().getStackTrace());
        msg = createLogWithoutFileName(tagInfo, msg);
        Log.e(tag, msg, e);
    }

    public static void w(String msg) {
        if (isDebug) {
            TagInfo tagInfo = getMethodNames(new Throwable().getStackTrace());
            msg = createLogWithoutFileName(tagInfo, msg);
            Log.w(TAG, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (isDebug) {
            TagInfo tagInfo = getMethodNames(new Throwable().getStackTrace());
            msg = createLogWithoutFileName(tagInfo, msg);
            Log.w(tag, msg);
        }
    }

    private static String createLogWithoutFileName(TagInfo tagInfo, String log, Object... args) {
        StringBuilder buffer = new StringBuilder();
        buffer.append("[");
        buffer.append(tagInfo.fileName);
        buffer.append("(line:").append(tagInfo.lineNumber).append(")").append(" ");
        buffer.append(tagInfo.methodName).append("()");
        buffer.append("]");
        buffer.append(" ");
        buffer.append(formatString(log, args));
        return buffer.toString();
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
