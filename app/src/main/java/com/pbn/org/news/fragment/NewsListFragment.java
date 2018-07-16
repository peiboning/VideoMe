package com.pbn.org.news.fragment;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.pbn.org.news.R;
import com.pbn.org.news.adapter.NewsChannelAdapter;
import com.pbn.org.news.base.MVPBaseFragment;
import com.pbn.org.news.model.Channel;
import com.pbn.org.news.model.haokan.HKRequestParams;
import com.pbn.org.news.model.haokan.HaokanVideo;
import com.pbn.org.news.model.xigua.QueryMap;
import com.pbn.org.news.model.xigua.XiguaModel;
import com.pbn.org.news.mvp.presenter.NewsListPresenter;
import com.pbn.org.news.net.RetrofitClient;
import com.pbn.org.news.net.api.HAOKANAPI;
import com.pbn.org.news.net.api.XiguaAPI;
import com.pbn.org.news.skin.SkinManager;
import com.pbn.org.news.skin.utils.SkinSp;
import com.pbn.org.news.utils.ActivityUtils;
import com.pbn.org.news.view.NewsViewPager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

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
//        addChannel.setVisibility(View.VISIBLE);
        addChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ActivityUtils.startChannelMgrActivity(getContext());
//                headerView.setTabTextColors(Color.BLUE, Color.GREEN);
                if(TextUtils.isEmpty(SkinSp.currentThemeName())){
                    SkinManager.with().startChangeSkin("skin_night.skin");
                }else{
                    SkinManager.with().startChangeSkin("");
                }
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
                    Channel channel = new Channel(object.optString("name"), object.optInt("channelId", -1)+"");
                    int quickId = object.optInt("quickId", -1);
                    channel.setQuickCode(quickId);
                    String haokanId = object.optString("haokanId", "");
                    channel.setHaokanId(haokanId);
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
