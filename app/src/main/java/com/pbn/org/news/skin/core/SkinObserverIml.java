package com.pbn.org.news.skin.core;

import android.app.Activity;

import com.pbn.org.news.skin.core.res.ResourceManager;
import com.pbn.org.news.skin.factoty.SkinLayoutFactoryManager;
import com.pbn.org.news.skin.inter.SkinObserver;

public class SkinObserverIml implements SkinObserver {
    public static SkinObserver create(Activity activity){
        return new SkinObserverIml(activity);
    }
    private Activity activity;

    private SkinObserverIml(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void applySkin() {
        if(ResourceManager.getInstance().isReady()){
            SkinLayoutFactoryManager.getInstance().apply(activity);
        }
    }
}
