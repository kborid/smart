package com.kborid.smart.network;

import com.kborid.smart.entity.NewsDetail;
import com.kborid.smart.entity.NewsSummary;
import com.kborid.smart.entity.PhotoResBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface RequestApi {

    // 新闻列表
    @GET("nc/article/{type}/{id}/{startPage}-20.html")
    Observable<Map<String, List<NewsSummary>>> getNewsList(@Path("type") String type, @Path("id") String id, @Path("startPage") int startPage);

    // 新闻详情
    @GET("nc/article/{postId}/full.html")
    Observable<NewsDetail> getNewDetail(@Path("postId") String postId);

    // 图片列表
    @GET("data/福利/{size}/{page}")
    Observable<PhotoResBean> getPhotoList(@Path("size") int size, @Path("page") int page);
}
