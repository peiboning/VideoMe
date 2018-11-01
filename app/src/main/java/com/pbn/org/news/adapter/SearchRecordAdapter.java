package com.pbn.org.news.adapter;

import android.app.SearchManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.pbn.org.news.R;
import com.pbn.org.news.model.search.SearchRecodeModel;
import com.pbn.org.news.vh.search.NoramlVH;
import com.pbn.org.news.vh.search.SpliteVH;
import com.pbn.org.news.vh.search.TitleVH;

import java.util.ArrayList;
import java.util.Collections;
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

    private List<SearchRecodeModel> partHistory;
    private List<SearchRecodeModel> allHistory;

    private Context mContext;
    private SearchRecodeModel spliteModel;
    private SearchRecodeModel historyTitle;
    private SearchRecodeModel hotTitle;
    private OnItemClickListener mOnItemClickListener;
    private boolean isExpandHistory;
    private boolean isEditable;

    public interface OnItemClickListener{
        void onItemClick(String content);
        void onExpandHistory();
        void onHistoryEditor();
        void onHistoryEditOver();
        void onDelete(SearchRecodeModel model);
    }

    public SearchRecordAdapter(Context context){
        history = new ArrayList<>();
        hot = new ArrayList<>();
        partHistory = new ArrayList<>();
        allHistory = new ArrayList<>();
        mContext = context;
        spliteModel = new SearchRecodeModel(SearchRecodeModel.TYPE_SPLITE_LINE);
        historyTitle = new SearchRecodeModel(SearchRecodeModel.TYPE_HISTORY_TITLE, "历史记录");
        hotTitle = new SearchRecodeModel(SearchRecodeModel.TYPE_HOT_TITLE, "热搜");
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mOnItemClickListener = listener;
    }

    public void updateHistory(List<SearchRecodeModel> list, boolean isShowHistory){
        if(null != list && list.size() > 0){
            partHistory.clear();
            allHistory.clear();
            if(list.size() > 4){
                for(int i = 0;i<4;i++){
                    partHistory.add(list.get(i));
                }
            }

            allHistory = new ArrayList<>(list);

            if(partHistory.size() > 0 && isShowHistory){
                history = new ArrayList<>(partHistory);
            }else{
                history = new ArrayList<>(allHistory);
            }
            history.add(0, historyTitle);
            history.add(0, spliteModel);
            notifyDataSetChanged();
        }
    }
    public void updateHistory(List<SearchRecodeModel> list){
        updateHistory(list, true);
    }

    public boolean isNeedShowPartHistory(){
        return partHistory.size() > 0;
    }

    public void edit(){
        isEditable = true;
        for(SearchRecodeModel model : allHistory){
            if(!TextUtils.isEmpty(model.getContent())){
                model.setStatus(SearchRecodeModel.STATUS_EDITOR);
            }
        }
        if(isNeedShowPartHistory()){
            showAllHistory();
        }else{
            history = new ArrayList<>(allHistory);
            history.add(0, historyTitle);
            history.add(0, spliteModel);
            notifyDataSetChanged();
        }
    }

    public void editOver(){
        isEditable = false;
        for(SearchRecodeModel model : allHistory){
            model.setStatus(SearchRecodeModel.STATUS_NORMAL);
        }
        history = new ArrayList<>(allHistory);
        history.add(0, historyTitle);
        history.add(0, spliteModel);
        notifyDataSetChanged();
    }

    public void delete(SearchRecodeModel model){
        List<SearchRecodeModel> li = new ArrayList<>(allHistory);
        li.remove(model);
        updateHistory(li, false);
    }

    public void showAllHistory(){
        isExpandHistory = true;
        history = new ArrayList<>(allHistory);
        history.add(0, historyTitle);
        history.add(0, spliteModel);
        notifyDataSetChanged();
    }

    public void showPartHistory(){
        isExpandHistory = false;
        history = new ArrayList<>(partHistory);
        history.add(0, historyTitle);
        history.add(0, spliteModel);
        notifyDataSetChanged();
    }

    public boolean isExpandHistory(){
        return isExpandHistory;
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
        }else if(SearchRecodeModel.TYPE_SPLITE_LINE == viewType){
            View view = View.inflate(mContext, R.layout.search_record_splite_item, null);
            VH = new SpliteVH(view);
        }else if(SearchRecodeModel.TYPE_HISTORY_TITLE == viewType){
            View view = View.inflate(mContext, R.layout.search_record_title_item, null);
            VH = new TitleVH(view);
        }else if(SearchRecodeModel.TYPE_HOT_TITLE == viewType){
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
            ((NoramlVH) holder).bindview(model, mOnItemClickListener);
        }else if(holder instanceof TitleVH){
            model.setStatus(isEditable?SearchRecodeModel.STATUS_EDITOR:SearchRecodeModel.STATUS_NORMAL);
            ((TitleVH) holder).bindView(model, partHistory.size() > 0,isExpandHistory, mOnItemClickListener);
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
