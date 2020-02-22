package com.kborid.smart.network;

import android.annotation.SuppressLint;
import android.util.SparseArray;

import com.kborid.smart.PRJApplication;
import com.kborid.smart.R;
import com.kborid.smart.entity.NewsChannelBean;
import com.kborid.smart.entity.NewsSummary;
import com.orhanobut.logger.Logger;
import com.thunisoft.common.network.OkHttpClientFactory;
import com.thunisoft.common.network.callback.ResponseCallback;
import com.thunisoft.common.network.func.ApiException;
import com.thunisoft.common.network.func.ErrorAction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
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
                    NewsChannelBean entity = new NewsChannelBean(channelName.get(i), channelId.get(i)
                            , ApiConstants.getType(channelId.get(i)), i <= 5, i, true);
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
    public static void getNewsList(String type, String id, int startPage, ResponseCallback<Map<String, List<NewsSummary>>> callback) {
        ApiManager.getApi(HostType.NETEASE_NEWS_VIDEO).getNewsList(type, id, startPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Map<String, List<NewsSummary>>>() {
                    @Override
                    public void accept(Map<String, List<NewsSummary>> map) throws Exception {
                        if (null != callback) {
                            callback.success(map);
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
//                .flatMap(new Function<Map<String, List<NewsSummary>>, ObservableSource<? extends R>>() {
//                    @Override
//                    public Observable<NewsSummary> call(Map<String, List<NewsSummary>> map) {
//                        if (id.endsWith(ApiConstants.HOUSE_ID)) {
//                            // 房产实际上针对地区的它的id与返回key不同
//                            return Observable.from(map.get("北京"));
//                        }
//                        return Observable.from(map.get(id));
//                    }
//                })
//                //转化时间
//                .map(new Func1<NewsSummary, NewsSummary>() {
//                    @Override
//                    public NewsSummary call(NewsSummary newsSummary) {
//                        String ptime = TimeUtil.formatDate(newsSummary.getPtime());
//                        newsSummary.setPtime(ptime);
//                        return newsSummary;
//                    }
//                })
//                .distinct()//去重
//                .toSortedList(new Func2<NewsSummary, NewsSummary, Integer>() {
//                    @Override
//                    public Integer call(NewsSummary newsSummary, NewsSummary newsSummary2) {
//                        return newsSummary2.getPtime().compareTo(newsSummary.getPtime());
//                    }
//                })
//                //声明线程调度
//                .compose(RxSchedulers.<List<NewsSummary>>io_main());
    }
}
