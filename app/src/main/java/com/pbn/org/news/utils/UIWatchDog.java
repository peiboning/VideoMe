package com.pbn.org.news.utils;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Choreographer;

import java.util.concurrent.TimeUnit;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class UIWatchDog implements Choreographer.FrameCallback {
    private static UIWatchDog sInstance = null;

    private long lastDrawTime;
    private long curDrawTime;
    public static UIWatchDog getInstance() {
        if (null == sInstance) {
            synchronized (UIWatchDog.class) {
                if (null == sInstance) {
                    sInstance = new UIWatchDog();
                }
            }
        }
        return sInstance;
    }

    private UIWatchDog() {
    }

    public void start(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            Choreographer.getInstance().postFrameCallback(this);
        }
    }

    @Override
    public void doFrame(long frameTimeNanos) {
        if(lastDrawTime == 0){
            lastDrawTime = frameTimeNanos;
        }
        curDrawTime = frameTimeNanos;

        long dif = TimeUnit.MILLISECONDS.convert((curDrawTime - lastDrawTime), TimeUnit.NANOSECONDS);
        lastDrawTime = curDrawTime;
//        Log.e("UIWatchDog", "dif is :" + dif);
        if(dif>0){
//            Log.e("UIWatchDog", "frame is :" + (1000/dif));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            Choreographer.getInstance().postFrameCallback(this);
        }

    }
}