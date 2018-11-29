package com.pbn.org.news.utils.sp;

import android.content.SharedPreferences;
import android.os.SystemClock;
import android.util.Log;


/**
 * function:
 *
 * @author peiboning
 * @DATE 2018/11/27
 */
public class SafeCommitTask implements Runnable{
    private SharedPreferences.Editor editor;

    public SafeCommitTask(SharedPreferences.Editor editor){
        this.editor = editor;
    }
    @Override
    public void run() {
        long s = SystemClock.currentThreadTimeMillis();
        boolean res = editor.commit();
        Log.e("SafeCommitTask", "commit cost time is " + (SystemClock.currentThreadTimeMillis() - s) + ", result is " + res);
    }
}
