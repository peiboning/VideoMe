package com.pbn.org.news.service.core;

import android.app.Notification;
import android.app.Service;

public class KeepLiveManager {
    public static final int FORE_NOTIFICATION_ID = 100;
    private static KeepLiveManager sInstance = null;

    public static KeepLiveManager getInstance() {
        if (null == sInstance) {
            synchronized (KeepLiveManager.class) {
                if (null == sInstance) {
                    sInstance = new KeepLiveManager();
                }
            }
        }
        return sInstance;
    }

    private KeepLiveManager() {
    }

    public void startForeground(Service service){
        try{
            service.startForeground(FORE_NOTIFICATION_ID, new Notification());
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}