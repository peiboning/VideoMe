package com.pbn.org.news.skin;

import android.content.Context;

import com.pbn.org.news.skin.core.ActivityLifecyleWatcher;
import com.pbn.org.news.skin.core.SkinChangeTask;
import com.pbn.org.news.skin.core.SkinOption;
import com.pbn.org.news.utils.NewsHandler;

import java.util.concurrent.atomic.AtomicBoolean;

public class SkinManager {
    private static SkinManager sInstance = null;
    private AtomicBoolean mInit;
    private SkinOption mOption;
    private Context mContext;

    public static SkinManager with() {
        if (null == sInstance) {
            synchronized (SkinManager.class) {
                if (null == sInstance) {
                    sInstance = new SkinManager();
                }
            }
        }
        return sInstance;
    }

    private SkinManager() {
        mInit = new AtomicBoolean(false);
    }

    public SkinOption init(Context context){
        //禁止再添加任何代码,初始化放到initReally中去
        if(!mInit.get()){
            initReally(context);
            mInit.set(true);
        }
        return mOption;
    }

    public SkinOption skinPath(String path){
        mOption.setSkinPath(path);
        return mOption;
    }

    public Context getContext() {
        return mContext;
    }

    public String getSkinPath(){
        return mOption.getSkinPath();
    }

    private void initReally(Context context) {
        //start activity watcher
        ActivityLifecyleWatcher.getInstance().startWatch(context);
        mOption = new SkinOption();
        mContext = context;
    }

    public void startChangeSkin(String name){
        NewsHandler.postToBgTask(new SkinChangeTask(mContext, name));
    }
}