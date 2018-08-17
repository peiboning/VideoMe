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

    public SkinOption setSkinPath(String skinPath) {
        this.skinPath = skinPath;
        return this;
    }

    public void apply(){
        SkinManager.with().startChangeSkin(SkinSp.currentThemeName());
    }
}
