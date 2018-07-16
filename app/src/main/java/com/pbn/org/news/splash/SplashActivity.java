package com.pbn.org.news.splash;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import com.pbn.org.news.R;
import com.pbn.org.news.base.MVPBaseActivity;
import com.pbn.org.news.loclib.LocationMgr;
import com.pbn.org.news.mvp.presenter.SplashPresenter;
import com.pbn.org.news.mvp.view.ISplashView;
import com.pbn.org.news.utils.ActivityUtils;
import com.pbn.org.news.utils.UMUtils;
import com.pbn.org.permission.PermissionClient;

import java.util.List;

/**
 * @author peiboning
 */
public class SplashActivity extends MVPBaseActivity<ISplashView, SplashPresenter> implements ISplashView {
    private String[] mNeedPermissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    @Override
    protected SplashPresenter createPresenter() {
        return new SplashPresenter();
    }

    @Override
    protected void initView() {
        List<String> permissions = PermissionClient.checkPermission(mNeedPermissions);
        if(null != permissions && permissions.size() > 0){
            if(!permissions.contains(Manifest.permission.ACCESS_COARSE_LOCATION)){
                LocationMgr.getInstance().startLoc();
            }
            PermissionClient.request(this, permissions.toArray(new String[permissions.size()]), null);
        }else{
            LocationMgr.getInstance().startLoc();
            ActivityUtils.startMainActivity(this);
            finish();
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
        ActivityUtils.startMainActivity(this);
        finish();
    }
}
