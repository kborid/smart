package com.kborid.smart.network;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.kborid.smart.BuildConfig;
import com.kborid.smart.network.Interceptor.HeaderInterceptor;
import com.kborid.smart.network.Interceptor.LoggerInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 获取okHttpClient工厂
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
            builder.addNetworkInterceptor(new StethoInterceptor());
        }

        builder.addInterceptor(new HeaderInterceptor());
        return builder.build();
    }
}
