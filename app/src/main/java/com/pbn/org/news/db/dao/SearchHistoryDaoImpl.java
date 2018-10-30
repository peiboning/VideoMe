package com.pbn.org.news.db.dao;

import com.pbn.org.news.db.model.SearchHistory;

import java.util.List;

/**
 * function:
 *
 * @author peiboning
 * @DATE 2018/10/30
 */
public class SearchHistoryDaoImpl {
    public static long insert(SearchHistory history){
        long id = DaoManager.getInstance().getDaoSession().getSearchHistoryDao()
                .insert(history);
        return id;
    }

    public static List<SearchHistory> getList(){
        return DaoManager.getInstance().getDaoSession().getSearchHistoryDao()
                .queryBuilder().list();
    }
}
