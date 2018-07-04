package com.pbn.org.news.video;

/**
 * 视频播放器管理器.
 */
public class NewsVideoPlayerManager {

    private NewsVideoPlayer mVideoPlayer;

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

    public void setCurrentSHVideoPlayer(NewsVideoPlayer videoPlayer) {
        if (mVideoPlayer != videoPlayer) {
            releaseNiceVideoPlayer();
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

    public void releaseNiceVideoPlayer() {
        if (mVideoPlayer != null) {
            mVideoPlayer.release();
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
