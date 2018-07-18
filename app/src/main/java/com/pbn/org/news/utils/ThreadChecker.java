package com.pbn.org.news.utils;

import android.os.Looper;

import com.pbn.org.news.exception.ThreadException;

public class ThreadChecker {
    public static void assertUIThread() {
        if(isChildThread()){
            throw new ThreadException("not int UI thread, please check");
        }
    }

    public static void assertChildThread() {
        if(isUIThread()){
            throw new ThreadException("not int UI thread, please check");
        }
    }

    public static boolean isUIThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }
    public static boolean isChildThread() {
        return !isUIThread();
    }



}
