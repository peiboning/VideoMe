package com.pbn.org.news.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.pbn.org.news.R;
import com.pbn.org.news.adapter.NewsChannelListAdapter;
import com.pbn.org.news.base.MVPBaseFragment;
import com.pbn.org.news.model.Channel;
import com.pbn.org.news.model.common.NewsBean;
import com.pbn.org.news.mvp.presenter.NewsListPresenter;
import com.pbn.org.news.mvp.view.INewsListView;
import com.pbn.org.news.utils.LogUtils;
import com.pbn.org.news.utils.SpUtils;
import com.pbn.org.news.utils.ViewUtils;
import com.pbn.org.news.vh.VideoVH;
import com.pbn.org.news.video.NewsVideoPlayerManager;
import com.pbn.org.news.view.CustomLinearLayoutManager;
import com.pbn.org.news.view.RefresRecyleView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author peiboning
 */
public class ChannelFragment extends MVPBaseFragment<INewsListView, NewsListPresenter> implements INewsListView {
    private static final String CHANNEL = "channel";
    public static ChannelFragment newInstance(Channel channel){
        ChannelFragment fragment = new ChannelFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(CHANNEL, channel);
        fragment.setArguments(bundle);
        return fragment;

    }

    private RefresRecyleView listView;
    private Channel channel;
    private NewsChannelListAdapter mAdapter;
    private int pageIndex = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(null == channel){
            Bundle args = getArguments();
            channel = (Channel) args.getSerializable(CHANNEL);
        }
    }

    @Override
    protected NewsListPresenter createPresenter() {
        return new NewsListPresenter();
    }

    @Override
    protected void initView(View view) {
        listView = view.findViewById(R.id.channel_list_view);
        mAdapter = new NewsChannelListAdapter(new ArrayList<NewsBean>(), getContext(), listView);
        listView.setAdapter(mAdapter);
        listView.setLayoutManager(new CustomLinearLayoutManager(getContext()));
        listView.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(RecyclerView.ViewHolder holder) {
                if(holder instanceof VideoVH){

                }
            }
        });
        listView.setOnRefreshListener(new RefresRecyleView.OnRefreshListener() {
            @Override
            public void onPullToRefreshing() {
                LogUtils.d("ChannelFragment", "start update news data");
                presenter.updateNewsList(channel, ++pageIndex, false);
            }

            @Override
            public void onLoadMore() {
                presenter.updateNewsList(channel, ++pageIndex, true);
            }
        });

        listView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState){
                    case RecyclerView.SCROLL_STATE_IDLE:
//                        atuoPlay(recyclerView);
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        break;
                    case RecyclerView.SCROLL_STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
               int fP = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
               int lp = ((LinearLayoutManager)recyclerView.getLayoutManager()).findLastVisibleItemPosition();
               int vP = recyclerView.getLayoutManager().getChildCount();
               int currentPlayerPos = NewsVideoPlayerManager.instance().getCurrentPlayerInFeedListPos() + listView.getHeaderViewNum();
               if(currentPlayerPos >= listView.getHeaderViewNum()){
                   if(currentPlayerPos < fP || currentPlayerPos > lp){
                       NewsVideoPlayerManager.instance().releaseNiceVideoPlayer();
                   }
                   if(currentPlayerPos == fP || currentPlayerPos == lp){//判断百分比
                       View view = ((LinearLayoutManager)recyclerView.getLayoutManager()).findViewByPosition(currentPlayerPos);
                       if(view.findViewById(R.id.video_layout_root) != null){
                           View playerView = view.findViewById(R.id.player);
                           if(ViewUtils.getViewVisiblePercent(playerView, 0.5f)<30){
                               NewsVideoPlayerManager.instance().releaseNiceVideoPlayer();
                           }
                       }
                   }
               }

               if(fP + vP >= recyclerView.getLayoutManager().getItemCount()){
                    listView.loadMore();
               }

            }
        });
    }

    private void atuoPlay(RecyclerView recyclerView) {
        int cc = recyclerView.getLayoutManager().getChildCount();
        CustomLinearLayoutManager linearLayoutManager = (CustomLinearLayoutManager) recyclerView.getLayoutManager();
        int fp = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
        int lp = linearLayoutManager.findLastCompletelyVisibleItemPosition();
        for(int i = fp;i<=lp;i++){
            View view = linearLayoutManager.findViewByPosition(i);
            if(view.findViewById(R.id.video_layout_root) != null){
                if(!NewsVideoPlayerManager.instance().isPlaying()){
                    view.findViewById(R.id.start).performClick();
                }
            }
        }
    }

    @Override
    public String getChannelName() {
        return channel.getTitle();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_channel;
    }

    @Override
    protected void fetchData() {
        presenter.refreshNews(channel);
    }

    @Override
    public void updateNewsList(List<NewsBean> news, boolean isLoadMore) {
        mAdapter.updateData(news, isLoadMore);
        if(isLoadMore){
            listView.loadMoreComplete();
        }else{
            listView.refreshOver();
        }
        if(null != news && news.size() > 0){
            SpUtils.putLong(NewsListPresenter.REFRESH_TIME, System.currentTimeMillis());
        }
    }

    @Override
    public void requestNews() {
        listView.startRefresh();
    }





}
