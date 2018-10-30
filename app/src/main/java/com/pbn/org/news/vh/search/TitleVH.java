package com.pbn.org.news.vh.search;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pbn.org.news.R;

/**
 * function:
 *
 * @author peiboning
 * @DATE 2018/10/30
 */
public class TitleVH extends RecyclerView.ViewHolder{
    private TextView title;
    public TitleVH(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.record_title_tv);
    }

    public void bindView(String text){
        title.setText(text);
    }
}
