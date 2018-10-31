package com.pbn.org.news.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.pbn.org.news.R;
import com.pbn.org.news.model.search.SearchRecodeModel;
import com.pbn.org.news.vh.search.NoramlVH;
import com.pbn.org.news.vh.search.SpliteVH;
import com.pbn.org.news.vh.search.TitleVH;

import java.util.ArrayList;
import java.util.List;

/**
 * function:
 *
 * @author peiboning
 * @DATE 2018/10/30
 */
public class SearchRecordAdapter extends RecyclerView.Adapter{
    private List<SearchRecodeModel> history;
    private List<SearchRecodeModel> hot;
    private Context mContext;
    private SearchRecodeModel spliteModel;
    private SearchRecodeModel historyTitle;
    private SearchRecodeModel hotTitle;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(View view);
    }

    public SearchRecordAdapter(Context context){
        history = new ArrayList<>();
        hot = new ArrayList<>();
        mContext = context;
        spliteModel = new SearchRecodeModel(SearchRecodeModel.TYPE_SPLITE_LINE);
        historyTitle = new SearchRecodeModel(SearchRecodeModel.TYPE_TITLE, "历史记录");
        hotTitle = new SearchRecodeModel(SearchRecodeModel.TYPE_TITLE, "热搜");
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mOnItemClickListener = listener;
    }

    public void updateHistory(List<SearchRecodeModel> list){
        if(null != list && list.size() > 0){
            history = new ArrayList<>(list);
            history.add(0, historyTitle);
            history.add(0, spliteModel);
            notifyItemRangeChanged(0, history.size());
        }
    }
    public void updateHot(List<SearchRecodeModel> list){
        if(null != list && list.size() > 0){
            hot = new ArrayList<>(list);
            hot.add(0, hotTitle);
            hot.add(0, spliteModel);
            notifyItemRangeChanged(history.size(), history.size() + history.size());
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder VH= null;
        if(SearchRecodeModel.TYPE_NORMAL == viewType){
            View view = View.inflate(mContext, R.layout.search_record_normal_item, null);
            VH = new NoramlVH(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(null != mOnItemClickListener){
                        mOnItemClickListener.onItemClick(v);
                    }
                }
            });
        }else if(SearchRecodeModel.TYPE_SPLITE_LINE == viewType){
            View view = View.inflate(mContext, R.layout.search_record_splite_item, null);
            VH = new SpliteVH(view);
        }else if(SearchRecodeModel.TYPE_TITLE == viewType){
            View view = View.inflate(mContext, R.layout.search_record_title_item, null);
            VH = new TitleVH(view);
        }else{
            throw new RuntimeException("not support viewType:" + viewType);
        }
        return VH;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SearchRecodeModel model = null;
        if(position >= history.size()){
            model = hot.get(position - history.size());
        }else{
            model = history.get(position);
        }

        if(holder instanceof NoramlVH){
            ((NoramlVH) holder).bindview(model.getContent());
        }else if(holder instanceof TitleVH){
            ((TitleVH) holder).bindView(model.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return history.size() + hot.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(position >= history.size()){
            return hot.get(position - history.size()).getType();
        }
        return history.get(position).getType();
    }

    public String getItemContent(int position){
        if(position >= history.size()){
            return hot.get(position - history.size()).getContent();
        }
        return history.get(position).getContent();
    }


}
