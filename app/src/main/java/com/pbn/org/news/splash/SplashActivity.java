package com.pbn.org.news.splash;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Debug;
import android.os.Looper;
import android.os.MessageQueue;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.pbn.org.news.NewsApplication;
import com.pbn.org.news.R;
import com.pbn.org.news.base.MVPBaseActivity;
import com.pbn.org.news.loclib.LocationMgr;
import com.pbn.org.news.mvp.presenter.SplashPresenter;
import com.pbn.org.news.mvp.view.ISplashView;
import com.pbn.org.news.service.AppService;
import com.pbn.org.news.service.BackgroudService;
import com.pbn.org.news.utils.ActivityUtils;
import com.pbn.org.news.utils.NewsHandler;
import com.pbn.org.news.utils.UMUtils;
import com.pbn.org.permission.PermissionClient;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author peiboning
 */
public class SplashActivity extends MVPBaseActivity<ISplashView, SplashPresenter> implements ISplashView {
    private String[] mNeedPermissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE
        ,Manifest.permission.READ_PHONE_STATE
    };
    public static String IS_NEED_START_MAIN = "is_need_start_main";
    private boolean isNeedStartMain;
    private long enterTime;
    private CountDownLatch latch;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Looper.myLooper().getQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                new Thread(){
                    int  i = 40;
                    @Override
                    public void run() {
                        NewsHandler.postToMainTask(new Runnable() {
                            @Override
                            public void run() {
                                Log.e("IdleHandler", "IdleHandler");
                                NewsApplication.testTime();
                                startServiceFirst();

                                Intent i = new Intent();
                                i.setClass(SplashActivity.this, AppService.class);
                                startService(i);

                                List<String> permissions = PermissionClient.checkPermission(mNeedPermissions);
                                if(null != permissions && permissions.size() > 0){
                                    if(!permissions.contains(Manifest.permission.ACCESS_COARSE_LOCATION)){
                                        LocationMgr.getInstance().startLoc();
                                    }
                                    PermissionClient.request(SplashActivity.this, permissions.toArray(new String[permissions.size()]), null);
                                }else{
                                    LocationMgr.getInstance().startLoc();
                                    startActivity(2);
                                }
                            }
                        });
                    }
                }.start();

                return false;
            }
        });
        super.onCreate(savedInstanceState);
    }

    private boolean test() {

        return false;
    }

    @Override
    protected SplashPresenter createPresenter() {
        return new SplashPresenter();
    }

    @Override
    protected void initView() {
        enterTime = System.currentTimeMillis();
        Intent intent = getIntent();
        if(null != intent){
            isNeedStartMain = intent.getBooleanExtra(IS_NEED_START_MAIN, true);
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onResume() {
        super.onResume();
//        UMUtils.OnOnlyActivityResume(this, "SplashActivity");
        Log.e("IdleHandler", "OnResume");
        NewsApplication.testTime();
    }

    @Override
    protected void onPause() {
        super.onPause();
        UMUtils.OnOnlyActivityPause(this, "SplashActivity");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(null != grantResults && grantResults.length>0){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                LocationMgr.getInstance().startLoc();
            }
        }
        startActivity(1);
    }

    public void startActivity(int from){
        long delay = System.currentTimeMillis() - enterTime<1000?(1000-(System.currentTimeMillis() - enterTime)):0;
        Log.e("SplashActivity", "start from is " + from+", isNeedstartMain : " + isNeedStartMain+", delay is " + delay);
        NewsHandler.postToMainTaskDelay(new Runnable() {
            @Override
            public void run() {
                if(isNeedStartMain){
                    ActivityUtils.startMainActivity(SplashActivity.this);
                }
                finish();
            }
        }, delay);
    }

    private void startServiceFirst() {
        Intent intent = new Intent();
        intent.setClass(this, BackgroudService.class);
        startService(intent);
    }
}
