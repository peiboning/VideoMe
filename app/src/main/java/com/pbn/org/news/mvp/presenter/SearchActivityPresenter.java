package com.pbn.org.news.mvp.presenter;

import android.util.Log;

import com.pbn.org.news.base.BasePresenter;
import com.pbn.org.news.db.dao.SearchHistoryDaoImpl;
import com.pbn.org.news.db.model.SearchHistory;
import com.pbn.org.news.mvp.view.ISearchActivityView;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * function:
 *
 * @author peiboning
 * @DATE 2018/10/30
 */
public class SearchActivityPresenter extends BasePresenter<ISearchActivityView>{
    public void updateSearchHistory(final String title){
        Observable.create(new ObservableOnSubscribe<SearchHistory>() {
            @Override
            public void subscribe(ObservableEmitter<SearchHistory> e) throws Exception {
                SearchHistory history = new SearchHistory();
                history.setTitle(title);
                long id = SearchHistoryDaoImpl.insert(history);
                Log.e("SearchActivityPresenter", "insert id is " + id);
                e.onNext(history);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}
