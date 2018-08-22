package com.pbn.org.news.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.pbn.org.news.service.core.KeepLiveManager;
import com.pbn.org.news.utils.LogUtils;

public class AppService extends Service{
    private static String TAG = AppService.class.getSimpleName();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw  new RuntimeException("AppService not suport bind");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        KeepLiveManager.getInstance().startForeground(this);
        LogUtils.i(TAG, "AppService onCreate....");
        Intent i = new Intent();
        i.setClass(this, ShellService.class);
        startService(i);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.i(TAG, "AppService onCreate....");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        LogUtils.i(TAG, "AppService onDestory....");
        super.onDestroy();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

}
