package com.pbn.org.news.detail;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pbn.org.news.R;
import com.pbn.org.news.adapter.RelateVideoAdapter;
import com.pbn.org.news.base.MVPBaseActivity;
import com.pbn.org.news.model.common.NewsBean;
import com.pbn.org.news.model.haokan.SearchVideo;
import com.pbn.org.news.model.zixun.VideoModel;
import com.pbn.org.news.mvp.presenter.VideoDetailPagePresenter;
import com.pbn.org.news.mvp.view.IVideoDetailView;
import com.pbn.org.news.skin.inter.ISkinChange;
import com.pbn.org.news.status_bar.StatusBarCompat;
import com.pbn.org.news.video.NewsVideoPlayer;
import com.pbn.org.news.video.NewsVideoPlayerManager;
import com.pbn.org.news.video.VideoPlayerController;
import com.pbn.org.news.view.ProgressView;

import java.util.List;

public class VideoDetailActivity extends MVPBaseActivity<IVideoDetailView, VideoDetailPagePresenter> implements IVideoDetailView, ISkinChange{
    public static final String FROM_X = "fromX";
    public static final String FROM_Y = "fromY";
    public static final String BEAN = "bean";
    public static final String SRC_SOURCE = "src_source";

    public static final int SOURCE_SELF = 1;
    public static final int SOURCE_SEARCH = 2;
    public static final int SOURCE_LIST = 3;

    private NewsVideoPlayer player;
    private VideoPlayerController controller;
    private LinearLayout bottom;
    private RecyclerView relateList;
    private TextView icon_txt;
    private TextView author_txt;

    private int fromX;
    private NewsBean bean;
    private RelateVideoAdapter mAdapter;
    private ProgressView mProgress;
    private int srcSource;
    @Override
    protected VideoDetailPagePresenter createPresenter() {
        return new VideoDetailPagePresenter();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        int color = getResources().getColor(R.color.skin_status_bar);
        StatusBarCompat.setStatusBarColor(this, color);
        parseIntent(getIntent());
    }

    @Override
    protected void initView() {
        player = findViewById(R.id.player);
        controller = new VideoPlayerController(this);
        player.setController(controller);
        bottom = findViewById(R.id.detail_bottom);
        relateList = findViewById(R.id.detail_relate);
        relateList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RelateVideoAdapter(this);
        relateList.setAdapter(mAdapter);
        mProgress = findViewById(R.id.load_progress);
        icon_txt = findViewById(R.id.src_icon_txt);
        author_txt = findViewById(R.id.video_src);
    }

    private void parseIntent(Intent intent) {
        fromX = intent.getIntExtra(FROM_X, 0);
        bean = (NewsBean) intent.getSerializableExtra(BEAN);
        srcSource = intent.getIntExtra(SRC_SOURCE, 0);

        String author = bean.getSource();
        if(TextUtils.isEmpty(author)){
            author = "VM";
        }

        icon_txt.setText(author.substring(0,1));
        author_txt.setText(author);

        if(NewsBean.CONTENT_SOURCE_Haokan == bean.getContentSource()){
            presenter.getRelativeHKVideo(bean.getId());
        }else if(NewsBean.CONTENT_SOURCE_sdk == bean.getContentSource()){
            presenter.getRelateVideoFromSDK(bean.getId(), bean.getChannelId(), this);
        }
        relateList.setVisibility(View.GONE);
        mProgress.start();
        VideoModel videoModel = bean.getVideos().get(0);
        if(SOURCE_SELF == srcSource){
            NewsVideoPlayerManager.instance().releaseMediaplayer();
        }
        player.setUp(videoModel.getUrl(), null);
        loadImage(controller.imageView(), bean.getImages().get(0).getUrl());
        controller.getTitleView().setVisibility(View.INVISIBLE);
        controller.getPlayTimeView().setVisibility(View.INVISIBLE);
        player.start(true);
        if(SOURCE_LIST == srcSource){
            player.post(new Runnable() {
                @Override
                public void run() {
                    startEnterAnimal();
                }
            });
        }
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
            if(SOURCE_LIST == srcSource){
                startExitAnimal();
            }else{
                finish();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

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
                NewsVideoPlayerManager.instance().releaseMediaplayer();
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

    @Override
    public void updateRelateVideo(List<SearchVideo> list) {
        mProgress.stop();
        relateList.setVisibility(View.VISIBLE);
        if(null != list && list.size() > 0){
            relateList.scrollToPosition(0);
            mAdapter.updateResult(list, bean);
        }else{
            updateRelateError();
        }
    }

    @Override
    public void updateRelateError() {
        mProgress.stop();
    }
}
