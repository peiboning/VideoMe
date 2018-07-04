package com.pbn.org.news;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.pbn.org.news.loclib.LocationMgr;
import com.pbn.org.permission.PermissionClient;

import java.util.UUID;

public class NewsApplication extends Application{
    private static Context sContext;
    public static String session;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        sContext = this;
        initPermissionSDK();
        initLoclib();
        initUM();
        session = UUID.randomUUID().toString();
    }



    private void initUM() {

    }

    private void initLoclib() {
        LocationMgr.getInstance().init(this);
    }

    private void initPermissionSDK() {
        PermissionClient.init(this);
    }

    public static Context getContext(){
        return sContext;
    }

    @Override
    public void onTrimMemory(int level) {
        Log.e("onTrimMemory", "onTrimMemory level is " + level);
        super.onTrimMemory(level);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
