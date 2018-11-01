package com.pbn.org.news.model.search;

/**
 * function:
 *
 * @author peiboning
 * @DATE 2018/10/30
 */
public class SearchRecodeModel {
    public static final int TYPE_SPLITE_LINE = 1;
    public static final int TYPE_HISTORY_TITLE = 2;
    public static final int TYPE_HOT_TITLE = 3;
    public static final int TYPE_NORMAL = 4;

    public static final int STATUS_EDITOR = 1;
    public static final int STATUS_NORMAL = 0;

    private int type;
    private String content;
    private int status;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
