package com.pbn.org.news.utils;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.widget.TextView;

import com.pbn.org.news.NewsApplication;

public class VersionUitls {
    private static String sVersionInfo;
    public static String getVersionInfo(){
        if(TextUtils.isEmpty(sVersionInfo)){
            PackageManager pm = NewsApplication.getContext().getPackageManager();
            try {
                PackageInfo pInfo = pm.getPackageInfo(NewsApplication.getContext().getPackageName(), PackageManager.GET_ACTIVITIES);
                sVersionInfo = pInfo.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return sVersionInfo;
    }
}
