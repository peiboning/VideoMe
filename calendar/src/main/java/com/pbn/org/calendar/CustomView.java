package com.pbn.org.calendar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;

public class CustomView extends FrameLayout{
    private View container;
    private View title;
    private MyWebView webView;
    private FrameLayout bottomView;
    private View bottom_cover;
    private int maxMove;
    private float moveProcess;
    private boolean isTopStatus;
    public CustomView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public CustomView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View view = inflate(context, R.layout.custom_layout, this);
        title = view.findViewById(R.id.title);
        container = view.findViewById(R.id.container);
        webView = view.findViewById(R.id.web);
        webView.loadUrl("http://m.sohu.com");
        bottomView = view.findViewById(R.id.bottom_tips);
        bottom_cover = view.findViewById(R.id.bottom_cover);
        bottom_cover.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        setBackgroundColor(Color.RED);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e("onSizeChanged", "onSizeChanged...........");
        process();
    }

    private void process() {
        int contianerHeight = container.getMeasuredHeight();
        int titleHeight = title.getMeasuredHeight();
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        float percent = 0;
        if(isTopStatus){
            percent = moveProcess;
        }else{
            percent = 1 - moveProcess;
        }

        int webTopMargin = (int) (contianerHeight * percent);
        if(webTopMargin <= titleHeight){
            webTopMargin = titleHeight;
        }

        if(webTopMargin >= contianerHeight){
            webTopMargin = contianerHeight;
        }

        lp.topMargin = webTopMargin;
        bottomView.setLayoutParams(lp);

        lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int titleTopMargin = (int) (-titleHeight * percent);
        if(titleTopMargin >=0){
            titleTopMargin = 0;
        }

        if(titleTopMargin<=-titleHeight){
            titleTopMargin = -titleHeight;
        }
        lp.topMargin = titleTopMargin;
        title.setLayoutParams(lp);

        maxMove = container.getMeasuredHeight() - titleHeight;
    }


    private float interceptY;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if(isTopStatus){
            switch (action){
                case MotionEvent.ACTION_DOWN:
                    interceptY = ev.getRawY();
                    Log.e("CustomView", "onInterceptTouchEvent down : " + interceptY);
                    break;
                case MotionEvent.ACTION_MOVE:
                    if(webView.getScrollY() == 0){
                        float dy = (ev.getRawY() - interceptY);
                        Log.e("CustomView", "onInterceptTouchEvent move : " + dy);
                        if(dy > 0){
                            return true;
                        }
                        if(isTopStatus){
                        }else{
                            if(dy < 0){
                                return true;
                            }
                        }
                    }else{
                        interceptY = ev.getRawY();
                    }

                    break;
                    case MotionEvent.ACTION_UP:
                        Log.e("CustomView", "onInterceptTouchEvent up : ");
                        break;
            }
        }else{
            switch (action){
                case MotionEvent.ACTION_DOWN:
                    interceptY = ev.getRawY();
                    Log.e("CustomView", "onInterceptTouchEvent down : " + interceptY);
                    break;
                case MotionEvent.ACTION_MOVE:
                    if(webView.getScrollY() == 0){
                        float dy = (ev.getRawY() - interceptY);
                        Log.e("CustomView", "onInterceptTouchEvent move : " + dy);
                        if(dy < 0){
                            return true;
                        }
                    }

                    break;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    private int touchY;
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                touchY = (int) ev.getRawY();
                Log.e("CustomView", "onTouchEvent down : " + touchY);
                return true;
            case MotionEvent.ACTION_MOVE:
                Log.e("CustomView", "onTouchEvent move : " + touchY);
                float dy = (ev.getRawY() - interceptY);
                if(isTopStatus){
                    if(dy>0){
                        moveProcess = Math.abs(dy/maxMove);
                        process();
                        return true;
                    }
                }else{
                    if(dy <0){
                        moveProcess = Math.abs(dy/maxMove);
                        process();
                        return true;
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                LayoutParams lp = (LayoutParams) title.getLayoutParams();
                if(lp.topMargin == 0){
                    isTopStatus = true;
                }else if(lp.topMargin <= -title.getMeasuredHeight()){
                    isTopStatus = false;
                }else{
                    animalToTarget();
                }
                Log.e("CustomView", "onTouchEvent up : isTopStatus: " + isTopStatus + "  moveprocess:" + moveProcess + ", lp.topmargin:" + lp.topMargin + "  ,height:" + title.getMeasuredHeight());
                break;
        }
        return super.onTouchEvent(ev);
    }

    private void animalToTarget(){
        Log.e("CustomView", "animalToTarget : isTopStatus: " + isTopStatus);

        float tagetValue = moveProcess > 0.5 ?1.0f:0.0f;

        ValueAnimator animator = ValueAnimator.ofFloat(moveProcess, tagetValue);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                moveProcess = value;
                process();
            }
        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                LayoutParams lp = (LayoutParams) title.getLayoutParams();
                if(lp.topMargin == 0){
                    isTopStatus = true;
                }else{
                    isTopStatus = false;
                }
            }
        });

        animator.setDuration(500);
        animator.start();

    }
}
