package com.kborid.smart.fragment.second;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.widget.ProgressBar;

import com.kborid.library.base.BaseSimpleFragment;
import com.kborid.smart.R;
import com.kborid.smart.test.AExecuteAsRoot;
import com.orhanobut.logger.Logger;
import com.thunisoft.common.util.ToastUtils;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class FragmentSecond extends BaseSimpleFragment {

    private static final String TAG = FragmentSecond.class.getSimpleName();
    private String url = "http://gdown.baidu.com/data/wisegame/d4524b42d5a5ccf7/baidujisuban_20514176.apk";
    private String mDirStr, mFileStr;

    @BindView(R.id.process)
    ProgressBar progressBar;

    public static Fragment newInstance() {
        return new FragmentSecond();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_second;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        mDirStr = getActivity().getExternalCacheDir().getPath() + File.separator;
        mFileStr = "ss%1$d.apk";
    }

    @OnClick(R.id.btn_click1)
    void click1() {
        ToastUtils.showToast("click1");
    }

    @OnClick(R.id.btn_click2)
    void click2() {
        ToastUtils.showToast("click2");
    }

    @OnClick(R.id.btn_click3)
    void click3() {
        ToastUtils.showToast("click3");
    }

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
        ToastUtils.showToast("click4");
        boolean isCan = false;
        Logger.t(TAG).d("can run root:" + (isCan = ExecuteAsRoot.canRunRootCommands()));

        if (isCan) {
            Logger.t(TAG).d("result = " + new ExecuteAsRoot().execute());
        }
    }

    @OnClick(R.id.btn_click5)
    void click5() {
        ToastUtils.showToast("click5");
    }
}
