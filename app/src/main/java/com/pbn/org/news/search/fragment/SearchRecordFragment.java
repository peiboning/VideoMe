package com.pbn.org.news.search.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.pbn.org.news.R;
import com.pbn.org.news.adapter.SearchRecordAdapter;
import com.pbn.org.news.base.MVPBaseFragment;
import com.pbn.org.news.model.haokan.HotWorld;
import com.pbn.org.news.model.search.SearchRecodeModel;
import com.pbn.org.news.mvp.presenter.SearchRecordPresenter;
import com.pbn.org.news.mvp.view.ISearchRecordView;
import com.pbn.org.news.search.ISearchActivity;
import com.pbn.org.news.search.SearchActivity;
import com.pbn.org.news.view.NewsToast;
import com.pbn.org.news.view.SearchHistoryItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * function:
 *
 * @author peiboning
 * @DATE 2018/10/30
 */
public class SearchRecordFragment extends MVPBaseFragment<ISearchRecordView, SearchRecordPresenter> implements ISearchRecordView, SearchRecordAdapter.OnItemClickListener{
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
        mAdapter.setOnItemClickListener(this);
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
    public void onStart() {
        super.onStart();
        Log.e("SearchRecordFragment", "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.loadHistory();
        Log.e("SearchRecordFragment", "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("SearchRecordFragment", "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("SearchRecordFragment", "onStop");
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

    @Override
    public void onItemClick(String content) {
        if(!TextUtils.isEmpty(content)){
            if(getActivity() instanceof ISearchActivity){
                ((ISearchActivity) getActivity()).search(content);
            }
        }else{
            NewsToast.showSystemToast("click item content is empty");
        }
    }

    @Override
    public void onExpandHistory() {
        if(mAdapter.isNeedShowPartHistory()){
            if(mAdapter.isExpandHistory()){
                mAdapter.showPartHistory();
            }else{
                mAdapter.showAllHistory();
            }
        }
    }

    @Override
    public void onHistoryEditor() {
        mAdapter.edit();
    }

    @Override
    public void onHistoryEditOver() {
        mAdapter.editOver();
    }

    @Override
    public void onDelete(SearchRecodeModel model) {
        mAdapter.delete(model);
    }
}
