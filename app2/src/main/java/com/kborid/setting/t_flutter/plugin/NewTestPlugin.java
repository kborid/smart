package com.kborid.setting.t_flutter.plugin;

import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.kborid.setting.PRJApplication;
import com.orhanobut.logger.Logger;
import com.thunisoft.common.tool.UIHandler;
import com.thunisoft.common.util.ToastUtils;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;

public class NewTestPlugin implements MethodChannel.MethodCallHandler, FlutterPlugin, ActivityAware {

    private static final String TAG = NewTestPlugin.class.getSimpleName();

    private MethodChannel.Result mResult;
    private static MethodChannel channel;
    private static NewTestPlugin instance = new NewTestPlugin();

    public static void registerWith(PluginRegistry.Registrar registrar) {
        channel = new MethodChannel(registrar.messenger(), PluginChannelDef.TEST_CHANNEL);
        channel.setMethodCallHandler(instance);
    }

    private NewTestPlugin() {
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
        Toast.makeText(PRJApplication.getInstance(), "invoke native method success~~~1", Toast.LENGTH_LONG).show();
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
    public void onAttachedToEngine(FlutterPluginBinding binding) {
        Logger.i(TAG, "onAttachedToEngine()");
        channel = new MethodChannel(binding.getBinaryMessenger(), PluginChannelDef.TEST_CHANNEL);
        channel.setMethodCallHandler(instance);
    }

    @Override
    public void onDetachedFromEngine(FlutterPluginBinding binding) {
        Logger.i(TAG, "onDetachedFromEngine()");
    }

    @Override
    public void onAttachedToActivity(ActivityPluginBinding binding) {
        Logger.i(TAG, "onAttachedToActivity()");
    }

    @Override
    public void onDetachedFromActivityForConfigChanges() {
        Logger.i(TAG, "onDetachedFromActivityForConfigChanges()");
    }

    @Override
    public void onReattachedToActivityForConfigChanges(ActivityPluginBinding binding) {
        Logger.i(TAG, "onReattachedToActivityForConfigChanges()");
    }

    @Override
    public void onDetachedFromActivity() {
        Logger.i(TAG, "onDetachedFromActivity()");
    }
}
