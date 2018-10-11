package com.pbn.org.news.video;

import android.media.MediaPlayer;

/**
 * 视频播放器管理器.
 */
public class NewsVideoPlayerManager {

    private NewsVideoPlayer mVideoPlayer;
    private MediaPlayer mPlayer;

    private NewsVideoPlayerManager() {
    }

    private static NewsVideoPlayerManager sInstance;

    public static synchronized NewsVideoPlayerManager instance() {
        if (sInstance == null) {
            sInstance = new NewsVideoPlayerManager();
        }
        return sInstance;
    }

    public NewsVideoPlayer getCurrentSHVideoPlayer() {
        return mVideoPlayer;
    }

    public void setShareMediaPlayer(MediaPlayer player){
        mPlayer = player;
    }

    public MediaPlayer getShareMediaPlayer(){
        return mPlayer;
    }

    public void releaseMediaplayer(){
        if(null != mVideoPlayer){
            releaseNiceVideoPlayer(false);
        }
        mPlayer = null;
    }

    public void setCurrentSHVideoPlayer(NewsVideoPlayer videoPlayer, boolean isDetail) {
        if (mVideoPlayer != videoPlayer) {
            releaseNiceVideoPlayer(isDetail);
            mVideoPlayer = videoPlayer;
        }
    }

    public int getCurrentPlayerInFeedListPos(){
        if(null != mVideoPlayer){
            return mVideoPlayer.getCurrentPlayerInFeedListPos();
        }
        return -1;
    }

    public boolean isPlaying(){
        if(null != mVideoPlayer){
            return mVideoPlayer.isPlaying();
        }
        return false;
    }

    public void suspendSHVideoPlayer() {
        if (mVideoPlayer != null && (mVideoPlayer.isPlaying() || mVideoPlayer.isBufferingPlaying())) {
            mVideoPlayer.pause();
        }
    }

    public void resumeNiceVideoPlayer() {
        if (mVideoPlayer != null && (mVideoPlayer.isPaused() || mVideoPlayer.isBufferingPaused())) {
            mVideoPlayer.restart();
        }
    }

    public void seekTo(long pos){
        if(null != mVideoPlayer){
            mVideoPlayer.seekTo(pos);
        }
    }

    public void releaseNiceVideoPlayer(boolean isEnterDetail) {
        if (mVideoPlayer != null) {
            mVideoPlayer.release(isEnterDetail);
            mVideoPlayer = null;
        }
    }

    public boolean onBackPressd() {
        if (mVideoPlayer != null) {
            if (mVideoPlayer.isFullScreen()) {
                return mVideoPlayer.exitFullScreen();
            } else if (mVideoPlayer.isTinyWindow()) {
                return mVideoPlayer.exitTinyWindow();
            }
        }
        return false;
    }
}
