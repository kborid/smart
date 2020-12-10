package com.kborid.library.util;

import android.content.Intent;
import android.net.Uri;

import com.kborid.library.base.BaseApplication;
import com.orhanobut.logger.Logger;
import com.thunisoft.common.util.CloseUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;


public class InstallUtil {

    private void installSystem(String filePath) {
        Logger.t("down").d("install()");
        File apkFile = new File(filePath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        BaseApplication.getInstance().startActivity(intent);
    }

    public static String installSilent(String path) {
        LogUtils.d("install:" + path);
        String[] args = {"pm", "install", "-r", path};
        return execCommand(args);
    }

    public static String uninstallSilent(String pkgName) {
        LogUtils.d("uninstall:" + pkgName);
        String[] args = {"pm", "uninstall", pkgName};
        return execCommand(args);
    }

    private static String execCommand(String... command) {
        LogUtils.d("execCommand() command = " + Arrays.toString(command));
        Process process = null;
        InputStream errIs = null;
        InputStream inIs = null;
        String result = "";

        try {
//            process = Runtime.getRuntime().exec("su");
            process = new ProcessBuilder().command(command).start();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int read = -1;
            errIs = process.getErrorStream();
            while ((read = errIs.read()) != -1) {
                baos.write(read);
            }
            inIs = process.getInputStream();
            while ((read = inIs.read()) != -1) {
                baos.write(read);
            }
            result = new String(baos.toByteArray());
            LogUtils.d("result = " + result);
            process.destroy();
        } catch (IOException e) {
            result = e.getMessage();
        } finally {
            CloseUtil.closeQuietly(inIs);
            CloseUtil.closeQuietly(errIs);
        }
        return result;
    }
}
