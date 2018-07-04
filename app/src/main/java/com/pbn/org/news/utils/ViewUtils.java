package com.pbn.org.news.utils;

import android.graphics.Rect;
import android.util.Log;
import android.view.View;

public class ViewUtils {
    public static int getViewVisiblePercent(View view, float alph){
        if(null != view && view.getVisibility() == View.VISIBLE && view.getAlpha()>alph){
            Rect rect = new Rect();
            view.getLocalVisibleRect(rect);
            int height = view.getHeight();
            int visbleHeight = Math.abs(rect.bottom - rect.top);
            int percent = (int) (visbleHeight * 1.0f/height * 100);
            Log.e("getViewVisiblePercent", rect.toShortString() + ", percent is " + percent);
            return percent;
        }
        return 0;
    }
}
