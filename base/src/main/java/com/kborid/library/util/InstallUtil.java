package com.kborid.library.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;


public class InstallUtil {

    public static String install(String path) {
        LogUtils.d("install:" + path);
        String[] args = {"pm", "install", "-r", path};
        return execCommand(args);
    }

    public static String uninstall(String pkgName) {
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
            if (inIs != null)
                inIs.close();
            if (errIs != null)
                errIs.close();
            process.destroy();
        } catch (IOException e) {
            result = e.getMessage();
        }
        return result;
    }
}
