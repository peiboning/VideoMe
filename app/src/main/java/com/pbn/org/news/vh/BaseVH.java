package com.pbn.org.news.vh;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.pbn.org.news.R;
import com.pbn.org.news.detail.DetailActivity;
import com.pbn.org.news.model.common.NewsBean;

import java.util.List;

public abstract class BaseVH extends RecyclerView.ViewHolder {
    public BaseVH(View itemView) {
        super(itemView);
    }

    public void showNews(NewsBean bean){}
    public void showNews(NewsBean bean,int pos){
        showNews(bean);
    }

    protected void loadImage(ImageView view,String url){
        Glide.with(view.getContext())
                .load(url)
                .placeholder(R.color.black)
                .into(view);
    }

    public void bind(final NewsBean bean){
        if(null == bean){
            return;
        }
        if(bean.getTemplate() != NewsBean.TYPE_ITEM_LIST_VIDEO){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DetailActivity.startDetailPage((Activity) itemView.getContext(), bean);
                }
            });
        }

    };

    public void bind(List<NewsBean> datas){}


}
