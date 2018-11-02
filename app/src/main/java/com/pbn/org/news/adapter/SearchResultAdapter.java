package com.pbn.org.news.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.pbn.org.news.R;
import com.pbn.org.news.model.haokan.SearchVideo;
import com.pbn.org.news.vh.search.SearchResultVH;

import java.util.ArrayList;
import java.util.List;

/**
 * function:
 *
 * @author peiboning
 * @DATE 2018/10/31
 */
public class SearchResultAdapter extends RecyclerView.Adapter{
    private List<SearchVideo> datas = new ArrayList<>();
    private Context mContext;
    private int from;

    public SearchResultAdapter(Context context){
        mContext = context;
    }

    public void updateResult(List<SearchVideo> list){
        datas = new ArrayList<>(list);
        notifyDataSetChanged();
    }

    public void setDetailPage(int from){
        this.from = from;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.item_search_video, null);
        SearchResultVH VH = new SearchResultVH(view, from);

        return VH;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof SearchResultVH){
            ((SearchResultVH) holder).bindView(datas.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
