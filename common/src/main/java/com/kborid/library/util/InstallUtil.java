package com.kborid.library.util;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;


/**
 * Created by Hal on 2016/11/1.
 */

public class InstallUtil {

    /**
     * 静默安装
     * 需要声明权限 INSTALL_PACKAGES，并打上系统签名
     */
    public static String installSilently(String path) {
        File apkFile = new File(path);
        apkFile.setWritable(true, false);
        apkFile.setReadable(true, false);
        Log.i("InstallUtil", "install apk " + path + ", exist " + apkFile.exists());
        // 通过命令行来安装APK
        String[] args = { "pm", "install", "-r", path };
        String result = "";
        // 创建一个操作系统进程并执行命令行操作
        ProcessBuilder processBuilder = new ProcessBuilder(args);
        Process process = null;
        InputStream errIs = null;
        InputStream inIs = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int read = -1;
            process = processBuilder.start();
            errIs = process.getErrorStream();
            while ((read = errIs.read()) != -1) {
                baos.write(read);
            }
            baos.write('\n');
            inIs = process.getInputStream();
            while ((read = inIs.read()) != -1) {
                baos.write(read);
            }
            byte[] data = baos.toByteArray();
            result = new String(data);
            Log.i("InstallUtil", "install result:" + result);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (errIs != null) {
                    errIs.close();
                }
                if (inIs != null) {
                    inIs.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (process != null) {
                process.destroy();
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String path = "/sdcard" + File.separator
                + "Download" + File.separator + "JumaVideo1111_1.apk";
//        path = "/data" + File.separator
//                + "media" + File.separator
//                + "0" + File.separator
//                + "Download" + File.separator;
        installSilently(path);
    }
}
