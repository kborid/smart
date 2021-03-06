package com.kborid.setting.plugin;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.annotation.NonNull;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.thunisoft.common.tool.UIHandler;
import com.thunisoft.common.util.ToastUtils;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;

public class TestPlugin implements MethodChannel.MethodCallHandler, PluginRegistry.ActivityResultListener {

    private static final String TAG = TestPlugin.class.getSimpleName();

    private Context mContext;
    private MethodChannel.Result mResult;

    public static void registerWith(PluginRegistry.Registrar registrar) {
        MethodChannel channel = new MethodChannel(registrar.messenger(), PluginChannelDef.TEST_CHANNEL);
        TestPlugin instance = new TestPlugin(registrar.activeContext());
        channel.setMethodCallHandler(instance);
    }

    private TestPlugin(Context context) {
        this.mContext = context;
    }

    @Override
    public void onMethodCall(@NonNull MethodCall methodCall, @NonNull MethodChannel.Result result) {
        Logger.i(TAG, "onMethodCall()");
        this.mResult = result;
        switch (methodCall.method) {
            case "checkInstalled":
                result.success(checkInstalled());
                break;
            case "getPlatformVersion":
                result.success(getPlatformVersion());
                break;
            default:
                result.notImplemented();
                break;
        }
    }

    private boolean checkInstalled() {
        Logger.i(TAG, "checkInstalled()");
        Toast.makeText(mContext, "invoke native method success~~~1", Toast.LENGTH_LONG).show();
        UIHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showToast("invoke native method success~~~2");
            }
        }, 1000);
        return false;
    }

    private String getPlatformVersion() {
        Logger.i(TAG, "getPlatformVersion()");
        return Build.VERSION.RELEASE;
    }

    @Override
    public boolean onActivityResult(int i, int i1, Intent intent) {
        return false;
    }
}
