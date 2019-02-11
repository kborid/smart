package com.kborid.smart.network;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

public class Api {
    private static final String baseUrl = "http://www.zaichengdu.com/cd_portal/service/";
    private static RequestApi requestApi;


    private static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)// Base URL
                .addConverterFactory(FastJsonConverterFactory.create())
                .build();
    }

    private static RequestApi getApi() {
        if (requestApi == null) {
            requestApi = getRetrofit().create(RequestApi.class);
        }
        return requestApi;
    }

    public static void getNews(Object data, Callback<Object> callback) {
        Call<Object> call = getApi().getNews(data);
        call.enqueue(callback);
    }

    public static void getWeather(String cityCode, Callback<Object> callback) {
        Call<Object> call = getApi().getWeather(cityCode);
        call.enqueue(callback);
    }
}
