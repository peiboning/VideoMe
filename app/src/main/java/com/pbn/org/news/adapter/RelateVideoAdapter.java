package com.pbn.org.news.adapter;

import android.content.Context;

import com.pbn.org.news.detail.VideoDetailActivity;
import com.pbn.org.news.model.common.NewsBean;
import com.pbn.org.news.model.haokan.SearchVideo;

import java.util.List;

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

    public void updateResult(List<SearchVideo> list, NewsBean bean){
        SearchVideo title = new SearchVideo();
        title.setContentType(SearchVideo.CONTENT_TYPE_DETAIL_TITLE);
        title.setTitle(bean.getTitle());
        list.add(0, title);
        updateResult(list);

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}
