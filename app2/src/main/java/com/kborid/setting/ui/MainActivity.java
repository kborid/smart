package com.kborid.setting.ui;//package com.kborid.setting.ui

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.alibaba.fastjson.JSON;
import com.kborid.setting.R;
import com.kborid.setting.t_okhttp.OkHttpHelper;
import com.thunisoft.common.base.BaseActivity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends BaseActivity {

    private LifecycleObserver lifecycleObserver = new LifecycleObserver() {
        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        void resume() {
            System.out.println("resume");
            getLayoutInflater().inflate(R.layout.activity_main, null);
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        void pause() {
            System.out.println("pause");
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        void destroy() {
            System.out.println("destroy");
        }
    };

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initDataAndEvent(@Nullable Bundle bundle) {
        getLifecycle().addObserver(lifecycleObserver);
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {
                try {
                    String host = "http://publicobject.com/helloworld.txt";

                    System.out.println(OkHttpHelper.getInstance().syncGet(host));
                    OkHttpHelper.getInstance().asyncGet(host, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            System.out.println("onFailure");
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            System.out.println("onSuccess");
//                            OkHttpHelper.getInstance().printResHeader(response);
//                            OkHttpHelper.getInstance().printResBody(response);
                            System.out.println(response);
                        }
                    });
                    Map<String, String> params = new HashMap<>();
                    params.put("key", "value");
                    System.out.println(OkHttpHelper.getInstance().syncPostForm(host, params));
                    System.out.println(OkHttpHelper.getInstance().syncPostJson(host, JSON.toJSONString(params)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getLifecycle().removeObserver(lifecycleObserver);
    }
}