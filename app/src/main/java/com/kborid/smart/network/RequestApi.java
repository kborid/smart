package com.kborid.smart.network;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RequestApi {

    @POST("/getLatestApps.json")
    Observable<Object> getUpdatesInfo();

    @POST("/getLatestAppByPkgName.json")
    Observable<Object> getUpdateInfo(@Body AppRequestBean bean);

}
