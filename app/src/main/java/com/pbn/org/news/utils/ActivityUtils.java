package com.pbn.org.news.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.pbn.org.news.NewsListActivity;
import com.pbn.org.news.channel.ChannelMgrActivity;
import com.pbn.org.news.detail.VideoDetailActivity;
import com.pbn.org.news.model.common.NewsBean;
import com.pbn.org.news.model.haokan.HotWorld;
import com.pbn.org.news.search.SearchActivity;
import com.pbn.org.news.setting.SettingActivity;
import com.pbn.org.news.view.NewsToast;

public class ActivityUtils {
    public static void startMainActivity(Context context){
        Intent intent = new Intent();
        intent.setClass(context, NewsListActivity.class);
        context.startActivity(intent);
    }

    public static void startChannelMgrActivity(Context context){
        Intent intent = new Intent();
        intent.setClass(context, ChannelMgrActivity.class);
        context.startActivity(intent);
    }

    public static void startSettingActivity(Context context){
        Intent intent = new Intent();
        intent.setClass(context, SettingActivity.class);
        context.startActivity(intent);
    }

    public static void startSearchActivity(Context context, HotWorld hotWorlds){
        Intent intent = new Intent();
        if(null != hotWorlds){
            intent.putExtra(SearchActivity.KEY_HOT_WRODS, hotWorlds);
        }
        intent.setClass(context, SearchActivity.class);
        context.startActivity(intent);
    }

    public static void startVideoDetailActivity(Context context, int fromX, NewsBean bean, int from){
        if(!NetUtil.isNetEnable(context)){
            NewsToast.showSystemToast("网络不可用");
            return;
        }
        Intent intent = new Intent();
        intent.setClass(context, VideoDetailActivity.class);
        intent.putExtra(VideoDetailActivity.FROM_X, fromX);
        intent.putExtra(VideoDetailActivity.BEAN, bean);
        intent.putExtra(VideoDetailActivity.SRC_SOURCE, from);
        context.startActivity(intent);

    }
    public static void startVideoDetailActivity(Context context, int fromX, NewsBean bean){
       startVideoDetailActivity(context, fromX, bean, VideoDetailActivity.SOURCE_LIST);
        ((Activity)context).overridePendingTransition(0, 0);
    }

}
