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
public class TitleVH extends RecyclerView.ViewHolder{
    private TextView title;

    private ImageView image;
    private TextView actionView;

    public TitleVH(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.record_title_tv);
        image = itemView.findViewById(R.id.action_image);
        actionView = itemView.findViewById(R.id.action_view);
    }

    public void bindView(final SearchRecodeModel model, boolean isNeedshowMore,boolean isExpand, final SearchRecordAdapter.OnItemClickListener listener){
        if(SearchRecodeModel.TYPE_HISTORY_TITLE == model.getType()){
            title.setText(model.getContent());
            if(isNeedshowMore){
                image.setImageResource(isExpand ? R.drawable.arrow_up:R.drawable.arrow_down );
                image.setVisibility(View.VISIBLE);

                title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(null != listener){
                            listener.onExpandHistory();
                        }
                    }
                });

            }
            if(SearchRecodeModel.STATUS_EDITOR == model.getStatus()){
                actionView.setText("完成");
            }else{
                actionView.setText("编辑");
            }
            actionView.setVisibility(View.VISIBLE);
            actionView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(SearchRecodeModel.STATUS_EDITOR == model.getStatus()){
                        if(null != listener){
                            listener.onHistoryEditOver();
                        }
                    }else{
                        if(null != listener){
                            listener.onHistoryEditor();
                        }
                    }
                }
            });

        }else{
            image.setVisibility(View.GONE);
            actionView.setVisibility(View.GONE);
        }
    }
}
