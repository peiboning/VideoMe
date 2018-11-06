package com.pbn.org.news.model.zixun;

import java.io.Serializable;

public class VideoModel implements Serializable{
    private String url;
    private float duration;
    private int size;
    private String vid;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
