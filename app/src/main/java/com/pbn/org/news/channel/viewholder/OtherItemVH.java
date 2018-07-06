package com.pbn.org.news.channel.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pbn.org.news.R;
import com.pbn.org.news.model.Channel;

public class OtherItemVH extends RecyclerView.ViewHolder {
    private TextView textView;
    public OtherItemVH(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.other_channel_item_tv);
    }

    public void show(Channel channel){
        textView.setText(channel.getTitle());
    }
}
