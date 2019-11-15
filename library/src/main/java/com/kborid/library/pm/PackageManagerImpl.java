package com.kborid.library.pm;

import android.content.Context;
import android.content.pm.IPackageDeleteObserver;
import android.content.pm.IPackageInstallObserver;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.RemoteException;
import android.text.TextUtils;

import com.thunisoft.common.util.ReflectUtil;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PackageManagerImpl {
    private static IPackageCallback mPackageCallback;
    private static PackageDeleteObserver packageDeleteObserver;
    private static PackageInstallObserver packageInstallObserver;
    private static PackageManager packageManager;
    private static Method installMethod, unInstallMethod;
    private static Class<?>[] installParamClazz;
    private static Class<?>[] unInstallParamClazz;

    public static void init(Context context) {
        packageManager = context.getPackageManager();
        packageDeleteObserver = new PackageDeleteObserver();
        packageInstallObserver = new PackageInstallObserver();
        installParamClazz = new Class[]{Uri.class, IPackageInstallObserver.class, int.class, String.class};
        unInstallParamClazz = new Class[]{String.class, IPackageDeleteObserver.class, int.class};

        try {
            installMethod = packageManager.getClass().getMethod("installPackage", installParamClazz);
            unInstallMethod = packageManager.getClass().getMethod("deletePackage", unInstallParamClazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void uninstallPackage(String pkgName, IPackageCallback callback) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        mPackageCallback = callback;
//        unInstallMethod.invoke(packageManager, pkgName, packageDeleteObserver, 0);
        ReflectUtil.invokeMethod(packageManager.getClass().getName(),
                "deletePackage",
                unInstallParamClazz,
                new Object[]{pkgName, packageDeleteObserver, 0});
    }

    public static void installPackage(String apkPath, IPackageCallback callback) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        if (!TextUtils.isEmpty(apkPath)) {
            installPackage(new File(apkPath), callback);
        }
    }

    public static void installPackage(File apkFile, IPackageCallback callback) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        if (apkFile.exists()) {
            installPackage(Uri.fromFile(apkFile), callback);
        }
    }

    public static void installPackage(Uri apkFile, IPackageCallback callback) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        mPackageCallback = callback;
//        installMethod.invoke(packageManager, apkFile, packageInstallObserver, 2, null);
        ReflectUtil.invokeMethod(packageManager.getClass().getName(),
                "installPackage",
                installParamClazz,
                new Object[]{apkFile, packageInstallObserver, 2, null});
    }

    static class PackageDeleteObserver extends IPackageDeleteObserver.Stub {

        @Override
        public void packageDeleted(String packageName, int returnCode) throws RemoteException {
            if (null != mPackageCallback) {
                if (returnCode != 1) {
                    mPackageCallback.fail(packageName, returnCode);
                } else {
                    mPackageCallback.success(packageName);
                }
            }
        }
    }

    static class PackageInstallObserver extends IPackageInstallObserver.Stub {
        @Override
        public void packageInstalled(String packageName, int returnCode) throws RemoteException {
            if (null != mPackageCallback) {
                if (returnCode != 1) {
                    mPackageCallback.fail(packageName, returnCode);
                } else {
                    mPackageCallback.success(packageName);
                }
            }
        }
    }
}
