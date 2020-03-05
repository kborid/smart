package com.kborid.smart.network;

import android.annotation.SuppressLint;

import com.kborid.smart.PRJApplication;
import com.kborid.smart.R;
import com.kborid.smart.entity.NewsChannelBean;
import com.kborid.smart.entity.VideoChannelBean;
import com.orhanobut.logger.Logger;
import com.thunisoft.common.network.callback.ResponseCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ChannelLoader {

    @SuppressLint("CheckResult")
    public static void loadNewsChannel(ResponseCallback<List<NewsChannelBean>> callback) {
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
                .subscribe(o -> {
                    if (null != callback) {
                        callback.success(o);
                    }
                }, throwable -> {
                    if (null != callback) {
                        callback.failure(throwable);
                    }
                });
    }

    @SuppressLint("CheckResult")
    public static void loadVideoChannel(ResponseCallback<List<VideoChannelBean>> callback) {
        Observable.create(new ObservableOnSubscribe<List<VideoChannelBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<VideoChannelBean>> emitter) throws Exception {
                Logger.i("thread: " + Thread.currentThread().getName());
                List<String> channelName = Arrays.asList(PRJApplication.getInstance().getResources().getStringArray(R.array.video_channel_name));
                List<String> channelId = Arrays.asList(PRJApplication.getInstance().getResources().getStringArray(R.array.video_channel_id));
                ArrayList<VideoChannelBean> videoChannelBeans = new ArrayList<>();
                for (int i = 0; i < channelName.size(); i++) {
                    VideoChannelBean entity = new VideoChannelBean(channelId.get(i), channelName.get(i));
                    videoChannelBeans.add(entity);
                }
                emitter.onNext(videoChannelBeans);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {
                    if (null != callback) {
                        callback.success(o);
                    }
                }, throwable -> {
                    if (null != callback) {
                        callback.failure(throwable);
                    }
                });
    }
}
