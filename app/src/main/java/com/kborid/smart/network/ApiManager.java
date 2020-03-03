package com.kborid.smart.network;

import android.annotation.SuppressLint;
import android.util.SparseArray;

import com.kborid.smart.PRJApplication;
import com.kborid.smart.R;
import com.kborid.smart.entity.NewsChannelBean;
import com.kborid.smart.entity.NewsDetail;
import com.kborid.smart.entity.NewsSummary;
import com.kborid.smart.entity.PhotoGirl;
import com.kborid.smart.entity.PhotoResBean;
import com.orhanobut.logger.Logger;
import com.thunisoft.common.network.OkHttpClientFactory;
import com.thunisoft.common.network.callback.ResponseCallback;
import com.thunisoft.common.network.func.ApiException;
import com.thunisoft.common.network.func.ErrorAction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

public class ApiManager {

    private static SparseArray<RequestApi> apiSparseArray = new SparseArray<>();

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

    private static RequestApi getApi(int hostType) {
        RequestApi requestApi = apiSparseArray.get(hostType);
        if (requestApi == null) {
            requestApi = getRetrofit(hostType).create(RequestApi.class);
            apiSparseArray.put(hostType, requestApi);
        }
        return requestApi;
    }

    @SuppressLint("CheckResult")
    public static void loadMainChannel(ResponseCallback<List<NewsChannelBean>> callback) {
        Observable.create(new ObservableOnSubscribe<List<NewsChannelBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<NewsChannelBean>> emitter) throws Exception {
                Logger.i("thread: " + Thread.currentThread().getName());
                List<String> channelName = Arrays.asList(PRJApplication.getInstance().getResources().getStringArray(R.array.news_channel_name_static));
                List<String> channelId = Arrays.asList(PRJApplication.getInstance().getResources().getStringArray(R.array.news_channel_id_static));
                ArrayList<NewsChannelBean> newsChannelBeans = new ArrayList<>();
                for (int i = 0; i < channelName.size(); i++) {
                    NewsChannelBean entity = new NewsChannelBean(channelName.get(i), channelId.get(i),
                            ApiConstants.getType(channelId.get(i)), i <= 5, i, true);
                    newsChannelBeans.add(entity);
                }
                emitter.onNext(newsChannelBeans);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<NewsChannelBean>>() {
                    @Override
                    public void accept(List<NewsChannelBean> o) throws Exception {
                        if (null != callback) {
                            callback.success(o);
                        }
                    }
                }, new ErrorAction() {
                    @Override
                    protected void call(ApiException e) {
                        if (null != callback) {
                            callback.failure(e);
                        }
                    }
                });
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
                // 格式化时间
//                .map(new Function<NewsSummary, NewsSummary>() {
//                    @Override
//                    public NewsSummary apply(NewsSummary newsSummary) throws Exception {
//                        String ptime = DateUtils.formatDate(newsSummary.getPtime());
//                        newsSummary.setPtime(ptime);
//                        return newsSummary;
//                    }
//                })
                .distinct() // 去重
                // 排序
                .toSortedList(new Comparator<NewsSummary>() {
                    @Override
                    public int compare(NewsSummary newsSummary1, NewsSummary newsSummary2) {
                        return newsSummary2.getPtime().compareTo(newsSummary1.getPtime());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<NewsSummary>>() {
                    @Override
                    public void accept(List<NewsSummary> list) throws Exception {
                        if (null != callback) {
                            callback.success(list);
                        }
                    }
                }, new ErrorAction() {
                    @Override
                    protected void call(ApiException e) {
                        if (null != callback) {
                            callback.failure(e);
                        }
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
                .subscribe(new Consumer<List<PhotoGirl>>() {
                    @Override
                    public void accept(List<PhotoGirl> girls) throws Exception {
                        if (null != callback) {
                            callback.success(girls);
                        }
                    }
                }, new ErrorAction() {
                    @Override
                    protected void call(ApiException e) {
                        if (null != callback) {
                            callback.failure(e);
                        }
                    }
                });
    }

    @SuppressLint("CheckResult")
    public static void getNewsDetail(String postId, ResponseCallback<NewsDetail> callback) {
        getApi(HostType.NETEASE_NEWS_VIDEO).getNewDetail(postId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<NewsDetail>() {
                    @Override
                    public void accept(NewsDetail newsDetail) throws Exception {
                        if (null != callback) {
                            callback.success(newsDetail);
                        }
                    }
                }, new ErrorAction() {
                    @Override
                    protected void call(ApiException e) {
                        if (null != callback) {
                            callback.failure(e);
                        }
                    }
                });
    }
}
