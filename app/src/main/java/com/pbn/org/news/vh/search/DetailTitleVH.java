package com.pbn.org.news.vh.search;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pbn.org.news.R;
import com.pbn.org.news.model.haokan.SearchVideo;
import com.pbn.org.news.view.KeyBoardTextView;

/**
 * function:
 *
 * @author peiboning
 * @DATE 2018/11/07
 */
public class DetailTitleVH extends RecyclerView.ViewHolder{
    private KeyBoardTextView title;
    public DetailTitleVH(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.title);
    }

    public void bindView(SearchVideo data){
        title.setText(data.getTitle(), TextView.BufferType.NORMAL);
        title.start();
    }
}
