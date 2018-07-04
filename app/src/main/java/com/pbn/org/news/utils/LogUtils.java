package com.pbn.org.news.utils;

import android.util.Log;

public class LogUtils {
    public static boolean DEBUG = false | true;

    public static void e(String tag, String msg){
        if(DEBUG){
            Log.e(tag, msg);
        }
    }
    public static void d(String tag, String msg){
        if(DEBUG){
            Log.d(tag, msg);
        }
    }
    public static void i(String tag, String msg){
        if(DEBUG){
            Log.i(tag, msg);
        }
    }
    public static void v(String tag, String msg){
        if(DEBUG){
            Log.v(tag, msg);
        }
    }
    public static void exception(Throwable e){
        if(DEBUG){
            Log.e("error", Log.getStackTraceString(e));
        }
    }
}
