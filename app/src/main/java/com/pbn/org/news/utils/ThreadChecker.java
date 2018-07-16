package com.pbn.org.news.utils;

import android.os.Looper;

import com.pbn.org.news.exception.ThreadException;

public class ThreadChecker {
    public static void assertUIThread() {
        if(Looper.getMainLooper() != Looper.myLooper()){
            throw new ThreadException("not int UI thread, please check");
        }
    }
}
