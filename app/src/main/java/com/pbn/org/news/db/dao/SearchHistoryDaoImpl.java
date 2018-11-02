package com.pbn.org.news.db.dao;

import com.pbn.org.news.db.model.SearchHistory;
import com.pbn.org.news.greendao.gen.SearchHistoryDao;
import com.pbn.org.news.model.search.SearchRecodeModel;

import org.greenrobot.greendao.query.DeleteQuery;
import org.greenrobot.greendao.query.QueryBuilder;

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

    public static void deleteByModel(SearchHistory model){
        QueryBuilder<SearchHistory> builder = DaoManager.getInstance().getDaoSession().getSearchHistoryDao()
                .queryBuilder();

        DeleteQuery<SearchHistory> deleteQuery = builder.where(SearchHistoryDao.Properties.Title.eq(model.getTitle())).buildDelete();

        deleteQuery.executeDeleteWithoutDetachingEntities();
    }
}
