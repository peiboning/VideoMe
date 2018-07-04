package com.pbn.org.news.vh;

import android.view.View;

import com.pbn.org.news.model.common.NewsBean;
import com.pbn.org.news.view.RefresRecyleView;

public class RefreshVH extends BaseVH {
    private RefresRecyleView parentView;
    public RefreshVH(View itemView) {
        super(itemView);

    }

    public void setParentView(RefresRecyleView parentView){
        this.parentView = parentView;
    }

    @Override
    public void showNews(NewsBean bean) {

    }

    @Override
    public void bind(NewsBean bean) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != parentView){
                    parentView.startRefresh();
                }
            }
        });
    }
}
