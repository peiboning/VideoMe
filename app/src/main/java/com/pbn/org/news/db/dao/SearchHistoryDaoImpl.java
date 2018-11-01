package com.pbn.org.news.db.dao;

import com.pbn.org.news.db.model.SearchHistory;
import com.pbn.org.news.greendao.gen.SearchHistoryDao;

import java.util.List;

/**
 * function:
 *
 * @author peiboning
 * @DATE 2018/10/30
 */
public class SearchHistoryDaoImpl {
    public static long insert(SearchHistory history){
        long id = -1;
        if(null == getContentByContent(history.getTitle())){
            id = DaoManager.getInstance().getDaoSession().getSearchHistoryDao()
                    .insert(history);
        }
        return id;
    }

    public static List<SearchHistory> getList(){
        return DaoManager.getInstance().getDaoSession().getSearchHistoryDao()
                .queryBuilder().list();
    }

    public static SearchHistory getContentByContent(String content){
        return DaoManager.getInstance().getDaoSession().getSearchHistoryDao()
                .queryBuilder()
                .where(SearchHistoryDao.Properties.Title.eq(content)).unique();
    }
}
