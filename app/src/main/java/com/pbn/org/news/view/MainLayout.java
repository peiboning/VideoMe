package com.pbn.org.news.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * function:
 *
 * @author peiboning
 * @DATE 2018/10/11
 */
public class MainLayout extends LinearLayout{
    private int mSearchBarHeight;
    private View mSearchView;
    public MainLayout(Context context) {
        super(context);
    }

    public MainLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MainLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mSearchView = getChildAt(0);
        mSearchBarHeight = mSearchView.getMeasuredHeight();
//        scrollBy(0, mSearchBarHeight/2);
    }

    private int mLastInterceptMoveX;
    private int mLastInterceptMoveY;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        boolean intercept = false;
        switch (action){
            case MotionEvent.ACTION_DOWN:
                mLastInterceptMoveX = (int) ev.getX();
                mLastInterceptMoveY = (int) ev.getY();
                mLastMoveX = (int) ev.getX();
                mLastMoveY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = (int) (ev.getX() - mLastInterceptMoveX);
                int dy = (int) (ev.getY() - mLastInterceptMoveY);
                if(Math.abs(dx) < Math.abs(dy)){
                    Rect rect = getSearchBarVisibleHeight();
                    int searchVisibleHeight = rect.bottom - rect.top;

                    if(rect.bottom < 0){
                        searchVisibleHeight = 0;
                    }

                    if(dy > 0){//向下滑
                        if(searchVisibleHeight<mSearchBarHeight){
                            intercept = true;
                        }
                    }else{
                        if(rect.bottom >= 0){
                            if(searchVisibleHeight >0){
                                intercept = true;
                            }
                        }
                    }
                    Log.e("onInterceptTouchEvent", "intercept:" + intercept + "   rect:" + rect.toShortString());
                }
                break;
        }
        Log.e("onInterceptTouchEvent", "intercept:" + intercept + "----"+ev.getAction());
        mLastInterceptMoveX = (int) ev.getX();
        mLastInterceptMoveY = (int) ev.getY();
        return intercept;
    }

    private int mLastMoveX;
    private int mLastMoveY;
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        boolean isconsumer = false;
        switch (action){
            case MotionEvent.ACTION_MOVE:
                int dx = (int) (ev.getX() - mLastMoveX);
                int dy = (int) (ev.getY() - mLastMoveY);
                if(Math.abs(dx) < Math.abs(dy)){
                    int moveY = 0;
                    Rect rect = getSearchBarVisibleHeight();
                    int searchVisibleHeight = rect.bottom - rect.top;
                    if(rect.bottom < 0){
                        searchVisibleHeight = 0;
                    }
                    if(dy > 0){//向下滑
                        if(searchVisibleHeight<mSearchBarHeight){
                            if((mSearchBarHeight - searchVisibleHeight) > dy){
                                moveY = dy;
                            }else{
                                moveY = (mSearchBarHeight - searchVisibleHeight);
                            }
                            scrollBy(0, -moveY);
                            isconsumer = true;
                        }
                    }else{
                        if(rect.bottom >= 0){
                            if(searchVisibleHeight >0){
                                if(searchVisibleHeight < 2){
                                    searchVisibleHeight = 2;
                                }
                                if(searchVisibleHeight/2 > Math.abs(dy)){
                                    moveY = Math.abs(dy);
                                }else{
                                    moveY = searchVisibleHeight/2;
                                }
                                scrollBy(0, moveY);
                                isconsumer = true;
                            }
                        }
                    }
                }
                break;
        }
        mLastMoveX = (int) ev.getX();
        mLastMoveY = (int) ev.getY();
//        Log.e("onTouchEvent", "----"+ev.getAction());
        return super.onTouchEvent(ev);
    }

    private Rect getSearchBarVisibleHeight(){
        Rect rect = new Rect();
        mSearchView.getGlobalVisibleRect(rect);
        return rect;
    }
}
