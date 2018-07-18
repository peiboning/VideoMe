package com.pbn.org.news.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.pbn.org.news.R;
import com.pbn.org.news.base.BaseActivity;
import com.pbn.org.news.skin.inter.ISkinChange;
import com.pbn.org.news.status_bar.StatusBarCompat;

public class SettingActivity extends BaseActivity implements ISkinChange{
    private ImageView leftIcon;
    private TextView title;
    private LinearLayout container;
    private SettingHelper helper;

    private Switch nigthThemeSwitch;
    private Switch wifiTipsSwitch;
    private TextView cacheSize;
    private TextView versionInfo;
    private RelativeLayout versionContainer;
    private RelativeLayout cahcheContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色

        int color = getResources().getColor(R.color.skin_setting_status_bar);
        StatusBarCompat.setStatusBarColor(this, color);
    }

    @Override
    protected void initView() {
        helper = new SettingHelper(this);
        leftIcon = findViewById(R.id.tools_bar_left);
        title = findViewById(R.id.tool_bar_title);
        container = findViewById(R.id.setting_container);

        nigthThemeSwitch = findViewById(R.id.theme_switch);
        wifiTipsSwitch = findViewById(R.id.wifi_tips_switch);
        cacheSize = findViewById(R.id.cache_size);
        versionInfo = findViewById(R.id.version_info);
        cahcheContainer = findViewById(R.id.cache_size_container);
        versionContainer = findViewById(R.id.version_info_container);

        leftIcon.setVisibility(View.VISIBLE);
        title.setVisibility(View.VISIBLE);
        title.setText(R.string.setting);
        helper.setVersionInfo(versionInfo);
        helper.setCacheSize(cacheSize);

        leftIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        helper.clearCache(cahcheContainer);

        helper.setNightThemeStatus(nigthThemeSwitch);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.setting_layout;
    }

    @Override
    protected boolean isNeedSlide() {
        return true;
    }

    @Override
    public void applySkin() {

    }
}
