package com.pbn.org.news;

import android.Manifest;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.pbn.org.news.base.BaseActivity;
import com.pbn.org.news.fragment.NewsListFragment;
import com.pbn.org.news.loclib.LocationMgr;
import com.pbn.org.news.video.NewsVideoPlayerManager;
import com.pbn.org.permission.OnRequestPermssionListener;
import com.pbn.org.permission.PermissionClient;
import com.pbn.org.permission.PermissionRejectHandler;
import com.umeng.analytics.MobclickAgent;

/**
 * @author peiboning
 */
public class NewsListActivity extends BaseActivity {

    private FrameLayout mContent;
    private NewsListFragment mNewsFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        getWindow().setStatusBarColor(Color.RED);
        initFragment();
        requestLocationInfo();

    }

    private void requestLocationInfo() {
        if(!PermissionClient.checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)){
            PermissionClient.request(this, Manifest.permission.ACCESS_COARSE_LOCATION, new OnRequestPermssionListener() {
                @Override
                public void onGrantPermission(String permssion) {
                    LocationMgr.getInstance().startLoc();
                    initStorePermission();
                }

                @Override
                public void onRejectPermission(String permssion) {
                    initStorePermission();
                }
            }, new PermissionRejectHandler() {
                @Override
                public boolean showRationaleToUser(String permission) {
                    return false;
                }
            });
        }else{
            LocationMgr.getInstance().startLoc();
        }
    }

    private void initStorePermission() {
        if(!PermissionClient.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            PermissionClient.request(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, null, new PermissionRejectHandler() {
                @Override
                public boolean showRationaleToUser(String permission) {
                    return false;
                }
            });
        }
    }

    private void initFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if(null == mNewsFragment){
            mNewsFragment = NewsListFragment.newFragment();
            ft.add(R.id.main_content, mNewsFragment);
        }else{
            ft.show(mNewsFragment);
        }

        ft.commit();
    }

    @Override
    protected void initView() {
        mContent = findViewById(R.id.main_content);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if(keyCode == KeyEvent.KEYCODE_BACK){
//            Log.e("onKeyDown", "press back");
//            return ;
//        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        if(NewsVideoPlayerManager.instance().isPlaying()){
            NewsVideoPlayerManager.instance().releaseNiceVideoPlayer();
        }
    }

    private long lastBackTime;
    @Override
    public void onBackPressed() {
        if(!NewsVideoPlayerManager.instance().onBackPressd()){
            if(System.currentTimeMillis() - lastBackTime < 1000){
                if(NewsVideoPlayerManager.instance().isPlaying()){
                    NewsVideoPlayerManager.instance().releaseNiceVideoPlayer();
                }
                super.onBackPressed();
            }else{
                lastBackTime = System.currentTimeMillis();
                Toast.makeText(this,"再按一次退出", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
