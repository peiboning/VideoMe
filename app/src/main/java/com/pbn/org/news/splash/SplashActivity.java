package com.pbn.org.news.splash;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.pbn.org.news.R;
import com.pbn.org.news.base.MVPBaseActivity;
import com.pbn.org.news.loclib.LocationMgr;
import com.pbn.org.news.mvp.presenter.SplashPresenter;
import com.pbn.org.news.mvp.view.ISplashView;
import com.pbn.org.news.utils.ActivityUtils;
import com.pbn.org.news.utils.NewsHandler;
import com.pbn.org.news.utils.UMUtils;
import com.pbn.org.permission.PermissionClient;

import java.util.List;

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
        List<String> permissions = PermissionClient.checkPermission(mNeedPermissions);
        if(null != permissions && permissions.size() > 0){
            if(!permissions.contains(Manifest.permission.ACCESS_COARSE_LOCATION)){
                LocationMgr.getInstance().startLoc();
            }
            PermissionClient.request(this, permissions.toArray(new String[permissions.size()]), null);
        }else{
            LocationMgr.getInstance().startLoc();
            startActivity(2);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onResume() {
        super.onResume();
        UMUtils.OnOnlyActivityResume(this, "SplashActivity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        UMUtils.OnOnlyActivityPause(this, "SplashActivity");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
            LocationMgr.getInstance().startLoc();
        }
        startActivity(1);
    }

    public void startActivity(int from){
        Log.e("SplashActivity", "start from is " + from+", isNeedstartMain : " + isNeedStartMain);
        long delay = System.currentTimeMillis() - enterTime<3000?(3000-(System.currentTimeMillis() - enterTime)):0;
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
}
