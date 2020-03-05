package com.kborid.smart.tt;

import android.database.Cursor;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

import com.kborid.smart.R;
import com.kborid.smart.activity.SimpleActivity;
import com.kborid.smart.provider.BinderParcelable;

public class FragmentActivity extends SimpleActivity {

//    private static final String AUTHORITY = "com.kborid.smart.provider";
//    private static final String METHOD_GET_LOGIN_INFO = "METHOD_GET_LOGIN_INFO";
//    private static final String METHOD_GET_SESSION = "METHOD_GET_SESSION";
//    private static final String KEY_SESSION = "KEY_SESSION";
//    private static final String KEY_LOGIN_INFO = "KEY_LOGIN_INFO";

    @Override
    protected int getLayoutResId() {
        return R.layout.act_fragment;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction ft = fragmentManager.beginTransaction();
//        ft.replace(R.id.container, VideoTabFragment.newInstance());
//        ft.commitAllowingStateLoss();
    }

    public void getSession(View view) {
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
    }

    public void getInfo(View view) {
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
