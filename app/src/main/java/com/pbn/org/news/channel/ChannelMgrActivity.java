package com.pbn.org.news.channel;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.pbn.org.news.R;
import com.pbn.org.news.base.BasePresenter;
import com.pbn.org.news.base.MVPBaseActivity;
import com.pbn.org.news.base.MVPBaseFragment;
import com.pbn.org.news.model.Channel;

import java.util.ArrayList;
import java.util.List;

public class ChannelMgrActivity extends MVPBaseActivity {
    private RecyclerView recyclerView;
    private ChannelAdapter adapter;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        recyclerView = findViewById(R.id.channel_mgr_list);
        final GridLayoutManager manager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(manager);
        adapter = new ChannelAdapter(this, recyclerView);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int type = adapter.getItemViewType(position);
                int size = (Channel.TYPE_MY == type || Channel.TYPE_OTHER == type) ? manager.getSpanCount() : 1;
                Log.e("ChannelMgrActivity", "pos is " + position + ", type is " + type  + ", size is " + size);
                return size;
            }
        });

        adapter.setDatas(getMyList(), getOtherList());
        recyclerView.setAdapter(adapter);
    }

    private List<Channel> getOtherList() {
        List<Channel> list = new ArrayList<>(20);
        for(int i = 0;i<20;i++){
            Channel channel = new Channel(Channel.TYPE_MY_CHANNEL, "其他" + i, "");
            list.add(channel);
        }
        return list;
    }

    public List<Channel> getMyList() {
        List<Channel> list = new ArrayList<>(20);
        for(int i = 0;i<10;i++){
            Channel channel = new Channel(Channel.TYPE_OTHER_CHANNEL, "我的" + i, "");
            list.add(channel);
        }
        return list;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_channel_mgr;
    }

    @Override
    public void onBackPressed() {//TODO
        if(adapter.isEditable()){

        }
        super.onBackPressed();
    }
}
