package com.kborid.setting.ui.Fragment;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kborid.setting.provider.BinderParcelable;
import com.thunisoft.common.base.BaseSimpleFragment;
import com.thunisoft.common.tool.MultiTaskHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestAFragment extends BaseSimpleFragment {

    private static final Logger logger = LoggerFactory.getLogger(TestAFragment.class);

    private static final String AUTHORITY = "com.kborid.setting.provider";
    private static final String METHOD_GET_LOGIN_INFO = "METHOD_GET_LOGIN_INFO";
    private static final String METHOD_GET_SESSION = "METHOD_GET_SESSION";
    private static final String KEY_SESSION = "KEY_SESSION";
    private static final String KEY_LOGIN_INFO = "KEY_LOGIN_INFO";

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        logger.info("onAttach");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        logger.info("onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        logger.info("onActivityCreated");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logger.info("onCreate");
        setRetainInstance(true);
    }

    @Override
    protected int getLayoutResId() {
        return 0;
    }

    @Override
    protected void initEventAndData(Bundle bundle) {

    }

    @Override
    public void onStart() {
        super.onStart();
        logger.info("onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        logger.info("onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        logger.info("onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        logger.info("onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        logger.info("onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        logger.info("onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        logger.info("onDetach");
    }

    public void getSession(View view) {
        MultiTaskHandler.post(new Runnable() {
            @Override
            public void run() {
                ContentResolver contentResolver = getContext().getContentResolver();
                Uri uri = Uri.parse("content://" + AUTHORITY);
                Bundle bundle = null;
                try {
                    bundle = contentResolver.call(uri, METHOD_GET_SESSION, null, null);
                } catch (Exception e) {
                    e.printStackTrace();
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
