package com.pbn.org.news.adapter;

import android.content.Context;

import com.pbn.org.news.detail.VideoDetailActivity;

/**
 * function:
 *
 * @author peiboning
 * @DATE 2018/11/02
 */
public class RelateVideoAdapter extends SearchResultAdapter{
    public RelateVideoAdapter(Context context) {
        super(context);
        setDetailPage(VideoDetailActivity.SOURCE_SELF);
    }
}
