package com.pbn.org.news.utils.crash;

import android.os.Environment;
import android.util.Log;

/**
 * function:
 *
 * @author peiboning
 * @DATE 2018/12/17
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler{

    private Thread.UncaughtExceptionHandler originHandler;
    private int a = 0;
    public CrashHandler(int flag){
        a = flag;
    }

    public void init(){
        originHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        String HPROF_FILE_PATH = Environment.getExternalStorageDirectory().getPath() + "/data_crash.hprof"


        Log.e("CrashHandler", "flag is :" + a);
        if(null != originHandler){
            Log.e("CrashHandler", "flag is :" + a + ", name is " + originHandler.getClass().getName());
            originHandler.uncaughtException(t,e);
        }
    }
}
