package com.pbn.org.news.utils;

import android.content.Context;
import android.content.Intent;

import com.pbn.org.news.NewsListActivity;
import com.pbn.org.news.channel.ChannelMgrActivity;
import com.pbn.org.news.setting.SettingActivity;

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

}
