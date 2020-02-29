package com.kborid.smart.network;

import com.kborid.smart.entity.NewsSummary;
import com.kborid.smart.entity.PhotoResBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RequestApi {

    @GET("/helloworld.txt")
    Observable<ResponseBody> getOkHttpTest();

    @GET("nc/article/{type}/{id}/{startPage}-20.html")
    Observable<Map<String, List<NewsSummary>>> getNewsList(@Path("type") String type, @Path("id") String id, @Path("startPage") int startPage);

    @GET("data/福利/{size}/{page}")
    Observable<PhotoResBean> getPhotoList(@Path("size") int size, @Path("page") int page);
}
