package com.kborid.smart.network;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RequestApi {

    //news
    @GET("{cityCode}")
    Call<Object> getWeather(@Path("cityCode") String cityCode);

    @POST("CW0005")
    Call<Object> getNews(@Body ResponseData data);
}
