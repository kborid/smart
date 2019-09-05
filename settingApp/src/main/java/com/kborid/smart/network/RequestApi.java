package com.kborid.smart.network;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;

public interface RequestApi {

    @GET("/helloworld.txt")
    Observable<ResponseBody> getOkHttpTest();
}
