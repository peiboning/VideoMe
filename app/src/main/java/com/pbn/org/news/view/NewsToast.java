package com.pbn.org.news.view;

import android.text.TextUtils;
import android.widget.Toast;

import com.pbn.org.news.NewsApplication;

public class NewsToast {
    public static void showSystemToast(String msg){
        if(!TextUtils.isEmpty(msg)){
            Toast.makeText(NewsApplication.getContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }
}
