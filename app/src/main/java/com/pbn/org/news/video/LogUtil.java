package com.pbn.org.news.video;

import android.util.Log;

/**
 * log工具.
 */
public class LogUtil {

    private static final String TAG = "SHVideoPlayer";

    public static void d(String message) {
        Log.d(TAG, message);
    }

    public static void d(String tag, String message) {
        Log.d(tag, message);
    }

    public static void i(String message) {
        Log.i(TAG, message);
    }

    public static void e(String message, Throwable throwable) {

        Log.e(TAG, message, throwable);
    }

    public static void printeException(Exception exception) {
        printeException(TAG, exception);
    }

    public static void printeException(String tag, Exception exception) {
        Log.e(tag, Log.getStackTraceString(exception));
    }
}
