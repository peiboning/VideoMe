package com.pbn.org.news.skin.utils;

import android.content.Context;

import com.pbn.org.news.skin.inter.ISkinChange;

public class SkinUtils {
    public static boolean isCanChangeSkin(Context context){
        return (context instanceof ISkinChange);
    }
}
