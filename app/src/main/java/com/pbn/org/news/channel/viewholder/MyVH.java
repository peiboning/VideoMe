package com.pbn.org.news.channel.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pbn.org.news.R;
import com.pbn.org.news.channel.inte.IChannelEditor;

public class MyVH extends RecyclerView.ViewHolder {
    private TextView editTv;
    private IChannelEditor editor;
    private boolean isEdit;
    public MyVH(View itemView, IChannelEditor adapter) {
        super(itemView);
        editor = adapter;
        editTv = itemView.findViewById(R.id.channel_edit_tv);
        editTv.setText(R.string.edit);
        editTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEdit){
                    editTv.setText(R.string.edit);
                }else{
                    editTv.setText(R.string.finish);
                }
                isEdit = !isEdit;
                if(null != editor){
                    editor.onEditStateChange(isEdit);
                }
            }
        });
    }
}
