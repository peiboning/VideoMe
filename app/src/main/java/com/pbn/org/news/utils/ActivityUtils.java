package com.pbn.org.news.utils;

import android.content.Context;
import android.content.Intent;

import com.pbn.org.news.NewsListActivity;

public class ActivityUtils {
    public static void startMainActivity(Context context){
        Intent intent = new Intent();
        intent.setClass(context, NewsListActivity.class);
        context.startActivity(intent);
    }
}
