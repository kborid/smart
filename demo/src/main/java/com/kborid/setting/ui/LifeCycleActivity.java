package com.kborid.setting.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.kborid.setting.PRJApplication;
import com.kborid.setting.R;
import com.kborid.setting.constant.LifecycleState;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.lang.reflect.Field;
import java.util.Arrays;

public class LifeCycleActivity extends Activity {

    Logger logger = LoggerFactory.getLogger(LifeCycleActivity.class);

    @LifecycleState
    int mState;

    @LifecycleState
    public int getState() {
        return mState;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_lifecycle);
        logger.info("获取state的值：{}", getState());
        printSystemInfo();
        logger.info("执行 onCreate() 方法");
    }

    @Override
    protected void onStart() {
        super.onStart();
        logger.info("执行 onStart() 方法");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        logger.info("执行 onConfigurationChanged() 方法");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        logger.info("执行 onRestoreInstanceState() 方法");
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        logger.info("执行 onPostCreate() 方法");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        logger.info("执行 onNewIntent() 方法");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        logger.info("执行 onSaveInstanceState() 方法");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        logger.info("执行 onLowMemory() 方法");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        logger.info("执行 onRestart() 方法");
    }

    @Override
    protected void onResume() {
        super.onResume();
        logger.info("执行 onResume() 方法");
    }

    @Override
    protected void onPause() {
        super.onPause();
        logger.info("执行 onPause() 方法");
    }

    @Override
    protected void onStop() {
        super.onStop();
        logger.info("执行 onStop() 方法");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        logger.info("执行 onDestroy() 方法");
    }

    private void printSystemInfo() {
        logger.info("当前系统API Level：【{}】", Build.VERSION.SDK_INT);
        logger.info("当前APP targetSDK：【{}】", PRJApplication.getInstance().getApplicationInfo().targetSdkVersion);
        try {
            Class<?> clazz = Class.forName("android.os.Build");
            Field[] fields = clazz.getDeclaredFields();
            Arrays.stream(fields).forEach(field -> {
                field.setAccessible(true);
                try {
                    logger.info("【{}】:【{}】", field.getName(), field.get(null));
                } catch (IllegalAccessException e) {
                    logger.info("反射获取字段{}失败", field.getName());
                }
            });
        } catch (Exception e) {
            logger.info("反射加载系统Build类失败");
        }
    }
}