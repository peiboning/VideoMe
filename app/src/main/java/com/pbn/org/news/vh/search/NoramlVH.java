package com.pbn.org.news.vh.search;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pbn.org.news.R;
import com.pbn.org.news.adapter.SearchRecordAdapter;
import com.pbn.org.news.model.search.SearchRecodeModel;

/**
 * function:
 *
 * @author peiboning
 * @DATE 2018/10/30
 */
public class NoramlVH extends RecyclerView.ViewHolder{
    private TextView textView;
    private ImageView delete;
    public NoramlVH(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.record_tv);
        delete = itemView.findViewById(R.id.delete_image);
    }

    public void bindview(final SearchRecodeModel model, final SearchRecordAdapter.OnItemClickListener listener){
        textView.setText(model.getContent());
        if(model.getStatus() == SearchRecodeModel.STATUS_EDITOR){
            delete.setVisibility(View.VISIBLE);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(null != listener){
                        listener.onDelete(model);
                    }
                }
            });
        }else{
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(null != listener){
                        listener.onItemClick(model.getContent());
                    }
                }
            });
        }
    }
}
