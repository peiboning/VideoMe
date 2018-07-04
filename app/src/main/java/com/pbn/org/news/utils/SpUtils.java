package com.pbn.org.news.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.pbn.org.news.NewsApplication;

public class SpUtils {
    private static final String NEWS_CONTENT_SP = "news_content_sp";
    private static SharedPreferences sp;

    private static void ensureSp(){
        if(null == sp){
            sp = NewsApplication.getContext().getSharedPreferences(NEWS_CONTENT_SP, Context.MODE_PRIVATE);
        }
    }

    public static long getLong(String key, long defValue){
        ensureSp();
        return sp.getLong(key, defValue);
    }

    public static void putLong(String key, long value){
        ensureSp();
        SharedPreferences.Editor e = sp.edit();
        e.putLong(key, value);
        e.apply();
    }

    public static String getString(String key, String defValue){
        ensureSp();
        return sp.getString(key, defValue);
    }

    public static void putString(String key, String value){
        ensureSp();
        SharedPreferences.Editor e = sp.edit();
        e.putString(key, value);
        e.apply();
    }




}
