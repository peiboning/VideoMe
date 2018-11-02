package com.pbn.org.news.mvp.presenter;

import com.pbn.org.news.base.BasePresenter;
import com.pbn.org.news.db.dao.SearchHistoryDaoImpl;
import com.pbn.org.news.db.model.SearchHistory;
import com.pbn.org.news.greendao.gen.SearchHistoryDao;
import com.pbn.org.news.model.search.SearchRecodeModel;
import com.pbn.org.news.mvp.view.ISearchRecordView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * function:
 *
 * @author peiboning
 * @DATE 2018/10/30
 */
public class SearchRecordPresenter extends BasePresenter<ISearchRecordView>{

    public void loadHistory(){
        Observable.create(new ObservableOnSubscribe<List<SearchHistory>>() {
            @Override
            public void subscribe(ObservableEmitter<List<SearchHistory>> e) throws Exception {
                List<SearchHistory> list = SearchHistoryDaoImpl.getList();
                e.onNext(list);
                e.onComplete();
            }
        }).map(new Function<List<SearchHistory>, List<SearchRecodeModel>>() {
            @Override
            public List<SearchRecodeModel> apply(List<SearchHistory> searchHistories) throws Exception {
                List<SearchRecodeModel> list = new ArrayList<>();
                if(null != searchHistories && searchHistories.size() > 0){
                    for(SearchHistory h:searchHistories ){
                        SearchRecodeModel model = new SearchRecodeModel(SearchRecodeModel.TYPE_NORMAL, h.getTitle());
                        list.add(model);
                    }
                    if(list.size() % 2 != 0){
                        list.add(new SearchRecodeModel(SearchRecodeModel.TYPE_NORMAL, ""));
                    }
                }
                return list;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<SearchRecodeModel>>() {
                    @Override
                    public void accept(List<SearchRecodeModel> list) throws Exception {
                        getView().updateHistory(list);
                    }
                });
    }

    public void deleteHistory(final SearchRecodeModel model) {
        Observable.create(new ObservableOnSubscribe<SearchRecodeModel>() {
            @Override
            public void subscribe(ObservableEmitter<SearchRecodeModel> e) throws Exception {
                e.onNext(model);
                e.onComplete();
            }
        }).map(new Function<SearchRecodeModel, SearchHistory>() {
            @Override
            public SearchHistory apply(SearchRecodeModel model) throws Exception {
                SearchHistory history = new SearchHistory();
                history.setTitle(model.getContent());
                SearchHistoryDaoImpl.deleteByModel(history);
                return history;
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }
}
