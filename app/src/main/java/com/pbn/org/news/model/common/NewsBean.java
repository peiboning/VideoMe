package com.pbn.org.news.model.common;

import com.pbn.org.news.model.zixun.VideoModel;

import java.io.Serializable;
import java.util.List;

public class NewsBean implements Serializable{


    public static final int CONTENT_SOURCE_zixun = 1;
    public static final int CONTENT_SOURCE_2 = 2;
    public static final int CONTENT_SOURCE_Haokan = 3;
    public static final int CONTENT_SOURCE_sdk = 4;

    public final static int TYPE_ITEM_LIST_NEWS_ONE     = 1;
    public final static int TYPE_ITEM_LIST_NEWS_BIG     = 2;
    public final static int TYPE_ITEM_LIST_NEWS_THREE   = 3;
    public final static int TYPE_ITEM_LIST_NEWS_REFRESH = 4;
    public final static int TYPE_ITEM_LIST_VIDEO        = 5;

    public final static int TYPE_ITEM_TEXT = 6;
    public final static int TYPE_ITEM_SUMMARY = 7;
    public final static int TYPE_ITEM_LABEL = 8;
    public final static int TYPE_ITEM_FOCUSNEWS = 7;
    public final static int TYPE_ITEM_AD = 9;

    private int contentSource;//标识是那个源

    private String id;
    private int template;
    private String title;
    private List<Image> images;
    private String source;
    private String updateTime;
    private String detailUrl;
    private int commitNum;
    private List<VideoModel> videos;
    private int playCount;

    private String channelId;


    public int getContentSource() {
        return contentSource;
    }

    public void setContentSource(int contentSource) {
        this.contentSource = contentSource;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTemplate() {
        return template;
    }

    public void setTemplate(int template) {
        this.template = template;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public int getCommitNum() {
        return commitNum;
    }

    public void setCommitNum(int commitNum) {
        this.commitNum = commitNum;
    }

    public List<VideoModel> getVideos() {
        return videos;
    }

    public void setVideos(List<VideoModel> videos) {
        this.videos = videos;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
}
