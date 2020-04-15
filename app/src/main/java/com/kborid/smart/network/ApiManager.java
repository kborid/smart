package com.kborid.smart.network;

import android.annotation.SuppressLint;
import android.util.SparseArray;

import com.kborid.smart.entity.NewsDetail;
import com.kborid.smart.entity.NewsSummary;
import com.kborid.smart.entity.PhotoGirl;
import com.kborid.smart.entity.PhotoResBean;
import com.kborid.smart.entity.VideoData;
import com.thunisoft.common.network.OkHttpClientFactory;
import com.thunisoft.common.network.callback.ResponseCallback;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

public class ApiManager {

    private static SparseArray<RequestApi> apiSparseArray = new SparseArray<>(HostType.TYPE_COUNT);

    private ApiManager() {
    }

    private static Retrofit getRetrofit(int hostType) {
        return new Retrofit.Builder()
                .client(OkHttpClientFactory.newOkHttpClient())
                .baseUrl(ApiConstants.getHost(hostType))
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static RequestApi getApi(int hostType) {
        RequestApi requestApi = apiSparseArray.get(hostType);
        if (requestApi == null) {
            requestApi = getRetrofit(hostType).create(RequestApi.class);
            apiSparseArray.put(hostType, requestApi);
        }
        return requestApi;
    }

    @SuppressLint("CheckResult")
    public static void getNewsList(String type, String id, int startPage, ResponseCallback<List<NewsSummary>> callback) {
        getApi(HostType.NETEASE_NEWS_VIDEO).getNewsList(type, id, startPage)
                .flatMap(new Function<Map<String, List<NewsSummary>>, ObservableSource<NewsSummary>>() {
                    @Override
                    public ObservableSource<NewsSummary> apply(Map<String, List<NewsSummary>> map) throws Exception {
                        List<NewsSummary> list;
                        if (id.endsWith(ApiConstants.HOUSE_ID)) {
                            list = map.get("北京");
                        } else {
                            list = map.get(id);
                        }
                        return Observable.fromIterable(null != list ? list : new ArrayList<>());
                    }
                })
                // 过滤
                .filter(newsSummary -> null != newsSummary && StringUtils.isNotBlank(newsSummary.getPostid()))
                // 去重
                .distinct(NewsSummary::getPostid)
                // 排序
                .toSortedList((newsSummary1, newsSummary2) -> newsSummary2.getPtime().compareTo(newsSummary1.getPtime()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    if (null != callback) {
                        callback.success(list);
                    }
                }, throwable -> {
                    if (null != callback) {
                        callback.failure(throwable);
                    }
                });
    }

    @SuppressLint("CheckResult")
    public static void getPhotoList(int size, int page, ResponseCallback<List<PhotoGirl>> callback) {
        getApi(HostType.GANK_GIRL_PHOTO).getPhotoList(size, page)
                .map(new Function<PhotoResBean, List<PhotoGirl>>() {
                    @Override
                    public List<PhotoGirl> apply(PhotoResBean photoResBean) throws Exception {
                        return photoResBean.getResults();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(girls -> {
                    if (null != callback) {
                        callback.success(girls);
                    }
                }, throwable -> {
                    if (null != callback) {
                        callback.failure(throwable);
                    }
                });
    }

    @SuppressLint("CheckResult")
    public static void getNewsDetail(String postId, ResponseCallback<NewsDetail> callback) {
        getApi(HostType.NETEASE_NEWS_VIDEO).getNewDetail(postId)
                .map(stringNewsDetailMap -> {
                    if (null == stringNewsDetailMap || null == stringNewsDetailMap.get(postId)) {
                        return NewsDetail.defaultNewsDetail();
                    }
                    return stringNewsDetailMap.get(postId);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newsDetail -> {
                    if (null != callback) {
                        callback.success(newsDetail);
                    }
                }, throwable -> {
                    if (null != callback) {
                        callback.failure(throwable);
                    }
                });
    }

    @SuppressLint("CheckResult")
    public static void getVideoList(String type, int startPage, ResponseCallback<List<VideoData>> callback) {
        getApi(HostType.NETEASE_NEWS_VIDEO).getVideoList(type, startPage)
                .flatMap(new Function<Map<String, List<VideoData>>, ObservableSource<VideoData>>() {
                    @Override
                    public ObservableSource<VideoData> apply(Map<String, List<VideoData>> map) throws Exception {
                        List<VideoData> list = map.get(type);
                        return Observable.fromIterable(null != list ? list : new ArrayList<>());
                    }
                })
                .distinct() // 去重
                // 排序
                .toSortedList((videoData1, videoData2) -> videoData2.getPtime().compareTo(videoData1.getPtime()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    if (null != callback) {
                        callback.success(list);
                    }
                }, throwable -> {
                    if (null != callback) {
                        callback.failure(throwable);
                    }
                });
    }
}
