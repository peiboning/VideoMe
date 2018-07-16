package com.pbn.org.news.skin.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.pbn.org.news.skin.SkinManager;

public class SkinSp {
    private static final String THEME_NAME = "current_theme_name";
    private static SharedPreferences sSp;

    private static void ensure(){
        if(null == sSp){
            sSp = SkinManager.with().getContext().getSharedPreferences("skin", Context.MODE_PRIVATE);
        }
    }

    public static void setCurrentTheme(String themeName){
        ensure();
        SharedPreferences.Editor e = sSp.edit();
        e.putString(THEME_NAME, themeName);
        e.commit();
    }

    public static String currentThemeName(){
        ensure();
        return sSp.getString(THEME_NAME, "");
    }
}
