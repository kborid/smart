package com.kborid.library.util;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.kborid.library.base.BaseApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CrashInfoUtils {

    private static final String TAG = CrashInfoUtils.class.getSimpleName();

    /**
     * 保存错误信息到文件中
     * *
     *
     * @param ex
     * @param infos
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private static String saveCrashInfo2File(Throwable ex, Map<String, String> infos) {
        Date date = new Date();
        String time4Log = DateUtils.formatDate("======yyyy-MM-dd HH:mm:ss======", date);
        StringBuffer sb = new StringBuffer();
        String lineSeparator = System.getProperty("line.separator", "\n");
        sb.append(lineSeparator + lineSeparator + time4Log).append(lineSeparator + lineSeparator);
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();

        String result = writer.toString();
        sb.append(result);
        FileOutputStream outStream = null;
        try {
            String time = DateUtils.formatDate("yyyy-MM-dd", date);
            String fileName = "crash-" + time + ".log";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String path = BaseApplication.getInstance().getExternalCacheDir().getPath() + File.separatorChar + "crash";
                File dir = new File(path);
                Log.i(TAG, dir.getAbsolutePath());
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                outStream = new FileOutputStream(dir.getAbsolutePath() + File.separator + fileName, true);
                outStream.write(sb.toString().getBytes());
            }

            return fileName;
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file...", e);
            CrashInfoUtils.collectDeviceInfo(e);
        } finally {
            IOUtils.closeQuietly(outStream);
        }

        return null;
    }

    /**
     * 收集设备参数信息
     */
    public static void collectDeviceInfo(Throwable throwable) {
        // 用来存储设备信息和异常信息
        Map<String, String> infos = new HashMap<String, String>();
        try {
            PackageManager pm = BaseApplication.getInstance().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(BaseApplication.getInstance().getPackageName(), PackageManager.GET_ACTIVITIES);

            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "an error occured when collect package info", e);
            CrashInfoUtils.collectDeviceInfo(e);
        }

        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
                Log.d(TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                Log.e(TAG, "an error occured when collect crash info", e);
                CrashInfoUtils.collectDeviceInfo(e);
            }
        }
        saveCrashInfo2File(throwable, infos);
    }
}
