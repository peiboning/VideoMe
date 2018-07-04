package com.pbn.org.news.fragment;

import android.content.res.AssetManager;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.pbn.org.news.R;
import com.pbn.org.news.adapter.NewsChannelAdapter;
import com.pbn.org.news.base.MVPBaseFragment;
import com.pbn.org.news.model.Channel;
import com.pbn.org.news.mvp.presenter.NewsListPresenter;
import com.pbn.org.news.view.NewsViewPager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class NewsListFragment extends MVPBaseFragment {

    public static NewsListFragment newFragment(){
        NewsListFragment fragment = new NewsListFragment();

        return fragment;
    }

    private NewsViewPager viewPager;
    private TabLayout headerView;
    private ImageView addChannel;
    private List<Channel> channels = new ArrayList<Channel>();

    @Override
    protected NewsListPresenter createPresenter() {
        return null;
    }


    @Override
    protected void initView(View view) {
        initChannel();
        viewPager = view.findViewById(R.id.news_viewpager);
        headerView = view.findViewById(R.id.header_view);
        addChannel = view.findViewById(R.id.add_channel_btn);
        viewPager.setAdapter(new NewsChannelAdapter(getChildFragmentManager(), channels));
        headerView.setupWithViewPager(viewPager);
        addChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "adsfas", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void initChannel() {
        AssetManager assetManager = getContext().getAssets();
        try {
            InputStream in = assetManager.open("json/soNews.json");
            byte[] buff = new byte[1024];
            int len = -1;
            StringBuffer sb = new StringBuffer();

            while ((len = in.read(buff)) != -1){
                String s = new String(buff, 0, len);
                sb.append(s);
            }

            in.close();
            String json = sb.toString();
            JSONObject obj = new JSONObject(json);

            JSONArray arr = obj.optJSONArray("data");
            if(null != arr && arr.length()>0){
                for(int i = 0;i<arr.length();i++){
                    JSONObject object = arr.getJSONObject(i);
                    Channel channel = new Channel(object.optString("name"), object.optInt("channelId")+"");
                    channels.add(channel);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_newslist;
    }

}
