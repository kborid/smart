package com.kborid.smart.network;

import android.annotation.SuppressLint;

import com.kborid.smart.PRJApplication;
import com.kborid.smart.R;
import com.kborid.smart.entity.NewsChannelBean;
import com.orhanobut.logger.Logger;
import com.thunisoft.common.network.OkHttpClientFactory;
import com.thunisoft.common.network.callback.ResponseCallback;
import com.thunisoft.common.network.func.ApiException;
import com.thunisoft.common.network.func.ErrorAction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

public class Api {
    private static final String baseUrl = "http://www.publicobject.com/";
    private static RequestApi requestApi;

    private Api() {
    }

    private static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .client(OkHttpClientFactory.newOkHttpClient())
                .baseUrl(baseUrl)// Base URL
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private static RequestApi getApi() {
        if (requestApi == null) {
            requestApi = getRetrofit().create(RequestApi.class);
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
}
