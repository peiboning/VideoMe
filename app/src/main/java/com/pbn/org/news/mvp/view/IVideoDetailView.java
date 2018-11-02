package com.pbn.org.news.mvp.view;

import com.pbn.org.news.model.haokan.SearchVideo;

import java.util.List;

public interface IVideoDetailView {
    void updateRelateVideo(List<SearchVideo> list);
    void updateRelateError();
}
