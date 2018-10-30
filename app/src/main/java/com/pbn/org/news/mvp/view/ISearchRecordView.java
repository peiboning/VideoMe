package com.pbn.org.news.mvp.view;

import com.pbn.org.news.model.search.SearchRecodeModel;

import java.util.List;

/**
 * function:
 *
 * @author peiboning
 * @DATE 2018/10/30
 */
public interface ISearchRecordView {
    void updateHistory(List<SearchRecodeModel> list);
    void updateHot(List<SearchRecodeModel> list);
}