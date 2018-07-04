package com.pbn.org.news.model.common;

public class Image {
    private int width;
    private int height;
    private String url;

    public Image(int width, int height, String url) {
        this.width = width;
        this.height = height;
        this.url = url;
    }

    public Image(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
