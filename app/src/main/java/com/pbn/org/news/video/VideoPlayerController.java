package com.pbn.org.news.video;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.DrawableRes;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pbn.org.news.R;
import com.pbn.org.news.utils.UMUtils;


/**
 * 视频主页播放器
 */
public class VideoPlayerController
        extends NewsBaseVideoPlayerController
        implements View.OnClickListener,
        SeekBar.OnSeekBarChangeListener, Runnable {

    private FrameLayout mVideoViewContainer;
    private ImageView mCoverImage;

    private LinearLayout mBottom;
    private TextView mCurrentPlayTime;
    private SeekBar mProgressBar;
    private TextView mTotallayTime;
    private ImageView mFullScreen;

    private ProgressBar mBottomProgressBar;

    private ProgressBar mLoading;
    private ImageView mStart;
    private TextView mTitle;
    private ImageView mBack;
    private ViewGroup mTop;
    private TextView mNormalTitle;

    private Context mContext;
    private Handler mHandler;
    private TextView mPlayTime;


    private OnScreenStatusChangeListener mScreenStatusChangeListener;
    private boolean mCenterVisible;

    public VideoPlayerController(Context context) {
        super(context);
        mContext = context;
        init();
        registerVolumeReceiver();
    }

    public void setOnScreenStatusChangeListener(OnScreenStatusChangeListener screenStatusChangeListener){
        mScreenStatusChangeListener = screenStatusChangeListener;
    }


    private void init() {
        LayoutInflater.from(mContext).inflate(R.layout.video_layout, this, true);
        mVideoViewContainer = findViewById(R.id.surface_container);
        mCoverImage = findViewById(R.id.cover_bg);
        mBottom = findViewById(R.id.layout_bottom);
        mCurrentPlayTime = findViewById(R.id.current);
        mProgressBar = findViewById(R.id.progress);
        mTotallayTime = findViewById(R.id.total);
        mFullScreen = findViewById(R.id.fullscreen);

        mBottomProgressBar = findViewById(R.id.bottom_progressbar);
        mLoading = findViewById(R.id.loading);
        mStart = findViewById(R.id.start);
        mTitle = findViewById(R.id.title);
        mBack = findViewById(R.id.back);
        mTop = findViewById(R.id.layout_top);

        mFullScreen.setOnClickListener(this);
        mStart.setOnClickListener(this);
        mNormalTitle = findViewById(R.id.normal_title);

        mPlayTime = findViewById(R.id.play_time);
        this.setOnClickListener(this);
        mHandler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                int what = msg.what;
            }
        };
        mProgressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int pos = (int) (seekBar.getProgress()*1.0f/100 * mSHVideoPlayer.getDuration());
                mSHVideoPlayer.seekTo(pos);
            }
        });
    }

    @Override
    public void setTitle(String title) {
        mTitle.setText(title);
        mNormalTitle.setText(title);
    }

    @Override
    public ImageView imageView() {
        return mCoverImage;
    }

    @Override
    public void setImage(@DrawableRes int resId) {
        mCoverImage.setImageResource(resId);
    }

    @Override
    public void setImage(Bitmap bitmap) {
        mCoverImage.setImageBitmap(bitmap);
    }

    @Override
    public void setLenght(long length) {
    }

    @Override
    public void setSHVideoPlayer(IVideoPlayer niceVideoPlayer) {
        super.setSHVideoPlayer(niceVideoPlayer);
    }


    @Override
    protected void onPlayStateChanged(int playState) {
        switch (playState) {
            case NewsVideoPlayer.STATE_IDLE:
                break;
            case NewsVideoPlayer.STATE_PREPARING:
                mLoading.setVisibility(View.VISIBLE);
				updateVoiceIcon();
                mStart.setVisibility(View.GONE);
                break;
            case NewsVideoPlayer.STATE_PREPARED:
                startUpdateProgressTimer();
                break;
            case NewsVideoPlayer.STATE_PLAYING:
                UMUtils.startPlay(getContext());
                mCoverImage.setVisibility(View.GONE);
                mLoading.setVisibility(View.GONE);
                mStart.setImageResource(R.mipmap.jc_pause_normal);
                startDismissTopBottomTimer();
                //回调客户端
                MainActivityLifecycleAndStatus.getInstance().onStartPlay();
                break;
            case NewsVideoPlayer.STATE_PAUSED:
                mLoading.setVisibility(View.GONE);
                mStart.setImageResource(R.mipmap.play_normal);
				setCenterVisible(true, false);
                cancelDismissTopBottomTimer();
                MainActivityLifecycleAndStatus.getInstance().onStopPlay();
                break;
            case NewsVideoPlayer.STATE_BUFFERING_PLAYING:
                mLoading.setVisibility(View.VISIBLE);
                mStart.setImageResource(R.mipmap.jc_pause_normal);
//                mLoadText.setText("正在缓冲...");
                startDismissTopBottomTimer();
                break;
            case NewsVideoPlayer.STATE_BUFFERING_PAUSED:
                mLoading.setVisibility(View.VISIBLE);
                mStart.setImageResource(R.mipmap.play_normal);
//                mLoadText.setText("正在缓冲...");
                cancelDismissTopBottomTimer();
                MainActivityLifecycleAndStatus.getInstance().onStopPlay();
                break;
            case NewsVideoPlayer.STATE_ERROR:
                cancelUpdateProgressTimer();
                setCenterVisible(false, false);
//                mError.setVisibility(View.VISIBLE);
                break;
            case NewsVideoPlayer.STATE_COMPLETED:
                UMUtils.playOver(getContext());
                cancelUpdateProgressTimer();
                setCenterVisible(false, false);
                mCoverImage.setVisibility(View.VISIBLE);
                mStart.setImageResource(R.mipmap.play_normal);
                mStart.setVisibility(View.VISIBLE);
                reset();
                MainActivityLifecycleAndStatus.getInstance().onStopPlay();
                break;
        }
    }

    @Override
    protected void onPlayModeChanged(int playMode) {
        switch (playMode) {
            case NewsVideoPlayer.MODE_NORMAL:
                mFullScreen.setImageResource(R.mipmap.jc_enlarge);
                mFullScreen.setVisibility(View.VISIBLE);
                mBottom.setVisibility(View.INVISIBLE);
                break;
            case NewsVideoPlayer.MODE_FULL_SCREEN:
                mFullScreen.setImageResource(R.mipmap.sh_player_shrink);
                mBottom.setVisibility(View.VISIBLE);
                break;
            case NewsVideoPlayer.MODE_TINY_WINDOW:
                break;
        }
    }


    @Override
    protected void reset() {
        cancelUpdateProgressTimer();
        cancelDismissTopBottomTimer();
        mProgressBar.setProgress(0);
        mProgressBar.setSecondaryProgress(0);
        mBottom.setVisibility(View.GONE);
        mBottomProgressBar.setVisibility(View.GONE);
        mPlayTime.setVisibility(View.VISIBLE);
        mStart.setVisibility(View.VISIBLE);
        mStart.setImageResource(R.mipmap.jc_play_normal);
        mCoverImage.setVisibility(View.VISIBLE);
        mNormalTitle.setVisibility(View.VISIBLE);
        if(null != mFullScreen){
            mFullScreen.post(new Runnable() {
                @Override
                public void run() {
                    mFullScreen.setImageResource(R.mipmap.jc_enlarge);
                }
            });
        }


        mLoading.setVisibility(View.GONE);

    }

    /**
     * 注册音量监听
     */
    private void registerVolumeReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.media.VOLUME_CHANGED_ACTION");
//        if (mVolumeReceiver == null) {
//            mVolumeReceiver = new BroadcastReceiver() {
//                @Override
//                public void onReceive(Context context, Intent intent) {
//                    try {
//                        if (intent.getAction().equals("android.media.VOLUME_CHANGED_ACTION")) {
//                            AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
//                            int currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
//                            if (currentVolume == 0) {
//                                isVoiceOn = false;
//                                updateVoiceIcon();
//                            } else {
//                                isVoiceOn = true;
//                                updateVoiceIcon();
//                            }
//                        }
//                    } catch (Exception e) {
//                        LogUtil.printeException(e);
//                    }
//                }
//            };
//            mContext.registerReceiver(mVolumeReceiver, filter);
//        }
    }

    /**
     * 取消音量监听
     */
    private void unRegisterVolumeReceiver() {
//        try {
//            if (mVolumeReceiver != null) {
//                mContext.unregisterReceiver(mVolumeReceiver);
//            }
//        } catch (Exception e) {
//            LogUtil.printeException(e);
//        }

    }

    /**
     * 尽量不要在onClick中直接处理控件的隐藏、显示及各种UI逻辑。
     * UI相关的逻辑都尽量到{@link #onPlayStateChanged}和{@link #onPlayModeChanged}中处理.
     */
    @Override
    public void onClick(View v) {
        if (v == mStart ) {
            mPlayTime.setVisibility(View.GONE);
            if (mSHVideoPlayer.isPlaying() || mSHVideoPlayer.isBufferingPlaying()) {
                mSHVideoPlayer.pause();
            } else if (mSHVideoPlayer.isPaused() || mSHVideoPlayer.isBufferingPaused()) {
                mSHVideoPlayer.restart();
                if (!isWifiConnected() && isMobileConnected(getContext())) {
                    if(!showCustomToast()){
                        Toast.makeText(getContext(), "正在使用流量播放", Toast.LENGTH_SHORT).show();
                    }
                }
            } else if (mSHVideoPlayer.isIdle()) {
                mSHVideoPlayer.start();
                setCenterVisible(false, false);
                if (!isWifiConnected() && isMobileConnected(getContext())) {
                    if(!showCustomToast()){
                        Toast.makeText(getContext(), "正在使用流量播放", Toast.LENGTH_SHORT).show();
                    }
                }
            } else if (mSHVideoPlayer.isCompleted()) {
                mSHVideoPlayer.restart();
                setCenterVisible(true, true);
                if (!isWifiConnected() && isMobileConnected(getContext())) {
                    if(!showCustomToast()){
                        Toast.makeText(getContext(), "正在使用流量播放", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } else if (v == mFullScreen) {
            if (mSHVideoPlayer.isNormal() || mSHVideoPlayer.isTinyWindow()) {
                if(null != mScreenStatusChangeListener){
                    mScreenStatusChangeListener.onEnterFullScreen();
                }
                mSHVideoPlayer.enterFullScreen();
            } else if (mSHVideoPlayer.isFullScreen()) {
                mSHVideoPlayer.exitFullScreen();
                if(null != mScreenStatusChangeListener){
                    mScreenStatusChangeListener.onExitFullScreen();
                }
            }
        }
//        else if (v == mRetry) {
//            mSHVideoPlayer.restart();
//        } else if (v == mVoiceOnOff) {
//            isVoiceOn = !isVoiceOn;
//            updateVoiceIcon();
//            if (isVoiceOn) {
//                //开启声音 值最大声音的一半
//                mSHVideoPlayer.setVolume(mSHVideoPlayer.getMaxVolume() / 2);
//            } else {
//                mSHVideoPlayer.setVolume(0);
//            }
//        }
        else if (v == this) {
            if (mSHVideoPlayer.isPlaying()
                    || mSHVideoPlayer.isPaused()
                    || mSHVideoPlayer.isBufferingPlaying()
                    || mSHVideoPlayer.isBufferingPaused()) {
                setCenterVisible(!mCenterVisible, false);
            }
        }
    }

    private void updateVoiceIcon() {
//        if (mVoiceOnOff == null) return;
//        if (isVoiceOn) {
//            mVoiceOnOff.setImageResource(R.drawable.details_player_music_on);
//        } else {
//            mVoiceOnOff.setImageResource(R.drawable.details_player_music_off);
//        }
    }

    /**
     * 设置top、bottom的显示和隐藏
     *
     * @param visible true显示，false隐藏.
     */
    private void setCenterVisible(boolean visible, boolean isComplete) {
        mStart.setVisibility(visible ? View.VISIBLE : View.GONE);
        mNormalTitle.setVisibility(visible ? View.VISIBLE : View.GONE);
        if(!isComplete){
            mBottom.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
        if(visible){
            mBottomProgressBar.setVisibility(View.GONE);
        }else{
            mBottomProgressBar.setVisibility(View.VISIBLE);
        }
        mCenterVisible = visible;
        if (visible) {
            if (null != mSHVideoPlayer && !mSHVideoPlayer.isPaused() && !mSHVideoPlayer.isBufferingPaused()) {
                startDismissTopBottomTimer();
            }
        } else {
            cancelDismissTopBottomTimer();
        }
    }


    @Override
    public void run() {
        setCenterVisible(false, false);
    }

    /**
     * 开启top、bottom自动消失的timer
     */
    private void startDismissTopBottomTimer() {
        cancelDismissTopBottomTimer();
        mHandler.postDelayed(this, 3000);
    }

    /**
     * 取消top、bottom自动消失的timer
     */
    private void cancelDismissTopBottomTimer() {
        mHandler.removeCallbacks(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (mSHVideoPlayer.isBufferingPaused() || mSHVideoPlayer.isPaused()) {
            mSHVideoPlayer.restart();
        }
        long position = (long) (mSHVideoPlayer.getDuration() * seekBar.getProgress() / 100f);
        mSHVideoPlayer.seekTo(position);
        if (!mSHVideoPlayer.isCompleted()) {
            startDismissTopBottomTimer();
        }
    }

    @Override
    protected void updateProgress() {
        long position = mSHVideoPlayer.getCurrentPosition();
        long duration = mSHVideoPlayer.getDuration();
        int bufferPercentage = mSHVideoPlayer.getBufferPercentage();
        mProgressBar.setSecondaryProgress(bufferPercentage);
        int progress = (int) (100f * position / duration);
        mProgressBar.setProgress(progress);
        String pos = VideoUtil.formatTime(position);
        mCurrentPlayTime.setText(pos);
        mTotallayTime.setText(VideoUtil.formatTime(duration));
        mBottomProgressBar.setProgress(progress);
        mBottomProgressBar.setSecondaryProgress(bufferPercentage);
        tellPlayPos(pos);
    }

    @Override
    protected void showChangePosition(long duration, int newPositionProgress) {
//        mChangePositon.setVisibility(View.VISIBLE);
        long newPosition = (long) (duration * newPositionProgress / 100f);
//        mChangePositionCurrent.setText(VideoUtil.formatTime(newPosition));
        mBottomProgressBar.setProgress(newPositionProgress);
        mProgressBar.setProgress(newPositionProgress);
        mCurrentPlayTime.setText(VideoUtil.formatTime(newPosition));
    }

    @Override
    protected void hideChangePosition() {
//        mChangePositon.setVisibility(View.GONE);
    }

    @Override
    protected void showChangeVolume(int newVolumeProgress) {
//        mChangeVolume.setVisibility(View.VISIBLE);
//        mChangeVolumeProgress.setProgress(newVolumeProgress);
    }

    @Override
    protected void hideChangeVolume() {
//        mChangeVolume.setVisibility(View.GONE);
    }

    @Override
    protected void showChangeBrightness(int newBrightnessProgress) {
//        mChangeBrightness.setVisibility(View.VISIBLE);
//        mChangeBrightnessProgress.setProgress(newBrightnessProgress);
    }

    @Override
    protected void hideChangeBrightness() {
//        mChangeBrightness.setVisibility(View.GONE);
    }

    @Override
    protected void release() {
        unRegisterVolumeReceiver();
    }

    @Override
    public void setPlayTime(String time) {
        mPlayTime.setText(time);
    }

    protected void tellPlayPos(String txt){

    }

    private boolean isWifiConnected() {
        try {
            ConnectivityManager mConnectivity =
                    (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = mConnectivity.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    return true;
                }
            }
        } catch (Exception e) {
            LogUtil.printeException(e);
        }
        return false;
    }

	private boolean isMobileConnected(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		return networkInfo != null && networkInfo.isConnected();
	}

    protected boolean showCustomToast(){
        return false;
    }


}
