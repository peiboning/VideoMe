package com.pbn.org.news.model.search;

/**
 * function:
 *
 * @author peiboning
 * @DATE 2018/10/30
 */
public class SearchRecodeModel {
    public static final int TYPE_SPLITE_LINE = 1;
    public static final int TYPE_TITLE = 2;
    public static final int TYPE_NORMAL = 3;

    private int type;
    private String content;

    public SearchRecodeModel(int type) {
        this(type, "");
    }

    public SearchRecodeModel(int type, String content) {
        this.type = type;
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public String getContent() {
        return content;
    }
}
