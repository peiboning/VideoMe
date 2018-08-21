package com.pbn.org.push;

import android.content.Context;

import com.pbn.org.push.xiaomi.XMPush;

public class PushSDK {
    private static PushSDK sInstance = null;

    public static PushSDK getInstance() {
        if (null == sInstance) {
            synchronized (PushSDK.class) {
                if (null == sInstance) {
                    sInstance = new PushSDK();
                }
            }
        }
        return sInstance;
    }

    private PushSDK() {
    }

    public void init(Context context){
        XMPush.getInstance().init(context);
    }
}