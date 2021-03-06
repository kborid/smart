package com.thunisoft.common.network.api;

import android.annotation.SuppressLint;

import com.thunisoft.common.network.OkHttpClientFactory;
import com.thunisoft.common.network.callback.ResponseCallback;
import com.thunisoft.common.network.comm.CommResBean;
import com.thunisoft.common.network.func.ResponseFunc;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public class SampleApiManager {

    private static final String baseUrl = "";

    private static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .client(OkHttpClientFactory.newOkHttpClient())
                .baseUrl(baseUrl)
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private static Api api;

    private static Api getApi() {
        if (api == null) {
            api = getRetrofit().create(Api.class);
        }
        return api;
    }

    @SuppressLint("CheckResult")
    public static void testPost(Object params, final ResponseCallback<Object> responseCallback) {
        getApi().post(params)
                .map(new ResponseFunc<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((o -> {
                    if (null != responseCallback) {
                        responseCallback.success(o);
                    }
                }), (e) -> {
                    if (null != responseCallback) {
                        responseCallback.failure(e);
                    }
                });
    }

    @SuppressLint("CheckResult")
    public static void testGet(String code, final ResponseCallback<Object> responseCallback) {
        getApi().get(code)
                .map(new ResponseFunc<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((o -> {
                    if (null != responseCallback) {
                        responseCallback.success(o);
                    }
                }), (e) -> {
                    if (null != responseCallback) {
                        responseCallback.failure(e);
                    }
                });
    }

    /**
     * 请求接口地址
     */
    private interface Api {

        /**
         * post
         *
         * @param param
         * @return
         */
        @POST("api/v1/post")
        Observable<CommResBean<Object>> post(@Body Object param);

        /**
         * get
         *
         * @param code
         * @return
         */
        @GET("api/v1/get/{code}")
        Observable<CommResBean<Object>> get(@Path("code") String code);
    }
}
