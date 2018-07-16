package com.pbn.org.news.skin.core;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.pbn.org.news.skin.factoty.SkinLayoutFactoryManager;
import com.pbn.org.news.skin.inter.SkinObserver;
import com.pbn.org.news.skin.utils.SkinUtils;

import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

public class ActivityLifecyleWatcher implements Application.ActivityLifecycleCallbacks{
    private static ActivityLifecyleWatcher sInstance = null;
    private WeakReference<Activity> mCurrentResumeActivity;
    //为了从stop-->Resume不需要再重新创建
    private WeakHashMap<Context, SkinObserver> mSkinObserverCache;

    public static ActivityLifecyleWatcher getInstance() {
        if (null == sInstance) {
            synchronized (ActivityLifecyleWatcher.class) {
                if (null == sInstance) {
                    sInstance = new ActivityLifecyleWatcher();
                }
            }
        }
        return sInstance;
    }

    private ActivityLifecyleWatcher() {
        mSkinObserverCache = new WeakHashMap<>();
    }

    public void startWatch(Context context){
        ((Application)context).registerActivityLifecycleCallbacks(this);
        SkinLayoutFactoryManager.getInstance().installFactory(context);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Log.e("onActivityCreated", "call back");
        SkinLayoutFactoryManager.getInstance().installFactory(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        if(SkinUtils.isCanChangeSkin(activity)){
            mCurrentResumeActivity = new WeakReference<Activity>(activity);
            SkinObserable.getInstance().subscribe(getObserver(activity));
            getObserver(activity).applySkin();
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        SkinLayoutFactoryManager.getInstance().unInstallFactory(activity);
        SkinObserable.getInstance().unSubscribe(getObserver(activity));
        mSkinObserverCache.remove(activity);
    }

    private SkinObserver getObserver(Activity activity){
        SkinObserver observer = mSkinObserverCache.get(activity);
        if(observer == null){
            observer = SkinObserverIml.create(activity);
            mSkinObserverCache.put(activity, observer);
        }
        return observer;
    }
}