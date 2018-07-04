package com.pbn.org.news.mvp.view;

import com.pbn.org.news.model.common.NewsBean;

import java.util.List;

public interface INewsListView {
    void updateNewsList(List<NewsBean> news, boolean isLoadMore);
    void requestNews();
}
