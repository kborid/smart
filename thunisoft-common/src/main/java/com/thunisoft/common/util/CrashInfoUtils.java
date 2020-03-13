package com.thunisoft.common.util;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;

import com.orhanobut.logger.Logger;
import com.thunisoft.common.ThunisoftCommon;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: crash信息收集工具类
 * @date: 2019/8/1
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 * @version: 1.0.0
 */
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
            String filePath = "";
            String fileTime = DateUtils.formatDate("yyyy-MM-dd", date);
            String fileName = "crash-" + fileTime + ".log";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                filePath = Environment.getExternalStorageDirectory().getAbsolutePath()
                        + File.separator + "thunisoft"
                        + File.separator + ThunisoftCommon.getContext().getPackageName()
                        + File.separator + "crash";
            } else {
                filePath = ThunisoftCommon.getContext().getFilesDir().getAbsolutePath()
                        + File.separator + "crash";
            }

            File dir = new File(filePath);
            Logger.t(TAG).i(dir.getAbsolutePath());
            if (!dir.exists()) {
                dir.mkdirs();
            }
            outStream = new FileOutputStream(dir.getAbsolutePath() + File.separator + fileName, true);
            outStream.write(sb.toString().getBytes());
            return fileName;
        } catch (FileNotFoundException e) {
            Logger.t(TAG).e(e, "an error occurred while writing file...");
            //如果是因为没有文件写入权限可能会引起递归收集错误日志，所以就不记录了
//            PackageUtils.collectDeviceInfo(PRJApplication.getInstance(),e);
        } catch (Exception e1) {
            Logger.t(TAG).e(e1, "an error occurred while writing file...");
            CrashInfoUtils.collectDeviceInfo(e1);
        } finally {
            if (outStream != null) {
                try {
                    outStream.close();
                } catch (IOException e) {
                    Logger.t(TAG).e(e, "an error occurred");
                }
            }
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
            PackageManager pm = ThunisoftCommon.getContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ThunisoftCommon.getContext().getPackageName(), PackageManager.GET_ACTIVITIES);

            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Logger.t(TAG).e(e, "an error occurred when collect package info");
            CrashInfoUtils.collectDeviceInfo(e);
        }

        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
//                Logger.t(TAG).i(field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                Logger.t(TAG).e(e, "an error occurred when collect crash info");
                CrashInfoUtils.collectDeviceInfo(e);
            }
        }
        saveCrashInfo2File(throwable, infos);
    }
}
