package com.kborid.smart.network;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

public class Api {
    public static final String baseUrl = "http://www.publicobject.com/";
    private static RequestApi requestApi;

    private Api() {
    }

    private static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .client(OkHttpClientFactory.newOkHttpClient())
                .baseUrl(baseUrl)// Base URL
//                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private static RequestApi getApi() {
        if (requestApi == null) {
            requestApi = getRetrofit().create(RequestApi.class);
        }
        return requestApi;
    }

    public static void getOkHttpTest(Observer<ResponseBody> observer) {
        getApi().getOkHttpTest()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
