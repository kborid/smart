package com.kborid.smart.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;

import com.juma.jumaid.aidl.IAidlSession;
import com.juma.jumaid.aidl.IAidlSessionGetter;
import com.juma.jumaid.provider.BinderParcelable;
import com.kborid.library.common.MultiTaskHandler;
import com.kborid.library.util.LogUtils;
import com.kborid.smart.R;
import com.kborid.smart.fragment.first.FragmentFirst;
import com.orhanobut.logger.Logger;

import java.util.List;

public class FragmentContainerActivity extends BaseActivity {

    private static final String TAG = FragmentContainerActivity.class.getSimpleName();
    private static final String AUTHORITY = "com.juma.jumaid.provider";

    private static final String PATH_SESSION_GETTER_BINDER = "PATH_SESSION_GETTER_BINDER";

    private static final String METHOD_GET_LOGIN_INFO = "METHOD_GET_LOGIN_INFO";
    private static final String METHOD_GET_SESSION = "METHOD_GET_SESSION";

    private static final String KEY_SESSION = "KEY_SESSION";
    private static final String KEY_LOGIN_INFO = "KEY_LOGIN_INFO";

    private HandlerThread mHandlerThread;
    private Handler mHandler;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_fragment_container;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandlerThread = new HandlerThread("HandlerThread");
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());
    }

    @Override
    protected void initParams() {
        super.initParams();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.container, FragmentFirst.newInstance());
        ft.commitAllowingStateLoss();
    }

    public void getSession1(View view) {
        MultiTaskHandler.post(new Runnable() {
            @Override
            public void run() {
                ContentResolver contentResolver = getContentResolver();
                Uri uri = Uri.parse("content://" + AUTHORITY);
                Bundle bundle = null;
                try {
                    bundle = contentResolver.call(uri, METHOD_GET_SESSION, null, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (null != bundle) {
                    Logger.t(TAG).d("session = " + bundle.getString(KEY_SESSION));
                }
            }
        });
    }

    public void getInfo1(View view) {
        MultiTaskHandler.post(new Runnable() {
            @Override
            public void run() {
                ContentResolver contentResolver = getContentResolver();
                Uri uri = Uri.parse("content://" + AUTHORITY);
                Bundle bundle = null;
                try {
                    bundle = contentResolver.call(uri, METHOD_GET_LOGIN_INFO, null, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (null != bundle) {
                    Logger.t(TAG).d("loginInfo = " + bundle.getString(KEY_LOGIN_INFO));
                }
            }
        });
    }

    public void login1(View view) {
        autoLogin();
    }

    private void autoLogin() {
        MultiTaskHandler.post(new Runnable() {
            @Override
            public void run() {
                ContentResolver contentResolver = getContentResolver();
                Uri uri = Uri.parse("content://" + AUTHORITY + "/" + PATH_SESSION_GETTER_BINDER);
                Cursor cursor = null;
                try {
                    cursor = contentResolver.query(uri, null, null, null, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (null == cursor) {
                    return;
                }

                IBinder binder = getBinder(cursor);
                try {
                    IAidlSessionGetter sessionGetter = IAidlSessionGetter.Stub.asInterface(binder);
                    sessionGetter.getSessionAutoLogin(1, new IAidlSession.Stub() {
                        @Override
                        public void onGetSession(String s) throws RemoteException {
                            Logger.t(TAG).d("session = " + s);
                        }
                    });
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                cursor.close();
            }
        });
    }

    public void logout1(View view) {
        MultiTaskHandler.post(new Runnable() {
            @Override
            public void run() {
                ContentResolver contentResolver = getContentResolver();
                Uri uri = Uri.parse("content://" + AUTHORITY + "/" + PATH_SESSION_GETTER_BINDER);
                Cursor cursor = null;
                try {
                    cursor = contentResolver.query(uri, null, null, null, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (null == cursor) {
                    return;
                }

                IBinder binder = getBinder(cursor);
                try {
                    IAidlSessionGetter sessionGetter = IAidlSessionGetter.Stub.asInterface(binder);
                    sessionGetter.logout();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                cursor.close();
            }
        });
    }

    public void getSession2(View view) {
        PackageManager pm = getPackageManager();
        List<PackageInfo> pkgInfoList = pm.getInstalledPackages(0);
        for (PackageInfo info : pkgInfoList) {
            if ((info.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != ApplicationInfo.FLAG_SYSTEM) {
                LogUtils.d("name:" + info.applicationInfo.loadLabel(pm) + ", " + info.packageName);
            }
        }
    }

    private Runnable runnableTask = new Runnable() {
        @Override
        public void run() {
            Logger.t(TAG).d("begin run in...." + Thread.currentThread().getName());
//            autoLogin();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Logger.t(TAG).d("end running!!!");
        }
    };

    public void getInfo2(View view) {
        mHandler.post(runnableTask);
        mHandler.postDelayed(runnableTask, 3000);
    }

    public void login2(View view) {
    }

    public void logout2(View view) {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mHandlerThread) {
            mHandlerThread.quitSafely();
            mHandlerThread = null;
        }
        mHandler = null;
    }
}
