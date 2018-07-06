package com.pbn.org.news.channel.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pbn.org.news.R;
import com.pbn.org.news.channel.inte.IOnMyItemClick;
import com.pbn.org.news.model.Channel;

public class MyItemVH extends RecyclerView.ViewHolder {
    private TextView textView;
    private ImageView deleteIcon;
    private IOnMyItemClick click;
    public MyItemVH(View itemView, final IOnMyItemClick click) {
        super(itemView);
        textView = itemView.findViewById(R.id.my_channel_item_tv);
        deleteIcon = itemView.findViewById(R.id.channel_edit_icon);
        deleteIcon.setVisibility(View.GONE);
        this.click = click;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != click){
                    click.onMyItemClick(MyItemVH.this);
                }
            }
        });
    }

    public void show(Channel channel){
        textView.setText(channel.getTitle());
    }

    public void setIsEditable(boolean isEditable){
        deleteIcon.setVisibility(isEditable ? View.VISIBLE : View.GONE);
    }
}
