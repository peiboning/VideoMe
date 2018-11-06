package com.pbn.org.news.model.haokan;

/**
 * function:
 *
 * @author peiboning
 * @DATE 2018/10/31
 */
public class SearchVideo {
    private String title;
    private String video_src;
    private String cover_src;
    private String media_id;
    private String author;
    private String publishTimeText;
    private String playcntText;
    private int duration;

    private int contentSource;
    private String channel;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideo_src() {
        return video_src;
    }

    public void setVideo_src(String video_src) {
        this.video_src = video_src;
    }

    public String getCover_src() {
        return cover_src;
    }

    public void setCover_src(String cover_src) {
        this.cover_src = cover_src;
    }

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishTimeText() {
        return publishTimeText;
    }

    public void setPublishTimeText(String publishTimeText) {
        this.publishTimeText = publishTimeText;
    }

    public String getPlaycntText() {
        return playcntText;
    }

    public void setPlaycntText(String playcntText) {
        this.playcntText = playcntText;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getContentSource() {
        return contentSource;
    }

    public void setContentSource(int contentSource) {
        this.contentSource = contentSource;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
