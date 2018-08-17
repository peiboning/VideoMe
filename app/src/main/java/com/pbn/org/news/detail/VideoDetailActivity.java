package com.pbn.org.news.detail;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pbn.org.news.R;
import com.pbn.org.news.base.MVPBaseActivity;
import com.pbn.org.news.model.common.NewsBean;
import com.pbn.org.news.model.zixun.VideoModel;
import com.pbn.org.news.mvp.presenter.VideoDetailPagePresenter;
import com.pbn.org.news.mvp.view.IVideoDetailView;
import com.pbn.org.news.skin.inter.ISkinChange;
import com.pbn.org.news.status_bar.StatusBarCompat;
import com.pbn.org.news.video.NewsVideoPlayer;
import com.pbn.org.news.video.NewsVideoPlayerManager;
import com.pbn.org.news.video.VideoPlayerController;

public class VideoDetailActivity extends MVPBaseActivity<IVideoDetailView, VideoDetailPagePresenter> implements IVideoDetailView, ISkinChange{
    public static final String FROM_X = "fromX";
    public static final String FROM_Y = "fromY";
    public static final String BEAN = "bean";

    private NewsVideoPlayer player;
    private VideoPlayerController controller;
    private LinearLayout bottom;

    private int fromX;
    private NewsBean bean;
    
    @Override
    protected VideoDetailPagePresenter createPresenter() {
        return new VideoDetailPagePresenter();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int color = getResources().getColor(R.color.skin_status_bar);
        StatusBarCompat.setStatusBarColor(this, color);
    }

    @Override
    protected void initView() {
        player = findViewById(R.id.player);
        controller = new VideoPlayerController(this);
        player.setController(controller);
        bottom = findViewById(R.id.detail_bottom);

        parseIntent(getIntent());

    }

    private void parseIntent(Intent intent) {
        fromX = intent.getIntExtra(FROM_X, 0);
        bean = (NewsBean) intent.getSerializableExtra(BEAN);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        parseIntent(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.video_detail_layout;
    }

    @Override
    public void onBackPressed() {
        if(!NewsVideoPlayerManager.instance().onBackPressd()){
            startExitAnimal();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        VideoModel videoModel = bean.getVideos().get(0);
        player.setUp(videoModel.getUrl(), null);
        loadImage(controller.imageView(), bean.getImages().get(0).getUrl());
        controller.getTitleView().setVisibility(View.INVISIBLE);
        controller.getPlayTimeView().setVisibility(View.INVISIBLE);
        player.post(new Runnable() {
            @Override
            public void run() {
                player.start();
                startEnterAnimal();
            }
        });
    }

    private void startEnterAnimal() {
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator playerTranslationY = ObjectAnimator.ofFloat(player, "translationY", fromX, player.getTranslationY());

        ObjectAnimator alpha = ObjectAnimator.ofFloat(bottom, "alpha", 0, 1);

        set.play(playerTranslationY).with(alpha);
        set.setDuration(300);
        set.start();

    }

    private void startExitAnimal() {
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator playerTranslationY = ObjectAnimator.ofFloat(player, "translationY", player.getTranslationY(), fromX);

        ObjectAnimator alpha = ObjectAnimator.ofFloat(bottom, "alpha", 1, 0);

        set.play(playerTranslationY).with(alpha);
        set.setDuration(300);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                NewsVideoPlayerManager.instance().releaseNiceVideoPlayer();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                finish();
                overridePendingTransition(0, 0);
            }
        });
        set.start();

    }



    private void loadImage(ImageView view, String url) {
        Glide.with(view.getContext())
                .load(url)
                .placeholder(R.color.black)
                .into(view);
    }

    @Override
    public void applySkin() {

    }
}
