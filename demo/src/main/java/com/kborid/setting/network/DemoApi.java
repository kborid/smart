package com.kborid.setting.network;

import com.kborid.setting.entity.WsVO;
import io.reactivex.Observable;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public interface DemoApi {

    @GET("/user/info/{id}")
    Observable<String> getUserInfo(@Path("id") String id);

    @GET("/user/info2/{id}")
    Call<String> getUserInfo2(@Path("id") String id);

    @GET("/api/v1/ws/{mbbh}/list")
    Observable<List<WsVO>> getWsListByMbbh(@Path("mbbh") String mbbh);

    @PATCH("/api/v1/patch")
    Call<String> updateInfo();
}
