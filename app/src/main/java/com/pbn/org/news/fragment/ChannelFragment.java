package com.pbn.org.news.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.pbn.org.news.utils.UMUtils;
import com.pbn.org.news.utils.ViewUtils;
import com.pbn.org.news.vh.VideoVH;
import com.pbn.org.news.video.IActivityCallback;
import com.pbn.org.news.video.MainActivityLifecycleAndStatus;
import com.pbn.org.news.video.NewsVideoPlayerManager;
import com.pbn.org.news.view.AdImageView;
import com.pbn.org.news.view.CustomLinearLayoutManager;
import com.pbn.org.news.view.RefresRecyleView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author peiboning
 */
public class ChannelFragment extends MVPBaseFragment<INewsListView, NewsListPresenter> implements INewsListView,IActivityCallback {
    private static final String TAG = ChannelFragment.class.getSimpleName();
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
//                LogUtils.d("ChannelFragment", "start update news data");
                presenter.updateNewsList(channel, ++pageIndex, false);
                UMUtils.refresh(getContext(), getChannelName());
            }

            @Override
            public void onLoadMore() {
                presenter.updateNewsList(channel, ++pageIndex, true);
                UMUtils.loadMore(getContext(), getChannelName());
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
                int lastCompleteV = ((LinearLayoutManager)recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                int fP = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                int lp = ((LinearLayoutManager)recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                int vP = recyclerView.getLayoutManager().getChildCount();
                for(int i = fP;i<=lp;i++){
                    View view = recyclerView.getLayoutManager().findViewByPosition(i);
                    AdImageView adImageView = view.findViewById(R.id.ad_item_img);
                    if (null != adImageView && adImageView.getVisibility() == View.VISIBLE) {
                        adImageView.setDy(recyclerView.getLayoutManager().getHeight() - view.getTop(), recyclerView.getLayoutManager().getHeight(), recyclerView.getTop());
                    }
                }
                if(dy > 0 || dx > 0){

                    int currentPlayerPos = NewsVideoPlayerManager.instance().getCurrentPlayerInFeedListPos() + listView.getHeaderViewNum();
                    if(currentPlayerPos >= listView.getHeaderViewNum()){
                        if(currentPlayerPos < fP || currentPlayerPos > lp){
                            NewsVideoPlayerManager.instance().releaseNiceVideoPlayer(false);
                        }
                        if(currentPlayerPos == fP || currentPlayerPos == lp){//判断百分比
                            View view = ((LinearLayoutManager)recyclerView.getLayoutManager()).findViewByPosition(currentPlayerPos);
                            if(view.findViewById(R.id.video_layout_root) != null){
                                View playerView = view.findViewById(R.id.player);
                                if(ViewUtils.getViewVisiblePercent(playerView, 0.5f)<30){
                                    NewsVideoPlayerManager.instance().releaseNiceVideoPlayer(false);
                                }
                            }
                        }
                    }

                    if(fP + vP >= recyclerView.getLayoutManager().getItemCount()){
                        listView.loadMore();
                    }
                }
            }
        });
    }



    @Override
    public String getChannelName() {
        if(null != channel){
            return channel.getTitle();
        }
        return "null";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_channel;
    }

    @Override
    protected void fetchData() {
        listView.startRefresh();
    }

    @Override
    public void updateNewsList(List<NewsBean> news, boolean isLoadMore) {
        mAdapter.updateData(news, isLoadMore);
        if(isLoadMore){
            listView.loadMoreComplete();
        }else{
            listView.refreshOver(null != news?news.size():0);
        }
        if(null != news && news.size() > 0){
            SpUtils.putLong(NewsListPresenter.REFRESH_TIME, System.currentTimeMillis());
        }
    }

    @Override
    public void requestNews() {
        listView.startRefresh();
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.i(TAG, "onResume:" + getChannelName());
        UMUtils.onFragmentResume(getChannelName());
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtils.i(TAG, "onPause:" + getChannelName());
        UMUtils.onFragmentPause(getChannelName());
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(!isVisibleToUser){
            MainActivityLifecycleAndStatus.getInstance().removeOnStopPlayCallback(this);
            NewsVideoPlayerManager.instance().releaseMediaplayer();
        }else{
            MainActivityLifecycleAndStatus.getInstance().addOnStopPlayCallback(this);
        }
        LogUtils.i(TAG, "setUserVisibleHint:" +isVisibleToUser + ","+ getChannelName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MainActivityLifecycleAndStatus.getInstance().removeOnStopPlayCallback(this);
    }

    @Override
    public void onEvent(Object object) {
        int currentPlayerPos = NewsVideoPlayerManager.instance().getCurrentPlayerInFeedListPos()  + listView.getHeaderViewNum();
        listView.smoothScrollToPosition(currentPlayerPos + 1);
        atuoPlay(listView, currentPlayerPos + 1);
    }

    private void atuoPlay(RecyclerView recyclerView, int pos) {
        CustomLinearLayoutManager linearLayoutManager = (CustomLinearLayoutManager) recyclerView.getLayoutManager();
        View view = linearLayoutManager.findViewByPosition(pos);
        if(view.findViewById(R.id.video_layout_root) != null){
            if(!NewsVideoPlayerManager.instance().isPlaying()){
                view.findViewById(R.id.start).performClick();
            }
        }
    }
}
