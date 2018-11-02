package com.pbn.org.news;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.pbn.org.news.base.BaseActivity;
import com.pbn.org.news.fragment.NewsListFragment;
import com.pbn.org.news.setting.SmartTheme;
import com.pbn.org.news.skin.inter.ISkinChange;
import com.pbn.org.news.status_bar.StatusBarCompat;
import com.pbn.org.news.utils.ActivityUtils;
import com.pbn.org.news.utils.NetUtil;
import com.pbn.org.news.video.NewsVideoPlayerManager;
import com.pbn.org.news.view.NewsToast;
import com.pbn.org.news.view.RoundImageView;
import com.umeng.analytics.MobclickAgent;

/**
 * @author peiboning
 */
public class NewsListActivity extends BaseActivity implements ISkinChange {

    private FrameLayout mContent;
    private NewsListFragment mNewsFragment;
    private RoundImageView mSettingIcon;
    private SmartTheme smartTheme;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("onActivityCreated", "onCreate");
        //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色

        int color = getResources().getColor(R.color.skin_status_bar);
        StatusBarCompat.setStatusBarColor(this, color);
        initFragment();
        smartTheme = new SmartTheme(this);
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
        mSettingIcon = findViewById(R.id.setting_icon);
        mSettingIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.startSettingActivity(NewsListActivity.this);
//                NewsToast.showSystemToast("setting Icon");
            }
        });
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
        smartTheme.checkSmartTheme();
        MobclickAgent.onResume(this);
        if(NetUtil.isNetEnable(this) && !NetUtil.isWIFIConnected(this)){
            NewsToast.showSystemToast("当前为移动网络,将消耗移动流量");
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        if(NewsVideoPlayerManager.instance().isPlaying()){
            NewsVideoPlayerManager.instance().releaseNiceVideoPlayer(true);
        }
    }

    private long lastBackTime;
    @Override
    public void onBackPressed() {
        if(!NewsVideoPlayerManager.instance().onBackPressd()){
            if(System.currentTimeMillis() - lastBackTime < 1000){
                if(NewsVideoPlayerManager.instance().isPlaying()){
                    NewsVideoPlayerManager.instance().releaseNiceVideoPlayer(false);
                }
                super.onBackPressed();
            }else{
                lastBackTime = System.currentTimeMillis();
                Toast.makeText(this,"再按一次退出", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void applySkin() {
        StatusBarCompat.setStatusBarColor(this, Color.RED);
    }
}
