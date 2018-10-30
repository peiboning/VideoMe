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
public class NoramlVH extends RecyclerView.ViewHolder{
    private TextView textView;
    public NoramlVH(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.record_tv);
    }

    public void bindview(String text){
        textView.setText(text);
    }
}
