package com.kborid.smart.network;

import com.kborid.smart.entity.NewsDetail;
import com.kborid.smart.entity.NewsSummary;
import com.kborid.smart.entity.PhotoResBean;
import com.kborid.smart.entity.VideoData;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface RequestApi {

    // 新闻列表
    @GET("nc/article/{type}/{id}/{startIndex}-{endIndex}.html")
    Observable<Map<String, List<NewsSummary>>> getNewsList(@Path("type") String type, @Path("id") String id, @Path("startIndex") int startIndex, @Path("endIndex") int endIndex);

    // 新闻详情
    @GET("nc/article/{postId}/full.html")
    Observable<Map<String, NewsDetail>> getNewDetail(@Path("postId") String postId);

    // 新闻详情图片
    @GET
    Observable<ResponseBody> getNewsBodyHtmlPhoto(@Url String photoPath);
    //@Url，它允许我们直接传入一个请求的URL。这样以来我们可以将上一个请求的获得的url直接传入进来，baseUrl将被无视baseUrl 需要符合标准，为空、""、或不合法将会报错

    // 图片列表
    @GET("data/福利/{size}/{page}")
    Observable<PhotoResBean> getPhotoList(@Path("size") int size, @Path("page") int page);

    @GET("nc/video/list/{type}/n/{startPage}-10.html")
    Observable<Map<String, List<VideoData>>> getVideoList(
            @Path("type") String type,
            @Path("startPage") int startPage);

    @GET("data/福利/{size}/{page}")
    Callback<Object> get(@Path("size") int size, @Path("page") int page);
}
