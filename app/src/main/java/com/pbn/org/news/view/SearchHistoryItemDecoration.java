package com.pbn.org.news.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.pbn.org.news.model.search.SearchRecodeModel;


/**
 * function:
 *
 * @author peiboning
 * @DATE 2018/10/30
 */
public class SearchHistoryItemDecoration extends RecyclerView.ItemDecoration{
    private Drawable mSpliteDrawable;
    private int lineWidth = 2;

    public SearchHistoryItemDecoration(){
        mSpliteDrawable = new ColorDrawable(Color.parseColor("#DEDEDE"));
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        GridLayoutManager gridLayoutManager = (GridLayoutManager) parent.getLayoutManager();
        int viewPos = parent.getChildAdapterPosition(view);
        GridLayoutManager.SpanSizeLookup lookup = gridLayoutManager.getSpanSizeLookup();
        int spanCount = lookup.getSpanSize(viewPos);
        if(spanCount == 1){
            outRect.set(2,2,2,2);
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawHorizontalLine(c, parent);
        drawVerticalLine(c, parent);
    }

    private void drawHorizontalLine(Canvas c, RecyclerView parent) {
        int cc = parent.getChildCount();
        for(int i = 0;i<cc;i++){
            View view = parent.getChildAt(i);
            int pos = parent.getChildAdapterPosition(view);
            int type = parent.getAdapter().getItemViewType(pos);
            mSpliteDrawable.setBounds(view.getLeft(), view.getBottom(), view.getRight(), view.getBottom() + lineWidth);
            mSpliteDrawable.draw(c);
        }
    }
    private void drawVerticalLine(Canvas c, RecyclerView parent) {
        int cc = parent.getChildCount();
        int offset = 0;
        GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
        for(int i = 0;i<cc;i++){
            View view = parent.getChildAt(i);

            int pos = parent.getChildAdapterPosition(view);
            if(SearchRecodeModel.TYPE_NORMAL != parent.getAdapter().getItemViewType(pos)){
                offset++;
            }

            if((i - offset) % layoutManager.getSpanCount() != 0){
                int left = view.getLeft() - lineWidth;
                int top = view.getTop();
                int right = left + lineWidth;
                int bottom = view.getBottom() + lineWidth;

                mSpliteDrawable.setBounds(left, top, right, bottom);
                mSpliteDrawable.draw(c);
            }

        }
    }
}
