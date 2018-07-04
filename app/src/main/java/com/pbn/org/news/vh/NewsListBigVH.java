package com.pbn.org.news.vh;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pbn.org.news.R;
import com.pbn.org.news.model.common.NewsBean;

public class NewsListBigVH extends BaseVH {

    private ImageView mBigView;
    private TextView mTitle;
    public NewsListBigVH(View itemView) {
        super(itemView);
        mBigView = itemView.findViewById(R.id.icon_big);
        mTitle = itemView.findViewById(R.id.news_title);
    }

    @Override
    public void showNews(NewsBean bean){
        mTitle.setText(bean.getTitle());
        if(null != bean.getImages() && bean.getImages().size()>0){
            loadImage(mBigView, bean.getImages().get(0).getUrl());
        }

    }
}
