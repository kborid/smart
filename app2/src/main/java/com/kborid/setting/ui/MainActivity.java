package com.kborid.setting.ui;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.LayoutInflaterCompat;
import androidx.lifecycle.LifecycleObserver;

import com.kborid.setting.R;
import com.kborid.setting.demo.SimpleLifecycleObserver;
import com.kborid.setting.demo.t_okhttp.OkHttpHelper;
import com.kborid.setting.demo.t_realm.entity.User;
import com.kborid.setting.demo.t_rxjava.RxJavaTest;
import com.thunisoft.common.base.BaseActivity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;

import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.Response;

public class MainActivity extends BaseActivity {

    private final static Logger logger = LoggerFactory.getLogger(MainActivity.class);

    private LifecycleObserver lifecycleObserver;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory2(LayoutInflater.from(this), new LayoutInflater.Factory2() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                logger.info("【Name = {}】", name);
                int n = attrs.getAttributeCount();
                for (int i = 0; i < n; i++) {
                    logger.info("【Attr = {}】:{}", attrs.getAttributeName(i), attrs.getAttributeValue(i));
                }
                if ("ImageView".equals(name)) {
                    AppCompatDelegate delegate = getDelegate();
                    ImageView view = (ImageView) delegate.createView(parent, name, context, attrs);
                    view.setImageResource(R.mipmap.logo_small);
                    return view;
                }
                return null;
            }

            @Override
            public View onCreateView(String name, Context context, AttributeSet attrs) {
                return null;
            }
        });
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initDataAndEvent(@Nullable Bundle bundle) {
        lifecycleObserver = new SimpleLifecycleObserver(logger);
        getLifecycle().addObserver(lifecycleObserver);
        RxJavaTest.test();
        new Thread(requestRunnable).start();
    }

    private Runnable requestRunnable = () -> {
        String host = "http://publicobject.com/helloworld.txt";
        Response res = OkHttpHelper.getInstance().syncGet(host);
        logger.info(res.toString());
    };

    public void onClick(View view) {
        ContentResolver resolver = getContentResolver();
        try {
            resolver.openFileDescriptor(Uri.parse("content://tt/t.txt"), "r");
        } catch (FileNotFoundException e) {
            logger.error("文件不存在", e);
        }

        try (Realm realm = Realm.getDefaultInstance()) {
            realm.executeTransaction(realm1 -> {
                User user = realm.createObject(User.class);
                user.setName("呆未厶");
                user.setAge(10);
                user.setAddress("山东省威海市");
            });

            RealmResults<User> results = realm.where(User.class).findAll();
            System.out.println(results.size());
        } catch (Exception e) {
            logger.error("插入数据库失败", e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getLifecycle().removeObserver(lifecycleObserver);
//        final String permission = "com.kborid.smart.permission.BROADCAST.TT";
//        Intent intent = new Intent("com.kborid.smart.ACTION.TT");
//        sendBroadcast(intent, permission);
//        sendOrderedBroadcast(intent, permission);
    }
}