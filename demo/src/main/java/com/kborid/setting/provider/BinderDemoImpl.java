package com.kborid.setting.provider;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

import com.kborid.setting.PRJApplication;
import com.thunisoft.common.tool.MultiTaskHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BinderDemoImpl {

    private static final Logger logger = LoggerFactory.getLogger(BinderDemoImpl.class);

    private static final String AUTHORITY = "com.kborid.setting.provider";
    private static final String METHOD_GET_LOGIN_INFO = "METHOD_GET_LOGIN_INFO";
    private static final String METHOD_GET_SESSION = "METHOD_GET_SESSION";
    private static final String KEY_SESSION = "KEY_SESSION";
    private static final String KEY_LOGIN_INFO = "KEY_LOGIN_INFO";

    public void getSession(View view) {
        MultiTaskHandler.post(new Runnable() {
            @Override
            public void run() {
                ContentResolver contentResolver = PRJApplication.getInstance().getContentResolver();
                Uri uri = Uri.parse("content://" + AUTHORITY);
                Bundle bundle = null;
                try {
                    bundle = contentResolver.call(uri, METHOD_GET_SESSION, null, null);
                } catch (Exception e) {
                    logger.error("ContentProvider调用失败", e);
                }
                if (null != bundle) {
                    com.orhanobut.logger.Logger.d("session = " + bundle.getString(KEY_SESSION));
                }
            }
        });
    }

    public static IBinder getBinder(Cursor cursor) {
        Bundle extras = cursor.getExtras();
        extras.setClassLoader(BinderParcelable.class.getClassLoader()); //目的是获取PathClassLoader加载自定义类，默认Parcel类的classloader是BootClassLoader
        BinderParcelable binderParcelable = extras.getParcelable("binder");
        if (null != binderParcelable) {
            return binderParcelable.getBinder();
        }
        return null;
    }
}
