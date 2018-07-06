package com.pbn.org.news.utils;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;

public class UMUtils {
    public static void OnOnlyActivityResume(Context context, String pageName){
        MobclickAgent.onResume(context);
        MobclickAgent.onPageStart(pageName);
    }
    public static void OnOnlyActivityPause(Context context, String pageName){
        MobclickAgent.onPause(context);
        MobclickAgent.onPageEnd(pageName);
    }

    public static void onFragmentResume(String pageName){
        MobclickAgent.onPageStart(pageName);
    }
    public static void onFragmentPause(String pageName){
        MobclickAgent.onPageEnd(pageName);
    }

    public static void loadMore(Context context, String channelName){
        MobclickAgent.onEvent(context, "load_more", channelName);
    }

    public static void refresh(Context context, String channelName){
        MobclickAgent.onEvent(context, "refresh", channelName);
    }

    public static void playOver(Context context){
        MobclickAgent.onEvent(context, "play_over");
    }

    public static void startPlay(Context context){
        MobclickAgent.onEvent(context, "play_num");
    }
}
