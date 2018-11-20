package com.pbn.org.news.utils;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

public class NewsHandler {
    private static Handler sMainHandler = new Handler(Looper.getMainLooper());
    private static HandlerThread sThread;
    private static Handler sHandler;

    private static void ensurebg(){
        if(null == sHandler){
            sThread = new HandlerThread("NewsHandler:bg");
            sThread.setPriority(Thread.MIN_PRIORITY);
            sThread.start();
            sHandler = new Handler(sThread.getLooper());
        }
    }

    public static void postToMainTask(Runnable runnable){
        sMainHandler.post(runnable);
    }
    public static void postToMainTaskDelay(Runnable runnable, long delay){
        sMainHandler.postDelayed(runnable, delay);
    }

    public static void postToBgTask(Runnable runnable){
        ensurebg();
        sHandler.post(runnable);
    }
}
