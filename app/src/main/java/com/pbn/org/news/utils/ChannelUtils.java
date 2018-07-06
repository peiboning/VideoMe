package com.pbn.org.news.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

public class ChannelUtils {

    private static String channelData = "";
    public static String getChannel(Context context){
        String channel = "100000";
        if(TextUtils.isEmpty(channelData)){
            PackageManager pm = context.getPackageManager();
            try {
                ApplicationInfo info = pm.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                channel = String.valueOf(info.metaData.getInt("CHANNEL", 100000));
                channelData = channel;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }else{
            return channelData;
        }
        return channel;
    }
}
