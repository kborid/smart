package com.kborid.smart.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.view.View;

import com.kborid.library.util.LogUtils;
import com.kborid.smart.R;
import com.kborid.smart.fragment.second.FragmentSecond;

public class FragmentActivity extends SimpleActivity {

    private static final String TAG = FragmentActivity.class.getSimpleName();
//    private static final String AUTHORITY = "com.kborid.smart.provider";
//    private static final String METHOD_GET_LOGIN_INFO = "METHOD_GET_LOGIN_INFO";
//    private static final String METHOD_GET_SESSION = "METHOD_GET_SESSION";
//    private static final String KEY_SESSION = "KEY_SESSION";
//    private static final String KEY_LOGIN_INFO = "KEY_LOGIN_INFO";

    private HandlerThread mHandlerThread;
    private Handler myHandler;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandlerThread = new HandlerThread("HandlerThread");
        mHandlerThread.start();
        myHandler = new Handler(mHandlerThread.getLooper());
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
//        ft.replace(R.id.container, FragmentSecond.newInstance());
        ft.commitAllowingStateLoss();
    }

    public void getSession1(View view) {
//        MultiTaskHandler.post(new Runnable() {
//            @Override
//            public void run() {
//                ContentResolver contentResolver = getContentResolver();
//                Uri uri = Uri.parse("content://" + AUTHORITY);
//                Bundle bundle = null;
//                try {
//                    bundle = contentResolver.call(uri, METHOD_GET_SESSION, null, null);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                if (null != bundle) {
//                    Logger.t(TAG).d("session = " + bundle.getString(KEY_SESSION));
//                }
//            }
//        });
        LogUtils.d(TAG, "getSession1");
    }

    public void getInfo1(View view) {
//        MultiTaskHandler.post(new Runnable() {
//            @Override
//            public void run() {
//                ContentResolver contentResolver = getContentResolver();
//                Uri uri = Uri.parse("content://" + AUTHORITY);
//                Bundle bundle = null;
//                try {
//                    bundle = contentResolver.call(uri, METHOD_GET_LOGIN_INFO, null, null);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                if (null != bundle) {
//                    Logger.t(TAG).d("loginInfo = " + bundle.getString(KEY_LOGIN_INFO));
//                }
//            }
//        });
        LogUtils.d(TAG, "getInfo1");
    }

    public void login1(View view) {
        LogUtils.d(TAG, "login1");
    }

    public void logout1(View view) {
        LogUtils.d(TAG, "logout1");
    }


    public void getInfo2(View view) {
        LogUtils.d(TAG, "getInfo2");
    }

    public void login2(View view) {
        LogUtils.d(TAG, "login2");
    }

    public void logout2(View view) {
        LogUtils.d(TAG, "logout2");
    }

    public static IBinder getBinder(Cursor cursor) {
//        Bundle extras = cursor.getExtras();
//        extras.setClassLoader(BinderParcelable.class.getClassLoader()); //目的是获取PathClassLoader加载自定义类，默认Parcel类的classloader是BootClassLoader
//        BinderParcelable binderParcelable = extras.getParcelable("binder");
//        if (null != binderParcelable) {
//            return binderParcelable.getBinder();
//        }
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mHandlerThread) {
            mHandlerThread.quitSafely();
            mHandlerThread = null;
        }
        myHandler = null;
    }
}
