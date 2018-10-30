package com.pbn.org.news.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pbn.org.news.R;
import com.pbn.org.news.model.quyue.QueyueNewsBean;
import com.pbn.org.news.model.common.NewsBean;
import com.pbn.org.news.utils.LogUtils;
import com.pbn.org.news.vh.AdItemVH;
import com.pbn.org.news.vh.BaseVH;
import com.pbn.org.news.vh.EmptyVH;
import com.pbn.org.news.vh.ListItemViewHolder;
import com.pbn.org.news.vh.NewsListBigVH;
import com.pbn.org.news.vh.NewsOneVH;
import com.pbn.org.news.vh.NewsThreeIconVH;
import com.pbn.org.news.vh.RefreshVH;
import com.pbn.org.news.vh.VideoVH;
import com.pbn.org.news.video.NewsVideoPlayerManager;
import com.pbn.org.news.view.RefresRecyleView;

import java.util.ArrayList;
import java.util.List;

public class NewsChannelListAdapter extends RecyclerView.Adapter {
    private List<NewsBean> mNewsDatas;
    private List<NewsBean> mImageDatas;
    private Context mContext;
    private RefresRecyleView mRecyleView;
    private NewsBean refreshBean = new NewsBean();
    private NewsBean adBean = new NewsBean();
    private NewsBean group = new NewsBean();

    public NewsChannelListAdapter(List<NewsBean> datas, Context context, RefresRecyleView view) {
        this.mNewsDatas = datas;
        this.mContext = context;
        mRecyleView = view;
        refreshBean.setTemplate(NewsBean.TYPE_ITEM_LIST_NEWS_REFRESH);
        adBean.setTemplate(QueyueNewsBean.TYPE_ITEM_LIST_AD);
        group.setTemplate(QueyueNewsBean.TYPE_ITEM_LIST_GROUP);
        mImageDatas = new ArrayList<>();
    }

    public void updateData(List<NewsBean> data, boolean isLoadMore){
        if(null != data && data.size()>0){
            if(NewsVideoPlayerManager.instance().isPlaying()){
                NewsVideoPlayerManager.instance().releaseNiceVideoPlayer(false);
            }
            if(null == mNewsDatas){
                mNewsDatas = new ArrayList<NewsBean>();
                mNewsDatas.add(new NewsBean());
            }
            if(mNewsDatas.size()<=0){
                mNewsDatas.addAll(data);
            }else{
                mNewsDatas.remove(adBean);
                mNewsDatas.remove(refreshBean);
//                mNewsDatas.remove(group);
                mImageDatas.clear();
                mImageDatas.addAll(data);
                if(isLoadMore){
                    mNewsDatas.addAll(data);
                }else{
                    mNewsDatas.add(0, refreshBean);
                    mNewsDatas.add(1,adBean);
                    mNewsDatas.addAll(0, data);
//                    mNewsDatas.add(1, group);
                }
            }
            LogUtils.e("ChannelFragment", "updateNewsList is " + data.size());
            mRecyleView.notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BaseVH VH = null;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = null;
        if(QueyueNewsBean.TYPE_ITEM_LIST_NEWS_ONE == viewType){
            view = inflater.inflate(R.layout.item_list_news_one, null);
            VH = new NewsOneVH(view);
        }else if(QueyueNewsBean.TYPE_ITEM_LIST_NEWS_BIG == viewType){
            view = inflater.inflate(R.layout.item_list_news_big, null);
            VH = new NewsListBigVH(view);
        }else if(QueyueNewsBean.TYPE_ITEM_LIST_NEWS_THREE == viewType){
            view = inflater.inflate(R.layout.item_list_news_three, null);
            VH = new NewsThreeIconVH(view);
        }else if(QueyueNewsBean.TYPE_ITEM_LIST_NEWS_REFRESH == viewType){
            view = inflater.inflate(R.layout.item_list_news_refresh, null);
            VH = new RefreshVH(view);
            ((RefreshVH)VH).setParentView(mRecyleView);
        }else if(QueyueNewsBean.TYPE_ITEM_LIST_VIDEO == viewType){
            view = inflater.inflate(R.layout.item_video_layout, null);
            VH = new VideoVH(view);
        }else if(QueyueNewsBean.TYPE_ITEM_LIST_AD == viewType){
            view = inflater.inflate(R.layout.item_ad_layout, null);
            VH = new AdItemVH(view);
        }else if(QueyueNewsBean.TYPE_ITEM_LIST_GROUP == viewType){
            view = inflater.inflate(R.layout.list_item_vh, null);
            VH = new ListItemViewHolder(view);
        }else{
            view = new View(mContext);
            VH = new EmptyVH(view);
        }
        return VH;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if(position > mNewsDatas.size()-1){
            return;
        }
        if(holder instanceof ListItemViewHolder){
            ((BaseVH)holder).bind(mImageDatas);
        }else{
            ((BaseVH)holder).showNews(mNewsDatas.get(position), position);
            ((BaseVH)holder).bind(mNewsDatas.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mNewsDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mNewsDatas.get(position).getTemplate();
    }
}
