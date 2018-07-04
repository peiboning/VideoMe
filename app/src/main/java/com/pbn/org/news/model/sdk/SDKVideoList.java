package com.pbn.org.news.model.sdk;

import java.util.List;

public class SDKVideoList {
    private int count;
    private String channelName;
    private int channelId;
    private List<SDKDataModel> videoList;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public List<SDKDataModel> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<SDKDataModel> videoList) {
        this.videoList = videoList;
    }
}
