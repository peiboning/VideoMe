package com.pbn.org.news.utils;

import android.os.Build;

import com.pbn.org.news.NewsApplication;

import java.util.HashMap;
import java.util.Map;

public class RequestParamsUtils {
    public static Map<String, Object> getNewsRequestParam(String catId, String pageIndex){
        Map hashMap = new HashMap();
        hashMap.put("apptoken", "e983eece");
        hashMap.put("sign", Md5Utils.md5("795178dbc2776900161500a9e983eecee983eece"));
        hashMap.put("pageIndex", pageIndex);
        hashMap.put("catId", catId);
        hashMap.put("operatorType", 3);
        hashMap.put("connectionType", 100);
        hashMap.put("ip", "");
        hashMap.put("userAgent", "");
        hashMap.put("brand", Build.BRAND);
        hashMap.put("screenWidth", DeviceUtil.getInstance().getScreenSize()[0]);
        hashMap.put("screenHeight", DeviceUtil.getInstance().getScreenSize()[1]);
        hashMap.put("vendor", Build.BRAND);
        hashMap.put("model", Build.MODEL);
        hashMap.put("androidId", DeviceUtil.getInstance().getAndroidId());
        hashMap.put("mac", DeviceUtil.getInstance().getMacAddress());
        hashMap.put("imsi", "");
        hashMap.put("imei", "");
        hashMap.put("idfa", "");
        hashMap.put("osVersion", Build.VERSION.RELEASE);
        hashMap.put("osType", "1");
        hashMap.put("deviceType", "1");
//        if (str.equals("1037")) {
//            hashMap.put("type", "video");
//        } else {
//        }
        hashMap.put("type", "news");
        return hashMap;
    }
}
