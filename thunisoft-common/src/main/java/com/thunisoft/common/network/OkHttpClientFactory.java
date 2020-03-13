package com.thunisoft.common.network;

import com.thunisoft.common.BuildConfig;
import com.thunisoft.common.network.interceptor.HeaderInterceptor;
import com.thunisoft.common.network.interceptor.LoggerInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @description: okHttpClient工厂类
 * @date: 2019/7/2
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 */
public class OkHttpClientFactory {

    private static final int TIMEOUT = 10;

    public static OkHttpClient newOkHttpClient() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(TIMEOUT, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new LoggerInterceptor());
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
            builder.addInterceptor(httpLoggingInterceptor);

            // 网络请求监测
//            builder.addNetworkInterceptor(new StethoInterceptor());
        }

        builder.addInterceptor(new HeaderInterceptor());
        return builder.build();
    }
}
