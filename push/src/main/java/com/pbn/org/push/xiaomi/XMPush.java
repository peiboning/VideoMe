package com.pbn.org.push.xiaomi;


import android.content.Context;

import com.xiaomi.mipush.sdk.MiPushClient;

public class XMPush {
    private static final String APP_ID = "2882303761517835068";
    private static final String APP_KEY = "5121783563068";
    private static XMPush sInstance = null;

    public static XMPush getInstance() {
        if (null == sInstance) {
            synchronized (XMPush.class) {
                if (null == sInstance) {
                    sInstance = new XMPush();
                }
            }
        }
        return sInstance;
    }

    private XMPush() {
    }

    public void init(Context context){
        MiPushClient.registerPush(context, APP_ID, APP_KEY);
    }
}