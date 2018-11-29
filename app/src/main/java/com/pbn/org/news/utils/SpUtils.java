package com.pbn.org.news.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.pbn.org.news.NewsApplication;
import com.pbn.org.news.utils.sp.SafeSharePreference;
import com.pbn.org.news.utils.sp.inter.ISafeSharePreference;

public class SpUtils {
    private static final String NEWS_CONTENT_SP = "news_content_sp";
    private static ISafeSharePreference sp;

    private static void ensureSp(){
        if(null == sp){
            sp = SafeSharePreference.getSafeSharePreference(NEWS_CONTENT_SP);
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

    public static int getInt(String key, int defValue){
        ensureSp();
        return sp.getInt(key, defValue);
    }

    public static void putInt(String key, int value){
        ensureSp();
        SharedPreferences.Editor e = sp.edit();
        e.putInt(key, value);
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
