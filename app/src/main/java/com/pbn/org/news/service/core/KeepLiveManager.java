package com.pbn.org.news.service.core;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import static android.support.v4.app.NotificationCompat.PRIORITY_LOW;

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
            Notification notification = getNotification(service);
            service.startForeground(FORE_NOTIFICATION_ID, notification);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public Notification getNotification(Service service) {
        String notificationChannel = "";
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O){
            NotificationManager mNotificationManager = (NotificationManager) service.getSystemService(Context.NOTIFICATION_SERVICE);

            String name = "snap map fake location ";
            int importance = NotificationManager.IMPORTANCE_LOW;

            NotificationChannel mChannel = new NotificationChannel("my_app_service", name, importance);

            mChannel.enableLights(true);
            mChannel.setLightColor(Color.BLUE);
            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(mChannel);
            }
            notificationChannel = "my_app_service";
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(service, notificationChannel).setSmallIcon(android.R.drawable.ic_menu_mylocation).setContentTitle("haha");
        Notification notification = mBuilder
                .setPriority(PRIORITY_LOW)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();

        return notification;
    }
}