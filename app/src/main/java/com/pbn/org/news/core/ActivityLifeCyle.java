package com.pbn.org.news.core;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.pbn.org.news.NewsApplication;
import com.pbn.org.news.splash.SplashActivity;


public class ActivityLifeCyle implements Application.ActivityLifecycleCallbacks {
    private int activityNums;
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        activityNums++;
        if(activityNums == 1){
            ((NewsApplication)NewsApplication.getContext()).enterForgroud();
            if(((NewsApplication)NewsApplication.getContext()).isStartSplash() && !(activity instanceof SplashActivity)){
                Intent i = new Intent();
                i.setClass(activity, SplashActivity.class);
                activity.startActivity(i);
            }
        }
        Log.e("ActivityLifeCyle", "onActivityResumed activity num is " + activityNums);
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        activityNums--;
        if(activityNums == 0){
            ((NewsApplication)NewsApplication.getContext()).enterBackgroud();
        }
        Log.e("ActivityLifeCyle", "onStop activity num is " + activityNums);
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
