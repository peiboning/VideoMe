package com.pbn.org.news.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;

/**
 * function:
 *
 * @author peiboning
 * @DATE 2018/10/30
 */
public class SearchGrideLayoutManager extends GridLayoutManager {
    public SearchGrideLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public SearchGrideLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public SearchGrideLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }
}
