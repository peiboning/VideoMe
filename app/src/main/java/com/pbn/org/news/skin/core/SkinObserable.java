package com.pbn.org.news.skin.core;

import android.support.annotation.UiThread;

import com.pbn.org.news.skin.inter.SkinObserver;
import com.pbn.org.news.utils.ThreadChecker;

import java.util.ArrayList;
import java.util.List;

public class SkinObserable {
    private static SkinObserable sInstance = null;
    private List<SkinObserver> observers;

    @UiThread
    public static SkinObserable getInstance() {
        if (null == sInstance) {
            sInstance = new SkinObserable();
        }
        return sInstance;
    }

    private SkinObserable() {
        observers = new ArrayList<>();
    }

    @UiThread
    public void subscribe(SkinObserver observer){
        ThreadChecker.assertUIThread();
        if(null != observer){
            if(!observers.contains(observer)){
                observers.add(observer);
            }
        }
    }

    @UiThread
    public void unSubscribe(SkinObserver observer){
        ThreadChecker.assertUIThread();
        if(null != observer){
            if(observers.contains(observer)){
                observers.remove(observer);
            }
        }
    }

    @UiThread
    public void notification(){
        ThreadChecker.assertUIThread();
        for(SkinObserver observer : observers){
            observer.applySkin();
        }
    }
}