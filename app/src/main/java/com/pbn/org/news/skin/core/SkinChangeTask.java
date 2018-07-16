package com.pbn.org.news.skin.core;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;

import com.pbn.org.news.skin.SkinManager;
import com.pbn.org.news.skin.core.loader.SkinPluginLoader;
import com.pbn.org.news.skin.core.res.ResourceManager;
import com.pbn.org.news.skin.utils.SkinSp;
import com.pbn.org.news.utils.NewsHandler;

import java.io.File;

public class SkinChangeTask implements Runnable {
    private String sCurrentTheme;
    private String themeName;
    private Context mContext;

    public SkinChangeTask(Context context, String themeName) {
        this.themeName = themeName;
        this.mContext = context;
    }

    @Override
    public void run() {
        if(themeName.equals(sCurrentTheme)){
            return;
        }
        boolean flag = loadNewResource();
        if(flag){
            SkinSp.setCurrentTheme(themeName);
            sCurrentTheme = themeName;
            NewsHandler.postToMainTask(new Runnable() {
                @Override
                public void run() {
                    SkinObserable.getInstance().notification();
                }
            });
        }else{
            SkinSp.setCurrentTheme("");
            ResourceManager.getInstance().setResource(mContext, null, "");
            NewsHandler.postToMainTask(new Runnable() {
                @Override
                public void run() {
                    SkinObserable.getInstance().notification();
                }
            });
        }
    }

    private boolean loadNewResource() {
        if(TextUtils.isEmpty(themeName)){
            ResourceManager.getInstance().setResource(mContext, null, "");
            return true;
        }else{
            String path = SkinManager.with().getSkinPath() + File.separator + themeName;
            Log.e("SkinChangeTask", "path is :\n" + path);
            String pkg = SkinPluginLoader.getPkg(mContext, path);
            Resources resources = SkinPluginLoader.getPluginResource(mContext, path);
            if(!TextUtils.isEmpty(pkg) && null != resources){
                ResourceManager.getInstance().setResource(mContext, resources, pkg);
                return true;
            }
        }
        return false;
    }
}
