package com.pbn.org.news.skin.core;

import android.os.Environment;
import android.text.TextUtils;

import com.pbn.org.news.skin.SkinManager;
import com.pbn.org.news.skin.utils.SkinSp;
import com.pbn.org.news.utils.SpUtils;

public class SkinOption {
    private String skinPath;

    public String getSkinPath() {
        if(TextUtils.isEmpty(skinPath)){
            skinPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return skinPath;
    }

    public void setSkinPath(String skinPath) {
        this.skinPath = skinPath;
    }

    public void apply(){
        SkinManager.with().startChangeSkin(SkinSp.currentThemeName());
    }
}
