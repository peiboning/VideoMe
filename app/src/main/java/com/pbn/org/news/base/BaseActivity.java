package com.pbn.org.news.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;

import com.pbn.org.news.NewsApplication;
import com.pbn.org.news.skin.inter.ISkinChange;
import com.pbn.org.news.view.SlideLayout;
import com.umeng.analytics.MobclickAgent;

public abstract class BaseActivity extends FragmentActivity implements SlideLayout.OnFinishListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDensity();
        setContentView(getLayoutId());
        if(isNeedSlide()){
            SlideLayout slideLayout = new SlideLayout(this);
            slideLayout.bindActivity(this);
            slideLayout.setOnFinishListener(this);
        }
        initView();
    }

    private void setDensity() {
        DisplayMetrics displayMetrics = NewsApplication.getContext().getResources().getDisplayMetrics();
        float targetDensity = displayMetrics.widthPixels / 360;
        int targetDpi = (int) (targetDensity * 160);
        displayMetrics.scaledDensity = displayMetrics.scaledDensity * (displayMetrics.density/targetDensity);

        displayMetrics.density = targetDensity;
        displayMetrics.densityDpi = targetDpi;

        DisplayMetrics displayMetrics1 = getResources().getDisplayMetrics();
        displayMetrics1.density = targetDensity;
        displayMetrics1.densityDpi = targetDpi;
        displayMetrics1.scaledDensity = displayMetrics1.scaledDensity * (displayMetrics1.density/targetDensity);


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
    }

    @Override
    public void onFinish() {
        finish();
    }

    protected abstract void initView();

    protected abstract int getLayoutId();

    protected boolean isNeedSlide(){
        return false;
    }
}
