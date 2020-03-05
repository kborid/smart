package com.kborid.smart.entity;

/**
 * des:视频分类
 * Created by xsf
 * on 2016.09.14:57
 */
public class VideoChannelBean {
    private String channelId;
    private String channelName;

    public VideoChannelBean(String channelId, String channelName){
        this.channelId=channelId;
        this.channelName=channelName;
    }
    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
}
