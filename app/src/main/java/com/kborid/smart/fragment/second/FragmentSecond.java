package com.kborid.smart.fragment.second;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.alibaba.fastjson.JSONObject;
import com.kborid.library.common.UIHandler;
import com.kborid.library.pm.IPackageCallback;
import com.kborid.library.pm.PackageManagerImpl;
import com.kborid.smart.BuildConfig;
import com.kborid.smart.R;
import com.kborid.smart.network.Api;
import com.kborid.smart.network.AppRequestBean;
import com.kborid.smart.test.AExecuteAsRoot;
import com.kborid.smart.util.ToastUtils;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class FragmentSecond extends Fragment {

    private static final String TAG = FragmentSecond.class.getSimpleName();
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
        mFileStr = "ss%1$d.apk";
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @OnClick(R.id.btn_click1)
    void click1() {
        Api.getUpdatesInfo(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {
                Logger.t(TAG).d("onSubscribe()");
            }

            @Override
            public void onNext(Object o) {
                Logger.t(TAG).d("onNext() o = " + o);
            }

            @Override
            public void onError(Throwable e) {
                Logger.t(TAG).d("onError()");
            }

            @Override
            public void onComplete() {
                Logger.t(TAG).d("onComplete()");
            }
        });
//        Logger.t("down").d("path:" + mDirStr + mFileStr);
//        FileDownloader.getImpl().create(url)
//                .setPath(mDirStr + mFileStr)
//                .setListener(mFileDownloadListener)
//                .start();
//        ToastUtils.showToast("开始下载");
//        FileDownloadQueueSet queueSet = new FileDownloadQueueSet(mFileDownloadListener);
//        List<BaseDownloadTask> tasks = new ArrayList<>();
//        for (int i = 0; i < 4; i++) {
//            int id = FileDownloadUtils.generateId(url, mDirStr + String.format(mFileStr, i));
//            Logger.t("duan").d("generate id = " + id);
//            BaseDownloadTask task = FileDownloader.getImpl().create(url).setPath(mDirStr + String.format(mFileStr, i)).setTag(i + 1);
//            Logger.t("duan").d("id = " + task.getId());
//            tasks.add(task);
//        }
//        queueSet.downloadTogether(tasks);
//        queueSet.start();
//        for (int i = 0; i < 3; i++) {
//            BaseDownloadTask task = FileDownloader.getImpl().create(url)
//                    .setListener(mFileDownloadListener)
//                    .setPath(mDirStr + String.format(mFileStr, i))
//                    .setTag(i + 1);
//            Logger.t("duan").d("id = " + task.getId());
//            task.start();
//        }
    }

    @OnClick(R.id.btn_click2)
    void click2() {
//        FileDownloader.getImpl().pauseAll();
//        ToastUtils.showToast("暂停下载");
        AppRequestBean bean = new AppRequestBean();
        bean.setPkgName("com.baidu.map");
        bean.setVersionName(BuildConfig.VERSION_NAME);
        bean.setVersionCode(105);

        Api.getUpdateInfo(bean, new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {
                Logger.t(TAG).d("onSubscribe()");
            }

            @Override
            public void onNext(Object o) {
                Logger.t(TAG).d("onNext() o = " + o);
                JSONObject json = (JSONObject) o;
                if (json.containsKey("path")) {
                    String path = json.getString("path");
                    Logger.t(TAG).d("path = "  + path);
//                    FileDownloader.getImpl().create(path).setPath(mDirStr+mFileStr).setListener(mFileDownloadListener).start();
                }
            }

            @Override
            public void onError(Throwable e) {
                Logger.t(TAG).d("onError()");
            }

            @Override
            public void onComplete() {
                Logger.t(TAG).d("onComplete()");
            }
        });
    }

    @OnClick(R.id.btn_click3)
    void click3() {
//        if (!new File(mDirStr + "ss.apk").exists()) {
//            ToastUtils.showToast("apk不存在");
//            return;
//        }
//        try {
//            PackageManagerImpl.installPackage(mDirStr + "ss.apk", callback);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    private IPackageCallback callback = new IPackageCallback() {
        @Override
        public void success(final String pkgName) {
            UIHandler.post(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.showToast(pkgName + " success");
                }
            });
        }

        @Override
        public void fail(final String pkgName, int code) {
            UIHandler.post(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.showToast(pkgName + " fail");
                }
            });
        }
    };

    private class ExecuteAsRoot extends AExecuteAsRoot {

        @Override
        protected ArrayList<String> getCommandsToExecute() {
            ArrayList<String> list = new ArrayList<String>();
//            list.add("adb devices");
//            list.add("setenforce 0");
//            list.add("su root");
            list.add("whoami");
//            list.add("mount -o rw,remount /system");
//            list.add("chmod 777 /system");
            list.add("ls -l");
//            list.add("cp /storage/emulated/0/Android/data/com.kborid.smart/cache/ss.apk /system/app");
//            list.add("cp /system/app/fyt.prop /storage/emulated/0/Android/data/com.kborid.smart/cache/");
            list.add("ls -l /system/app | grep apk");
            return list;
        }
    }

    @OnClick(R.id.btn_click4)
    void click4() {
        ToastUtils.showToast("doSU");
        boolean isCan = false;
        Logger.t(TAG).d("can run root:" + (isCan = ExecuteAsRoot.canRunRootCommands()));

        if (isCan) {
            Logger.t(TAG).d("result = " + new ExecuteAsRoot().execute());
        }
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
            PackageManagerImpl.uninstallPackage("com.juma.jumalauncher.horizontal", callback);
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
            int progress = (int) ((float) soFarBytes / totalBytes * 100);
            progressBar.setProgress(progress);
        }

        @Override
        protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            Logger.t("down").d(task.getTag() + " progress soFarBytes:" + soFarBytes + ", totalBytes:" + totalBytes);
            int progress = (int) ((float) soFarBytes / totalBytes * 100);
            Logger.t("down").d(task.getTag() + " progress progress:" + progress);
            progressBar.setProgress(progress);
        }

        @Override
        protected void completed(BaseDownloadTask task) {
            Logger.t("down").d(task.getTag() + " completed");
            progressBar.setProgress(100);
            Logger.t("down").d(task.getTag() + " path = " + task.getPath());
            Logger.t("down").d(task.getTag() + " name = " + task.getFilename());
            ToastUtils.showToast("apk下载完成");
        }

        @Override
        protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
        }

        @Override
        protected void error(BaseDownloadTask task, Throwable e) {
        }

        @Override
        protected void warn(BaseDownloadTask task) {
        }
    };
}
