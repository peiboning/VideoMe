package com.pbn.org.news.model.haokan;

import java.io.Serializable;
import java.util.List;

/**
 * function:
 *
 * @author peiboning
 * @DATE 2018/10/11
 */
public class HotWorld implements Serializable{
    private String hotWords;
    private String title;
    private List<String> likes;

    public String getHotWords() {
        return hotWords;
    }

    public void setHotWords(String hotWords) {
        this.hotWords = hotWords;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getLikes() {
        return likes;
    }

    public void setLikes(List<String> likes) {
        this.likes = likes;
    }
}


