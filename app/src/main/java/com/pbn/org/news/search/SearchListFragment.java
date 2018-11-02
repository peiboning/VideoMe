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
import com.pbn.org.news.detail.VideoDetailActivity;
import com.pbn.org.news.model.haokan.HaokanVideo;
import com.pbn.org.news.model.haokan.SearchResult;
import com.pbn.org.news.model.haokan.SearchVideo;
import com.pbn.org.news.mvp.presenter.SearchResultPresenter;
import com.pbn.org.news.mvp.view.ISearchResultView;
import com.pbn.org.news.view.NewsToast;
import com.pbn.org.news.view.ProgressView;

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
    private ProgressView mProgress;

    @Override
    protected SearchResultPresenter createPresenter() {
        return new SearchResultPresenter();
    }

    @Override
    protected void initView(View view) {
        listView = view.findViewById(R.id.search_result_lv);
        mProgress = view.findViewById(R.id.load_progress);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new SearchResultAdapter(getContext());
        mAdapter.setDetailPage(VideoDetailActivity.SOURCE_SEARCH);
        listView.setAdapter(mAdapter);
        Bundle bundle = getArguments();
        if(null != bundle){
            searchWord = bundle.getString(KEY_WORD, "");
            NewsToast.showSystemToast(searchWord);
            if(!TextUtils.isEmpty(searchWord)){
                presenter.searchByWord(searchWord, 0);
                listView.setVisibility(View.INVISIBLE);
                mProgress.setVisibility(View.VISIBLE);
                mProgress.start();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

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

                listView.setVisibility(View.VISIBLE);
                mProgress.stop();
                mProgress.setVisibility(View.INVISIBLE);
            }else{

            }

        }else{

        }
    }
}
