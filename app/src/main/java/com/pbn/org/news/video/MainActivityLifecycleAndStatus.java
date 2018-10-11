package com.pbn.org.news.video;

import android.content.res.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 */

public class MainActivityLifecycleAndStatus {

    /**
     * 非全屏
     */
    public static final int ORIENTATION_PORTRAIT = 1;
    /**
     * 全屏展示
     */
    public static final int ORIENTATION_LANDSCAPE = 2;
    /**
     * 包框广告不在展示状态
     */
    public final static int PLAYER_VIEW_STATUS_WRAPFRAME_OFF = 3;
    /**
     * 包框广告正在展示状态
     */
    public final static int PLAYER_VIEW_STATUS_WRAPFRAME_ON = 4;

    public static int wrapframeStatus = PLAYER_VIEW_STATUS_WRAPFRAME_OFF;

    private static final String TAG = "MainActivityLifecycleAndStatus";
    public static MainActivityLifecycleAndStatus instance;
    public static Configuration configuration;
    public static int orientation = -1;
    private static final List<IActivityCallback> onCreateCallbacks = new ArrayList<IActivityCallback>();
    private static final List<IActivityCallback> onActivityResultCallbacks = new ArrayList<IActivityCallback>();
    private static final List<IActivityCallback> onStartCallbacks = new ArrayList<IActivityCallback>();
    private static final List<IActivityCallback> onResumeCallbacks = new ArrayList<IActivityCallback>();
    private static final List<IActivityCallback> onPauseCallbacks = new ArrayList<IActivityCallback>();
    private static final List<IActivityCallback> onStopCallbacks = new ArrayList<IActivityCallback>();
    private static final List<IActivityCallback> startPlayCallbacks = new ArrayList<IActivityCallback>();
    private static final List<IActivityCallback> onStartPlayCallbacks = new ArrayList<IActivityCallback>();
    private static final List<IActivityCallback> onStopPlayCallbacks = new ArrayList<IActivityCallback>();
    private static final List<IActivityCallback> stopPlayCallbacks = new ArrayList<IActivityCallback>();
    private static final List<IActivityCallback> onDestoryCallbacks = new ArrayList<IActivityCallback>();


    private static final List<IActivityCallback> onVideoPauseCallbacks = new ArrayList<IActivityCallback>();
    private static final List<IActivityCallback> onVideoResumeCallbacks = new ArrayList<IActivityCallback>();
    /**
     * 切换为全屏状态回调
     */
    private static final List<IActivityCallback> onFullScreenCallbacks = new ArrayList<IActivityCallback>();
    /**
     * 切换为竖屏回调
     */
    private static final List<IActivityCallback> onNormalScreenCallbacks = new ArrayList<IActivityCallback>();

    private MainActivityLifecycleAndStatus() {
    }

    public static MainActivityLifecycleAndStatus getInstance() {
        if (instance == null) {
            instance = new MainActivityLifecycleAndStatus();
        }
        return instance;
    }

    /**
     * 播放页Activity生命周期回调
     */
    public void onCreate() {
        try {
            LogUtil.d(TAG, "onCreate");
            for (IActivityCallback callback : onCreateCallbacks) {
                callback.onEvent(null);
            }
        } catch (Exception e) {
            LogUtil.printeException(e);
        }

    }

    /**
     * 播放页Activity生命周期回调
     */
    public void onStart() {
        try {
            LogUtil.d(TAG, "onStart");
            for (IActivityCallback callback : onStartCallbacks) {
                callback.onEvent(null);
            }
        } catch (Exception e) {
            LogUtil.printeException(e);
        }

    }

    /**
     * 播放页Activity生命周期回调
     */
    public void onResume() {
        try {
            LogUtil.d(TAG, "onResume");
            for (IActivityCallback callback : onResumeCallbacks) {
                callback.onEvent(null);
            }
        } catch (Exception e) {
            LogUtil.printeException(e);
        }

    }

    /**
     * 播放页Activity生命周期回调
     */
    public void onPause() {
        try {
            LogUtil.d(TAG, "onPause");
            for (IActivityCallback callback : onPauseCallbacks) {
                callback.onEvent(null);
            }
        } catch (Exception e) {
            LogUtil.printeException(e);
        }

    }

    /**
     * 播放页Activity生命周期回调
     */
    public void onStop() {
        try {
            LogUtil.d(TAG, "onStop");
            for (IActivityCallback callback : onStopCallbacks) {
                callback.onEvent(null);
            }
        } catch (Exception e) {
            LogUtil.printeException(e);
        }

    }

    /**
     * 播放页Activity生命周期回调
     */
    public void onStartPlay() {
        try {
            LogUtil.d(TAG, "onStartPlay");
            for (IActivityCallback callback : onStartPlayCallbacks) {
                callback.onEvent(null);
            }
        } catch (Exception e) {
            LogUtil.printeException(e);
        }

    }

    /**
     * 播放页video  客户端通知sdk开始播放回调
     */
    public void startPlay() {
        try {
            LogUtil.d(TAG, "startPlay");
            for (IActivityCallback callback : startPlayCallbacks) {
                callback.onEvent(null);
            }
        } catch (Exception e) {
            LogUtil.printeException(e);
        }

    }

    /**
     * 播放页Activity生命周期回调
     */
    public void onStopPlay() {
        try {
            LogUtil.d(TAG, "onStopPlay");
            for (IActivityCallback callback : onStopPlayCallbacks) {
                callback.onEvent(null);
            }
        } catch (Exception e) {
            LogUtil.printeException(e);
        }

    }

    /**
     * 播放页video  客户端通知sdk停止播放回调
     */
    public void stopPlay() {
        try {
            LogUtil.d(TAG, "stopPlay");
            for (IActivityCallback callback : stopPlayCallbacks) {
                callback.onEvent(null);
            }
        } catch (Exception e) {
            LogUtil.printeException(e);
        }

    }

    /**
     * 播放页Activity生命周期回调
     */
    public void onDestory() {
        try {
            LogUtil.d(TAG, "onDestory");
            for (IActivityCallback callback : onDestoryCallbacks) {
                callback.onEvent(null);
            }
            onCreateCallbacks.clear();
            onStartCallbacks.clear();
            onResumeCallbacks.clear();
            onPauseCallbacks.clear();
            onStopCallbacks.clear();
            onDestoryCallbacks.clear();
            onFullScreenCallbacks.clear();
            onNormalScreenCallbacks.clear();
            onVideoPauseCallbacks.clear();
            onActivityResultCallbacks.clear();
            onStartPlayCallbacks.clear();
            onStopPlayCallbacks.clear();
            startPlayCallbacks.clear();
            stopPlayCallbacks.clear();
            orientation = -1;
            wrapframeStatus = PLAYER_VIEW_STATUS_WRAPFRAME_OFF;
            configuration = null;
        } catch (Exception e) {
            LogUtil.printeException(e);
        }

    }

    public void onConfigurationChanged(Configuration newConfig) {
        try {
            LogUtil.d(TAG, "onConfigurationChanged" + newConfig.orientation);
            configuration = newConfig;
            orientation = newConfig.orientation;
            LogUtil.d(TAG, "onConfigurationChanged" + orientation);
            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                for (IActivityCallback callback : onFullScreenCallbacks) {
                    callback.onEvent(null);
                }
            } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
                for (IActivityCallback callback : onNormalScreenCallbacks) {
                    callback.onEvent(null);
                }
            }
        } catch (Exception e) {
            LogUtil.printeException(e);
        }

    }

    /**
     * 当视频暂停的时候调用
     */
    public void onVideoPause() {
        try {
            LogUtil.d(TAG, "onVideoPause");
            for (IActivityCallback callback : onVideoPauseCallbacks) {
                callback.onEvent(null);
            }
        } catch (Exception e) {
            LogUtil.printeException(e);
        }

    }

    /**
     * 当视频继续播放的时候调用
     */
    public void onVideoResume() {
        try {
            LogUtil.d(TAG, "onVideoResume");
            for (IActivityCallback callback : onVideoResumeCallbacks) {
                callback.onEvent(null);
            }
        } catch (Exception e) {
            LogUtil.printeException(e);
        }

    }


    public void addOnVideoPauseCallback(IActivityCallback callback) {
        try {
            onVideoPauseCallbacks.add(callback);

        } catch (Exception e) {
            LogUtil.printeException(e);
        }
    }

    public void addOnVideoResumeCallback(IActivityCallback callback) {
        try {
            onVideoResumeCallbacks.add(callback);
        } catch (Exception e) {
            LogUtil.printeException(e);
        }
    }

    public void addOnCreateCallback(IActivityCallback callback) {
        try {
            onCreateCallbacks.add(callback);

        } catch (Exception e) {
            LogUtil.printeException(e);
        }
    }

    public void addOnStartCallback(IActivityCallback callback) {
        try {
            onStartCallbacks.add(callback);

        } catch (Exception e) {
            LogUtil.printeException(e);
        }
    }

    public void addOnResumeCallback(IActivityCallback callback) {
        try {
            onResumeCallbacks.add(callback);

        } catch (Exception e) {
            LogUtil.printeException(e);
        }
    }

    public void addOnPauseCallback(IActivityCallback callback) {
        try {
            onPauseCallbacks.add(callback);

        } catch (Exception e) {
            LogUtil.printeException(e);
        }
    }

    public void addOnStopCallback(IActivityCallback callback) {
        try {
            onStopCallbacks.add(callback);

        } catch (Exception e) {
            LogUtil.printeException(e);
        }
    }

    public void addOnDestoryCallback(IActivityCallback callback) {
        try {
            onDestoryCallbacks.add(callback);

        } catch (Exception e) {
            LogUtil.printeException(e);
        }
    }


    public void addOnFullScreenCallback(IActivityCallback callback) {
        try {
            onFullScreenCallbacks.add(callback);
        } catch (Exception e) {
            LogUtil.printeException(e);
        }
    }

    public void removeOnFullScreenCallback(IActivityCallback callback) {
        try {
            onFullScreenCallbacks.remove(callback);
        } catch (Exception e) {
            LogUtil.printeException(e);
        }
    }

    public void addOnStopPlayCallback(IActivityCallback callback) {
        try {
            if(!onStopCallbacks.contains(callback)){
                onStopPlayCallbacks.add(callback);
            }
        } catch (Exception e) {
            LogUtil.printeException(e);
        }
    }

    public void removeOnStopPlayCallback(IActivityCallback callback) {
        try {
            onStopPlayCallbacks.remove(callback);
        } catch (Exception e) {
            LogUtil.printeException(e);
        }
    }

    public void addOnStartPlayCallback(IActivityCallback callback) {
        try {
            onStartPlayCallbacks.add(callback);
        } catch (Exception e) {
            LogUtil.printeException(e);
        }
    }

    public void removeOnStartPlayCallback(IActivityCallback callback) {
        try {
            onStartPlayCallbacks.remove(callback);
        } catch (Exception e) {
            LogUtil.printeException(e);
        }
    }

    public void addStartPlayCallback(IActivityCallback callback) {
        try {
            startPlayCallbacks.add(callback);
        } catch (Exception e) {
            LogUtil.printeException(e);
        }
    }

    public void removeStartPlayCallback(IActivityCallback callback) {
        try {
            startPlayCallbacks.remove(callback);
        } catch (Exception e) {
            LogUtil.printeException(e);
        }
    }

    public void addStopPlayCallback(IActivityCallback callback) {
        try {
            stopPlayCallbacks.add(callback);
        } catch (Exception e) {
            LogUtil.printeException(e);
        }
    }

    public void removeStopPlayCallback(IActivityCallback callback) {
        try {
            stopPlayCallbacks.remove(callback);
        } catch (Exception e) {
            LogUtil.printeException(e);
        }
    }

    public void addOnActivityResultCallback(IActivityCallback callback) {
        try {
            onActivityResultCallbacks.add(callback);
        } catch (Exception e) {
            LogUtil.printeException(e);
        }
    }

    public void removeOnActivityResultCallback(IActivityCallback callback) {
        try {
            onActivityResultCallbacks.remove(callback);
        } catch (Exception e) {
            LogUtil.printeException(e);
        }
    }

    public void addOnNormalScreenCallback(IActivityCallback callback) {
        try {
            onNormalScreenCallbacks.add(callback);
        } catch (Exception e) {
            LogUtil.printeException(e);
        }
    }

    public void removeOnNormalScreenCallback(IActivityCallback callback) {
        try {
            onNormalScreenCallbacks.remove(callback);
        } catch (Exception e) {
            LogUtil.printeException(e);
        }
    }

    public void removeOnVideoPauseCallback(IActivityCallback callback) {
        try {
            onVideoPauseCallbacks.remove(callback);

        } catch (Exception e) {
            LogUtil.printeException(e);
        }
    }

    public void removeOnVideoResumeCallback(IActivityCallback callback) {
        try {
            onVideoResumeCallbacks.remove(callback);

        } catch (Exception e) {
            LogUtil.printeException(e);
        }
    }

    public void removeOnCreateCallback(IActivityCallback callback) {
        try {
            onCreateCallbacks.remove(callback);

        } catch (Exception e) {
            LogUtil.printeException(e);
        }
    }

    public void removeOnStartCallback(IActivityCallback callback) {
        try {
            onStartCallbacks.remove(callback);

        } catch (Exception e) {
            LogUtil.printeException(e);
        }
    }

    public void removeOnResumeCallback(IActivityCallback callback) {
        try {
            onResumeCallbacks.remove(callback);

        } catch (Exception e) {
            LogUtil.printeException(e);
        }
    }

    public void removeOnPauseCallback(IActivityCallback callback) {
        try {
            onPauseCallbacks.remove(callback);

        } catch (Exception e) {
            LogUtil.printeException(e);
        }
    }

    public void removeOnStopCallback(IActivityCallback callback) {
        try {
            onStopCallbacks.remove(callback);

        } catch (Exception e) {
            LogUtil.printeException(e);
        }
    }

    public void removeOnDestoryCallback(IActivityCallback callback) {
        try {
            onDestoryCallbacks.remove(callback);

        } catch (Exception e) {
            LogUtil.printeException(e);
        }
    }

    public boolean onBackPressd() {
        return NewsVideoPlayerManager.instance().onBackPressd();
    }
}