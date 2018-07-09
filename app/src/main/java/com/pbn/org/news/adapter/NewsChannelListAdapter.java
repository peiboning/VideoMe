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
import com.pbn.org.news.vh.BaseVH;
import com.pbn.org.news.vh.EmptyVH;
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
    private Context mContext;
    private RefresRecyleView mRecyleView;
    private NewsBean refreshBean = new NewsBean();

    public NewsChannelListAdapter(List<NewsBean> datas, Context context, RefresRecyleView view) {
        this.mNewsDatas = datas;
        this.mContext = context;
        mRecyleView = view;
        refreshBean.setTemplate(NewsBean.TYPE_ITEM_LIST_NEWS_REFRESH);
    }

    public void updateData(List<NewsBean> data, boolean isLoadMore){
        if(null != data && data.size()>0){
            if(NewsVideoPlayerManager.instance().isPlaying()){
                NewsVideoPlayerManager.instance().releaseNiceVideoPlayer();
            }
            if(null == mNewsDatas){
                mNewsDatas = new ArrayList<NewsBean>();
            }
            if(mNewsDatas.size()<=0){
                mNewsDatas.addAll(data);
            }else{
                mNewsDatas.remove(refreshBean);
                if(isLoadMore){
                    mNewsDatas.addAll(data);
                }else{
                    mNewsDatas.add(0, refreshBean);
                    mNewsDatas.addAll(0, data);
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
        ((BaseVH)holder).showNews(mNewsDatas.get(position), position);
        ((BaseVH)holder).bind(mNewsDatas.get(position));
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
