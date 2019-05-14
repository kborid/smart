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
    }

    @OnClick(R.id.btn_click2)
    void click2() {
    }

    @OnClick(R.id.btn_click3)
    void click3() {
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
    }

    @OnClick(R.id.btn_click5)
    void click5() {
    }
}
