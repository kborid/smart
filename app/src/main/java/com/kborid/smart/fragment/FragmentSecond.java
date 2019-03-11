package com.kborid.smart.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.kborid.library.common.UIHandler;
import com.kborid.library.pm.IPackageCallback;
import com.kborid.library.pm.PackageManagerImpl;
import com.kborid.smart.R;
import com.kborid.smart.test.AExecuteAsRoot;
import com.kborid.smart.util.ToastUtils;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.orhanobut.logger.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentSecond extends Fragment {

    private String url = "http://gdown.baidu.com/data/wisegame/d4524b42d5a5ccf7/baidujisuban_20514176.apk";
    private String mDirStr, mFileStr;

    @BindView(R.id.process)
    ProgressBar progressBar;

    public static Fragment newInstance() {
        return new FragmentSecond();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_second, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mDirStr = getActivity().getExternalCacheDir().getPath() + File.separator;
        mFileStr = "ss.apk";
        PackageManagerImpl.getInstance().registerPackageCallback(callback);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        PackageManagerImpl.getInstance().unRegisterPackageCallback();
    }

    @OnClick(R.id.btn_click1)
    void click1() {
        Logger.t("down").d("path:" + mDirStr + mFileStr);
        FileDownloader.getImpl().create(url)
                .setPath(mDirStr + mFileStr)
                .setListener(mFileDownloadListener)
                .start();
        ToastUtils.showToast("开始下载");
    }

    @OnClick(R.id.btn_click2)
    void click2() {
        FileDownloader.getImpl().pauseAll();
        ToastUtils.showToast("暂停下载");
    }

    @OnClick(R.id.btn_click3)
    void click3() {
        if (!new File(mDirStr + mFileStr).exists()) {
            ToastUtils.showToast("apk不存在");
            return;
        }
//        MultiTaskHandler.post(new Runnable() {
//            @Override
//            public void run() {
//                InstallUtil.install(mDirStr + mFileStr);
//            }
//        });
        try {
            PackageManagerImpl.getInstance().installPackage(mDirStr + mFileStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private IPackageCallback callback = new IPackageCallback() {
        @Override
        public void installPackage(final String pkgName, int code) {
            UIHandler.post(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.showToast("install " + pkgName + " success");
                }
            });
        }

        @Override
        public void deletePackage(final String pkgName, int code) {
            UIHandler.post(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.showToast("delete " + pkgName + " success");
                }
            });
        }
    };

    private void doSU() {
        Process process = null;
        DataOutputStream os = null;
        DataInputStream is = null;
        try {
            process = Runtime.getRuntime().exec("sh"); /*这里可能需要修改su的源代码 （注掉  if (myuid != AID_ROOT && myuid != AID_SHELL) {*/
            os = new DataOutputStream(process.getOutputStream());
            is = new DataInputStream(process.getInputStream());
            os.writeBytes("cp /storage/emulated/0/Android/data/com.kborid.smart/cache/ss.apk /system/app/");
            os.writeBytes("exit \n");
            os.flush();
            process.waitFor();
            //如果已经root，但是用户选择拒绝授权,e.getMessage() = write failed: EPIPE (Broken pipe)
            //如果没有root，,e.getMessage()= Error running exec(). Command: [su] Working Directory: null Environment: null
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (is != null) {
                    is.close();
                }
                if (null != process) {
                    process.destroy();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class ExecuteAsRoot extends AExecuteAsRoot {

        @Override
        protected ArrayList<String> getCommandsToExecute() {
            ArrayList<String> list = new ArrayList<String>();
//            list.add("add kill-server");
//            list.add("adb devices");
//            list.add("ls -l");
            list.add("mount -o remount /system");
            list.add("cp /storage/emulated/0/Android/data/com.kborid.smart/cache/ss.apk /system/app");
            list.add("cp /storage/emulated/0/Android/data/com.kborid.smart/cache/ss.apk /storage/emulated/0/Android/data/com.kborid.smart/cache/ss111.apk");
            return list;
        }
    }

    @OnClick(R.id.btn_click4)
    void click4() {
        ToastUtils.showToast("doSU");
//        doSU();
        try {
            Log.d("ROOT", "result:" + new ExecuteAsRoot().execute());
        } catch (Exception e) {
            e.printStackTrace();
        }
//        ToastUtils.showToast("222222");
//        try {
//            PackageManagerImpl.getInstance().uninstallPackage("com.baidu.searchbox.lite");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (!new File(mDirStr + mFileStr).exists()) {
//            ToastUtils.showToast("apk不存在");
//            return;
//        }
//        new File(mDirStr + mFileStr).delete();
//        ToastUtils.showToast("apk已删除");
    }

    @OnClick(R.id.btn_click5)
    void click5() {
        ToastUtils.showToast("begin uninstall jumaLauncher");
        try {
            PackageManagerImpl.getInstance().uninstallPackage("com.juma.jumalauncher.horizontal");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void install(String filePath) {
        Logger.t("down").d("install()");
        File apkFile = new File(filePath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        startActivity(intent);
    }

    private FileDownloadListener mFileDownloadListener = new FileDownloadListener() {
        @Override
        protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            Logger.t("down").d("pending soFarBytes:" + soFarBytes + ", totalBytes:" + totalBytes);
            int progress = (int) ((float) soFarBytes / totalBytes * 100);
            Logger.t("down").d("progress progress:" + progress);
            progressBar.setProgress(progress);
        }

        @Override
        protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            Logger.t("down").d("progress soFarBytes:" + soFarBytes + ", totalBytes:" + totalBytes);
            int progress = (int) ((float) soFarBytes / totalBytes * 100);
            Logger.t("down").d("progress progress:" + progress);
            progressBar.setProgress(progress);
        }

        @Override
        protected void completed(BaseDownloadTask task) {
            Logger.t("down").d("completed");
            progressBar.setProgress(100);
            Logger.t("down").d("path = " + task.getPath());
            Logger.t("down").d("name = " + task.getFilename());
            ToastUtils.showToast("apk下载完成");
        }

        @Override
        protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            Logger.t("down").d("paused soFarBytes:" + soFarBytes + ", totalBytes:" + totalBytes);
        }

        @Override
        protected void error(BaseDownloadTask task, Throwable e) {
            Logger.t("down").d("error");
            e.printStackTrace();
        }

        @Override
        protected void warn(BaseDownloadTask task) {
            Logger.t("down").d("warn");
        }
    };
}
