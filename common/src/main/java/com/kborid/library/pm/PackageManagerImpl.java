package com.kborid.library.pm;

import android.content.Context;
import android.content.pm.IPackageDeleteObserver;
import android.content.pm.IPackageInstallObserver;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.RemoteException;
import android.text.TextUtils;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PackageManagerImpl {
    private IPackageCallback mPackageCallback;
    private PackageDeleteObserver packageDeleteObserver;
    private PackageInstallObserver packageInstallObserver;
    private static PackageManager packageManager;
    private Method installMethod, unInstallMethod;

    private static PackageManagerImpl instance;

    public static PackageManagerImpl getInstance() {
        if (null == instance) {
            instance = new PackageManagerImpl();
        }
        return instance;
    }

    public static void init(Context context) {
        packageManager = context.getPackageManager();
    }

    private PackageManagerImpl() {
        this.packageDeleteObserver = new PackageDeleteObserver();
        this.packageInstallObserver = new PackageInstallObserver();
        Class<?>[] installParamClazz = new Class[]{Uri.class, IPackageInstallObserver.class, int.class, String.class};
        Class<?>[] unInstallParamClazz = new Class[]{String.class, IPackageDeleteObserver.class, int.class};

        try {
            installMethod = packageManager.getClass().getMethod("installPackage", installParamClazz);
            unInstallMethod = packageManager.getClass().getMethod("deletePackage", unInstallParamClazz);
//            installMethod = PackageManager.class.getDeclaredMethod("installPackage", installParamClazz);
//            unInstallMethod = PackageManager.class.getDeclaredMethod("deletePackage", unInstallParamClazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void registerPackageCallback(IPackageCallback callback) {
        this.mPackageCallback = callback;
    }

    public void unRegisterPackageCallback() {
        this.mPackageCallback = null;
    }

    public void uninstallPackage(String pkgName) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        unInstallMethod.invoke(packageManager, new Object[]{pkgName, packageDeleteObserver, 0});
    }

    public void installPackage(String apkPath) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        if (!TextUtils.isEmpty(apkPath)) {
            installPackage(new File(apkPath));
        }
    }

    public void installPackage(File apkFile) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        if (apkFile.exists()) {
            installPackage(Uri.fromFile(apkFile));
        }
    }

    public void installPackage(Uri apkFile) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        installMethod.invoke(packageManager, new Object[]{apkFile, packageInstallObserver, 2, null});
    }

    class PackageDeleteObserver extends IPackageDeleteObserver.Stub {

        @Override
        public void packageDeleted(String packageName, int returnCode) throws RemoteException {
            if (null != mPackageCallback) {
                mPackageCallback.deletePackage(packageName, returnCode);
            }
        }
    }

    class PackageInstallObserver extends IPackageInstallObserver.Stub {
        @Override
        public void packageInstalled(String packageName, int returnCode) throws RemoteException {
            if (null != mPackageCallback) {
                mPackageCallback.installPackage(packageName, returnCode);
            }
        }
    }
}
