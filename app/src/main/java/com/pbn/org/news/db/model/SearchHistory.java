package com.pbn.org.news.db.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * function:
 * 搜索历史记录
 * @author peiboning
 * @DATE 2018/10/30
 */
@Entity
public class SearchHistory {
    @Id(autoincrement = true)
    private Long _id;
    private String title;
    @Generated(hash = 843425241)
    public SearchHistory(Long _id, String title) {
        this._id = _id;
        this.title = title;
    }
    @Generated(hash = 1905904755)
    public SearchHistory() {
    }
    public Long get_id() {
        return this._id;
    }
    public void set_id(Long _id) {
        this._id = _id;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
}
