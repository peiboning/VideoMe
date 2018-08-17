package com.pbn.org.news.setting;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pbn.org.news.NewsApplication;
import com.pbn.org.news.R;
import com.pbn.org.news.cache.CacheManager;
import com.pbn.org.news.skin.SkinManager;
import com.pbn.org.news.skin.utils.SkinSp;
import com.pbn.org.news.utils.NewsHandler;
import com.pbn.org.news.utils.VersionUitls;
import com.pbn.org.news.view.NewsToast;

import okhttp3.Cache;

public class SettingHelper {
    private static final String THEME_NIGHT_SWITCH = "";
    private Activity activity;

    public SettingHelper(Activity activity) {
        this.activity = activity;
        init();
    }

    private void init() {

    }

    public void setVersionInfo(TextView versionInfo){
        if(null != versionInfo){
            versionInfo.setText(VersionUitls.getVersionInfo());
        }
    }

    public void setNightThemeStatus(Switch sw){
        if(TextUtils.isEmpty(SkinSp.currentThemeName())){
            sw.setChecked(false);
        }else{
            sw.setChecked(true);
        }
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    SkinManager.with().startChangeSkin("skin_night.skin");
                }else{
                    SkinManager.with().startChangeSkin("");
                }
            }
        });
    }

    public void clearCache(View view){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                NewsToast.showSystemToast("开始清理缓存");
                NewsHandler.postToBgTask(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(NewsApplication.getContext()).clearDiskCache();
                        final String size = CacheManager.getInstance().getCacheSize();
                        NewsToast.showSystemToast("清理完毕");
                        NewsHandler.postToMainTask(new Runnable() {
                            @Override
                            public void run() {
                                ((TextView)activity.findViewById(R.id.cache_size)).setText(size);
                            }
                        });
                    }
                });

            }
        });
    }

    public void setCacheSize(final TextView tv){
        NewsHandler.postToBgTask(new Runnable() {
            @Override
            public void run() {
                final String size = CacheManager.getInstance().getCacheSize();
                NewsHandler.postToMainTask(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText(size);
                    }
                });
            }
        });
    }

}
