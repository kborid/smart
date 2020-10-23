package com.kborid.smart.network;

import android.annotation.SuppressLint;
import com.kborid.smart.R;
import com.kborid.smart.entity.NewsChannelBean;
import com.kborid.smart.entity.VideoChannelBean;
import com.kborid.smart.util.StringResUtil;
import com.orhanobut.logger.Logger;
import com.thunisoft.common.network.util.RxUtil;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ChannelLoader {

    /**
     * 加载新闻频道本地数据
     */
    public static Observable<List<NewsChannelBean>> loadNewsChannel() {
        return Observable.create((ObservableOnSubscribe<List<NewsChannelBean>>) emitter -> {
            Logger.i("thread: " + Thread.currentThread().getName());
            List<String> channelName = Arrays.stream(StringResUtil.getStringArray(R.array.news_channel_name_static)).collect(Collectors.toList());
            List<String> channelId = Arrays.stream(StringResUtil.getStringArray(R.array.news_channel_id_static)).collect(Collectors.toList());
            ArrayList<NewsChannelBean> newsChannelBeans = new ArrayList<>();
            for (int i = 0; i < channelName.size(); i++) {
                NewsChannelBean entity = new NewsChannelBean(channelName.get(i), channelId.get(i),
                        ApiConstants.getType(channelId.get(i)), i <= 5, i, true);
                newsChannelBeans.add(entity);
            }
            emitter.onNext(newsChannelBeans);
        }).compose(RxUtil.rxSchedulerHelper());
    }

    /**
     * 加载video频道本地数据
     */
    @SuppressLint("CheckResult")
    public static Observable<List<VideoChannelBean>> loadVideoChannel() {
        return Observable.create((ObservableOnSubscribe<List<VideoChannelBean>>) emitter -> {
            Logger.i("thread: " + Thread.currentThread().getName());
            List<String> channelName = Arrays.stream(StringResUtil.getStringArray(R.array.video_channel_name)).collect(Collectors.toList());
            List<String> channelId = Arrays.stream(StringResUtil.getStringArray(R.array.video_channel_id)).collect(Collectors.toList());
            ArrayList<VideoChannelBean> videoChannelBeans = new ArrayList<>();
            for (int i = 0; i < channelName.size(); i++) {
                VideoChannelBean entity = new VideoChannelBean(channelId.get(i), channelName.get(i));
                videoChannelBeans.add(entity);
            }
            emitter.onNext(videoChannelBeans);
        }).compose(RxUtil.rxSchedulerHelper());
    }
}
