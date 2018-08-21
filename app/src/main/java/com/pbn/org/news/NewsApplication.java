package com.pbn.org.news;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.pbn.org.news.cache.CacheManager;
import com.pbn.org.news.core.ActivityLifeCyle;
import com.pbn.org.news.loclib.LocationMgr;
import com.pbn.org.news.skin.SkinManager;
import com.pbn.org.news.utils.ChannelUtils;
import com.pbn.org.news.utils.NewsHandler;
import com.pbn.org.news.utils.SpUtils;
import com.pbn.org.permission.PermissionClient;
import com.pbn.org.push.PushSDK;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class NewsApplication extends MultiDexApplication{
    private static Context sContext;
    public static String session;
    private static String APP_KEY = "5afe2b88a40fa307a6000054";
    private boolean isBackgroud;
    private long enterBackgroudTime;

    @Override
    public void onCreate() {
        super.onCreate();
        PushSDK.getInstance().init(sContext);
    }

    public void enterBackgroud(){
        isBackgroud = true;
        enterBackgroudTime = System.currentTimeMillis();
    }

    public void enterForgroud(){
        isBackgroud = false;
    }

    public boolean isStartSplash(){
        if(enterBackgroudTime == 0){
            return false;
        }
        return System.currentTimeMillis() - enterBackgroudTime > 15000;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        registerActivityLifecycleCallbacks(new ActivityLifeCyle());
        sContext = this;
        initSkin();
        initPermissionSDK();
        initLoclib();
        initUM();
//        initBugly();
        initCache();
        session = UUID.randomUUID().toString();
        NewsHandler.postToBgTask(new Runnable() {
            @Override
            public void run() {
                initSkin();
            }
        });

    }



    private void initCache() {
        CacheManager.getInstance();
    }

    private void initSkin() {
        String path = sContext.getExternalCacheDir() + File.separator + "skin";
        File file = new File(path);
        copySkinFile(path);
        if(!file.exists()){
            file.mkdirs();
        }
        SkinManager.with().init(sContext).setSkinPath(path).apply();
    }

    private void copySkinFile(String path) {
        try {
            InputStream inputStream = sContext.getAssets().open("night/skin_night.skin");
            File file = new File(path + File.separator + "skin_night.skin");
            if(file.exists() && file.length() > 0){

            }else{
                file.deleteOnExit();
                file.createNewFile();
                FileOutputStream outputStream = new FileOutputStream(file);
                int len = -1;
                byte[] buff = new byte[2048];
                while ((len = inputStream.read(buff)) != -1){
                    outputStream.write(buff, 0, len);
                }
                inputStream.close();
                outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
