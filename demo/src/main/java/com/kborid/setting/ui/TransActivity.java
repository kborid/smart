package com.kborid.setting.ui;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.LayoutInflaterCompat;

import com.kborid.demo.t_okhttp.OkHttpHelper;
import com.kborid.demo.t_realm.entity.User;
import com.kborid.setting.R;
import com.thunisoft.common.base.BaseSimpleActivity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.Response;

public class TransActivity extends BaseSimpleActivity {

    private final static Logger logger = LoggerFactory.getLogger(TransActivity.class);

    @Override
    protected int getLayoutResId() {
        return R.layout.act_trans;
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
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.executeTransaction(r -> {
                User user = r.createObject(User.class);
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onClick(View view) {
//        ContentResolver resolver = getContentResolver();
//        try {
//            resolver.openFileDescriptor(Uri.parse("content://tt/t.txt"), "r");
//        } catch (FileNotFoundException e) {
//            logger.error("文件不存在", e);
//        }
//
//        startActivity(new Intent(this, ThirdActivity.class));
        new Thread(() -> {
            logger.info("url req start");
            Response res = OkHttpHelper.getInstance().syncGet(T_URL);
            logger.info("url req enddd");
            logger.info("{}", res);
        /*URL url = null;
        try {
            url = new URL(T_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            if (connection.getResponseCode() == 200) {
                logger.info("res = {}", connection.getResponseMessage());
            }
        } catch (MalformedURLException e) {
            logger.error("url失败", e);
        } catch (IOException ioe) {
            logger.error("IO失败", ioe);
        }*/
        }).start();
    }

    private final static String T_URL = "http://publicobjecct.com/helloworld.txt";
}