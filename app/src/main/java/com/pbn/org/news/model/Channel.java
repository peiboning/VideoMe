package com.pbn.org.news.model;

import java.io.Serializable;

public class Channel implements Serializable{
    public static final int TYPE_MY = 1;
    public static final int TYPE_MY_CHANNEL = 3;
    public static final int TYPE_OTHER = 2;
    public static final int TYPE_OTHER_CHANNEL = 4;
    public String Title;
    public String TitleCode;
    private int itemType;
    private int quickCode;

    public Channel(String title, String titleCode) {
        this(3, title, titleCode);
    }

    public Channel(int type, String title, String titleCode) {
        this.itemType = type;
        this.Title = title;
        this.TitleCode = titleCode;
    }

    public int getQuickCode() {
        return quickCode;
    }

    public void setQuickCode(int quickCode) {
        this.quickCode = quickCode;
    }

    public String getTitle() {
        return this.Title;
    }

    public void setTitle(String str) {
        this.Title = str;
    }

    public String getTitleCode() {
        return this.TitleCode;
    }

    public void setTitleCode(String str) {
        this.TitleCode = str;
    }

    public void setItemType(int i) {
        this.itemType = i;
    }

    public int getItemType() {
        return this.itemType;
    }
}
