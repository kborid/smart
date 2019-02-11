package com.kborid.smart.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.kborid.smart.PRJApplication;
import com.kborid.smart.R;
import com.kborid.smart.util.ToastUtils;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.orhanobut.logger.Logger;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentSecond extends Fragment {

    private String url = "http://gdown.baidu.com/data/wisegame/d4524b42d5a5ccf7/baidujisuban_20514176.apk";
    private String mDirStr, mFileStr;

    @BindView(R.id.process)
    ProgressBar progressBar;
    @BindView(R.id.btn_click1)
    Button btn_click1;
    @BindView(R.id.btn_click2)
    Button btn_click2;
    @BindView(R.id.btn_click3)
    Button btn_click3;
    @BindView(R.id.btn_click4)
    Button btn_click4;

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
        FileDownloader.init(PRJApplication.getInstance());
        mDirStr = getActivity().getExternalCacheDir().getPath() + File.separator;
        mFileStr = "ss.apk";
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
        install(mDirStr + mFileStr);
    }

    @OnClick(R.id.btn_click4)
    void click4() {
        if (!new File(mDirStr + mFileStr).exists()) {
            ToastUtils.showToast("apk不存在");
            return;
        }
        new File(mDirStr + mFileStr).delete();
        ToastUtils.showToast("apk已删除");
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
