package com.pbn.org.news;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.pbn.org.news.cache.CacheManager;
import com.pbn.org.news.loclib.LocationMgr;
import com.pbn.org.news.skin.SkinManager;
import com.pbn.org.news.utils.ChannelUtils;
import com.pbn.org.permission.PermissionClient;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import java.util.UUID;

public class NewsApplication extends Application{
    private static Context sContext;
    public static String session;
    private static String APP_KEY = "5afe2b88a40fa307a6000054";
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        sContext = this;
        initSkin();
        initPermissionSDK();
        initLoclib();
        initUM();
        initBugly();
        initCache();
        session = UUID.randomUUID().toString();
    }

    private void initCache() {
        CacheManager.getInstance();
    }

    private void initSkin() {
        SkinManager.with().init(sContext).apply();
    }

    private void initBugly() {
        CrashReport.initCrashReport(this, "8d143cd859", BuildConfig.DEBUG);
    }


    private void initUM() {
        UMConfigure.init(this, APP_KEY, ChannelUtils.getChannel(this), UMConfigure.DEVICE_TYPE_PHONE, "");
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        MobclickAgent.openActivityDurationTrack(false);
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
