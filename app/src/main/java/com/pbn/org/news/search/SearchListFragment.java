package com.pbn.org.news.search;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.pbn.org.news.R;
import com.pbn.org.news.adapter.SearchResultAdapter;
import com.pbn.org.news.base.BasePresenter;
import com.pbn.org.news.base.MVPBaseFragment;
import com.pbn.org.news.model.haokan.HaokanVideo;
import com.pbn.org.news.model.haokan.SearchResult;
import com.pbn.org.news.model.haokan.SearchVideo;
import com.pbn.org.news.mvp.presenter.SearchResultPresenter;
import com.pbn.org.news.mvp.view.ISearchResultView;
import com.pbn.org.news.view.NewsToast;

import java.util.List;

/**
 * @author peiboning
 * @DATE 2018/10/29
 */
public class SearchListFragment extends MVPBaseFragment<ISearchResultView, SearchResultPresenter> implements ISearchResultView{
    public static final String KEY_WORD = "key_words";
    private RecyclerView listView;
    private String searchWord;
    private SearchResultAdapter mAdapter;

    @Override
    protected SearchResultPresenter createPresenter() {
        return new SearchResultPresenter();
    }

    @Override
    protected void initView(View view) {
        listView = view.findViewById(R.id.search_result_lv);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new SearchResultAdapter(getContext());
        listView.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = getArguments();
        if(null != bundle){
            searchWord = bundle.getString(KEY_WORD, "");
            NewsToast.showSystemToast(searchWord);
            if(!TextUtils.isEmpty(searchWord)){
                presenter.searchByWord(searchWord, 0);
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search_result;
    }


    @Override
    public void updateResult(SearchResult result) {
        if(null != result.getSearch()){
            SearchResult.Data data = result.getSearch().getData();
            if(null != data){
                List<SearchVideo> list = data.getList();
                int hasMore = data.getHas_more();
                mAdapter.updateResult(list);
            }

        }
    }
}
