package com.pbn.org.news.search.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.pbn.org.news.R;
import com.pbn.org.news.adapter.SearchRecordAdapter;
import com.pbn.org.news.base.MVPBaseFragment;
import com.pbn.org.news.model.haokan.HotWorld;
import com.pbn.org.news.model.search.SearchRecodeModel;
import com.pbn.org.news.mvp.presenter.SearchRecordPresenter;
import com.pbn.org.news.mvp.view.ISearchRecordView;
import com.pbn.org.news.search.SearchActivity;
import com.pbn.org.news.view.SearchHistoryItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * function:
 *
 * @author peiboning
 * @DATE 2018/10/30
 */
public class SearchRecordFragment extends MVPBaseFragment<ISearchRecordView, SearchRecordPresenter> implements ISearchRecordView{
    private RecyclerView mSearchHistoryListView;
    private SearchRecordAdapter mAdapter;
    private GridLayoutManager mLayoutManager;
    private HotWorld hotWorld;
    @Override
    protected SearchRecordPresenter createPresenter() {
        return new SearchRecordPresenter();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(null != bundle){
            Object o = bundle.getSerializable(SearchActivity.KEY_HOT_WRODS);
            if(o instanceof HotWorld){
                hotWorld = (HotWorld) o;
            }
        }
    }

    @Override
    protected void initView(View view) {
        mSearchHistoryListView = view.findViewById(R.id.search_history_lv);
        mAdapter = new SearchRecordAdapter(getActivity());
        mSearchHistoryListView.setAdapter(mAdapter);
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int type = mAdapter.getItemViewType(position);
                int count = SearchRecodeModel.TYPE_NORMAL == type ? 1 : 2;
                return count;
            }
        });

        mSearchHistoryListView.addItemDecoration(new SearchHistoryItemDecoration());

        mSearchHistoryListView.setLayoutManager(mLayoutManager);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search_record;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.loadHistory();
    }

    @Override
    public void updateHistory(List<SearchRecodeModel> list) {
        if(null != list){
            mAdapter.updateHistory(list);
        }

        if(null != hotWorld){
            List<SearchRecodeModel> hotList = new ArrayList<>();
            List<String> others = hotWorld.getLikes();
            if(null != others && others.size() > 0){
                for(String s : others){
                    SearchRecodeModel model = new SearchRecodeModel(SearchRecodeModel.TYPE_NORMAL, s);
                    hotList.add(model);
                }
            }
            hotList.add(0,new SearchRecodeModel(SearchRecodeModel.TYPE_NORMAL, hotWorld.getHotWords()));
            if(hotList.size() % 2 != 0){
                hotList.add(new SearchRecodeModel(SearchRecodeModel.TYPE_NORMAL, ""));
            }
            updateHot(hotList);
        }
    }

    @Override
    public void updateHot(List<SearchRecodeModel> list) {
        if(null != list){
            mAdapter.updateHot(list);
        }
    }
}
