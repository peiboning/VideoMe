package com.pbn.org.news.splash;

import com.pbn.org.news.R;
import com.pbn.org.news.base.MVPBaseActivity;
import com.pbn.org.news.mvp.presenter.SplashPresenter;
import com.pbn.org.news.mvp.view.ISplashView;
import com.pbn.org.news.utils.ActivityUtils;
import com.pbn.org.news.utils.UMUtils;

/**
 * @author peiboning
 */
public class SplashActivity extends MVPBaseActivity<ISplashView, SplashPresenter> implements ISplashView {
    @Override
    protected SplashPresenter createPresenter() {
        return new SplashPresenter();
    }

    @Override
    protected void initView() {
        ActivityUtils.startMainActivity(this);
        finish();
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
}
