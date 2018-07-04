package com.pbn.org.news.view;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Scroller;

/*
处理滑动的view
 */
public class SlideLayout extends FrameLayout {
    private static final int MIN_START = 2;
    private static final int MIN_CLOSE = 5;
    private Scroller mScroller;
    private int mScrollSlop;
    private OnFinishListener listener;

    public interface OnFinishListener{
        void onFinish();
    }


    public SlideLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public SlideLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SlideLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mScroller = new Scroller(getContext());
        mScrollSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    public void setOnFinishListener(OnFinishListener listener){
        this.listener = listener;
    }

    public void bindActivity(Activity activity){
        ViewGroup decoreView = (ViewGroup) activity.getWindow().getDecorView();
        View rootView = decoreView.getChildAt(0);
        decoreView.removeView(rootView);
        addView(rootView);
        decoreView.addView(this);
    }

    private int mLastInterceptX;
    private int mLastInterceptY;
    private int mInterceptDownX;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean isIntercept = false;
        int x = (int) ev.getRawX();
        int y = (int) ev.getRawY();
        int action = ev.getAction();

        switch (action){
            case MotionEvent.ACTION_DOWN:
                mLastInterceptX = x;
                mLastInterceptY = y;
                mInterceptDownX = x;
                break;
            case MotionEvent.ACTION_MOVE:
                int dX = x - mLastInterceptX;
                int dY = y - mLastInterceptY;

                if(mInterceptDownX < getWidth()/ MIN_START && (Math.abs(dX) > Math.abs(dY)) ){
                    isIntercept = true;
                }
                mLastInterceptX = x;
                mLastInterceptY = y;
                break;
            default:
                break;

        }

        return isIntercept;
    }

    private int mTouchDownX;
    private int mLastTouchX;
    private int mLastTouchY;
    private boolean isStartScrolled;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                mTouchDownX = x;
                mLastTouchY = y;
                mLastTouchX = x;
                break;
            case MotionEvent.ACTION_MOVE:
                int dX = x - mLastTouchX;
                int dY = y - mLastTouchY;
                if(!isStartScrolled && mTouchDownX < getWidth()/ MIN_START && (Math.abs(dX) > Math.abs(dY))){
                    isStartScrolled = true;
                }

                if(isStartScrolled){
                    int moveX = mLastTouchX - x;
                    if(getScrollX() + moveX >= 0){
                        scrollBack();
                    }else{
                        scrollBy(moveX, 0);
                    }
                }
                mLastTouchY = y;
                mLastTouchX = x;
                break;
            case MotionEvent.ACTION_UP:
                isStartScrolled = false;
                if(-getScrollX() > getWidth()/MIN_CLOSE){
                    scrollClose();
                }else{
                    scrollBack();
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    private void scrollBack() {
        int startX = getScrollX();
        int dx = -getScrollX();
        mScroller.startScroll(startX, 0, dx, 0, 500);
        postInvalidate();
    }

    private void scrollClose() {
        int startX = getScrollX();
        int dx = -getScrollX() - getWidth();
        mScroller.startScroll(startX, 0, dx, 0, 500);
        postInvalidate();
    }

    @Override
    public void computeScroll() {
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(), 0);
            postInvalidate();
        }else if(-getScrollX() >= getWidth()){
            if(null != listener){
                listener.onFinish();
            }
        }
    }
}