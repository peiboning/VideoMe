package com.pbn.org.news.mvp.presenter;

import com.pbn.org.news.base.BasePresenter;
import com.pbn.org.news.model.haokan.HKRequestParams;
import com.pbn.org.news.model.haokan.SearchResult;
import com.pbn.org.news.mvp.view.ISearchResultView;
import com.pbn.org.news.net.RetrofitClient;
import com.pbn.org.news.net.api.HAOKANAPI;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * function:
 *
 * @author peiboning
 * @DATE 2018/10/31
 */
public class SearchResultPresenter extends BasePresenter<ISearchResultView>{

    public void searchByWord(String word, int page){
        RetrofitClient.getInstance().getHaokanRetrofit().create(HAOKANAPI.class)
                .getSearchResult(HKRequestParams.getQueryMap(), HKRequestParams.getSearchBodyMap(word, page))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SearchResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SearchResult searchResult) {
                        getView().updateResult(searchResult);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
