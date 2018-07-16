package com.pbn.org.news.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pbn.org.news.R;
import com.pbn.org.news.utils.LogUtils;
import com.pbn.org.news.utils.NetUtil;

public class RefreshHeaderView extends FrameLayout implements IResfreshHeaderView {
    private View rootView;
    private int mHeight;
    private int state = STATE_DEF;
    private DotView dotView;
    private TextView tips;
    private TextView numTips;
    public RefreshHeaderView(@NonNull Context context) {
        super(context);
        init();
    }

    public RefreshHeaderView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RefreshHeaderView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        rootView = LayoutInflater.from(getContext()).inflate(R.layout.listview_header, null);

        dotView = rootView.findViewById(R.id.dot_view);
        tips = rootView.findViewById(R.id.head_tips);
        numTips = rootView.findViewById(R.id.head_update_tips);

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        addView(rootView, params);

        setLayoutParams(new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mHeight = getMeasuredHeight();
    }

    @Override
    public void onMove(int dy) {
        setVisvibleHeight(dy);
    }

    @Override
    public void release2Resfresh() {
        resfresh();
        dotView.setProgress(1);
        dotView.startLoading();
    }

    @Override
    public void reset(boolean flag) {
        dotView.stopLoading();
        state = STATE_RESET;
        final int start = flag ? mHeight:rootView.getLayoutParams().height;
        int end = 0;

        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int height = (int) animation.getAnimatedValue();
                LayoutParams layoutParams = (LayoutParams) rootView.getLayoutParams();
                layoutParams.height = height;
                rootView.setLayoutParams(layoutParams);
                dotView.setProgress(height * 1.0f/mHeight);
//                float alph = height*1.0f/start;
//                rootView.setAlpha(alph);
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                tips.setVisibility(VISIBLE);
                dotView.setVisibility(VISIBLE);
                numTips.setVisibility(GONE);
            }
        });

        animator.setDuration(500);
        if(flag){
            animator.setStartDelay(1000);
        }
        animator.start();

    }

    @Override
    public void updateNumTip(int num) {
        state = STATE_SHOW_UPDATE_TIPS;
        dotView.stopLoading();
        tips.setVisibility(GONE);
        dotView.setVisibility(GONE);
        if(num <= 0){
            numTips.setText(getErrorInfo());
        }else{
            numTips.setText("为你找到了" + num + "条更新");
        }
        ViewGroup.LayoutParams layoutParams = numTips.getLayoutParams();
        layoutParams.height = mHeight;
        layoutParams.width = getWidth();
        numTips.setLayoutParams(layoutParams);
        numTips.setVisibility(VISIBLE);
        reset(true);
    }

    private String getErrorInfo(){
        String info = "sorry,更新失败";
        if(!NetUtil.checkNet(getContext())){
            info = "无网络";
        }
        return info;
    }

    private void setVisvibleHeight(int dh){
        int nowH = rootView.getHeight();
        int tH = nowH + dh;
//        LogUtils.e("onMove", "target Height is " + tH);
        if(tH<=0){
            return;
        }

        if(tH > mHeight*9 /10 ){
            state = STATE_RELEASE_REFRESH;
            tips.setText("松手刷新");
        }else{
            state = STATE_MOVE;
            tips.setText("下拉刷新");
        }

        dotView.setProgress(tH * 1.0f/mHeight);

        LayoutParams layoutParams = (LayoutParams) rootView.getLayoutParams();
        layoutParams.height = tH;
        rootView.setLayoutParams(layoutParams);
    }

    private void resfresh(){
        state = STATE_RESFRESHING;
        int start = rootView.getLayoutParams().height;
        int end = mHeight;

        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int height = (int) animation.getAnimatedValue();
                LayoutParams layoutParams = (LayoutParams) rootView.getLayoutParams();
                layoutParams.height = height;
                rootView.setLayoutParams(layoutParams);
            }
        });

        animator.setDuration(500);
        animator.start();
    }

    public int getState(){
        return state;
    }

    public int getVisbleHeight(){
        return rootView.getHeight();
    }
    public int getAllHeight(){
        return mHeight;
    }
}
