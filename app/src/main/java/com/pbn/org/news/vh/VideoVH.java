package com.pbn.org.news.vh;

import android.view.View;
import android.widget.TextView;

import com.pbn.org.news.R;
import com.pbn.org.news.model.common.NewsBean;
import com.pbn.org.news.model.zixun.VideoModel;
import com.pbn.org.news.video.NewsVideoPlayer;
import com.pbn.org.news.video.VideoPlayerController;

public class VideoVH extends BaseVH {
    private NewsVideoPlayer player;
    private VideoPlayerController controller;
    private TextView videoSrc;
    private TextView videoSrcIconTxt;
    public VideoVH(View itemView) {
        super(itemView);
        player = itemView.findViewById(R.id.player);
        controller = new VideoPlayerController(itemView.getContext());
        player.setController(controller);
        videoSrc = itemView.findViewById(R.id.video_src);
        videoSrcIconTxt = itemView.findViewById(R.id.src_icon_txt);
    }

    @Override
    public void showNews(NewsBean bean, int pos) {
        VideoModel videoModel = bean.getVideos().get(0);
        player.setUp(videoModel.getUrl(), null);
        player.setViewInFeedListPos(pos);
        loadImage(controller.imageView(), bean.getImages().get(0).getUrl());
        controller.setTitle(bean.getTitle());
        controller.setPlayTime(getPlayTime((int) videoModel.getDuration()));
        videoSrc.setText(bean.getSource());
        videoSrcIconTxt.setText(bean.getSource().subSequence(0,1));
    }

    private String getPlayTime(int duration) {
        String time = "";
        if(duration<3600){
            int min = duration/60;
            int second = duration%60;
            if(min<10){
                time="0" + min + ":";
            }else{
                time = min+":";
            }
            if(second<10){
                time = time + "0" + second;
            }else{
                time = time + second;
            }
        }
        return time;
    }
}
