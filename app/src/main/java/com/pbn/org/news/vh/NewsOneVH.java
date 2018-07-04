package com.pbn.org.news.vh;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pbn.org.news.R;
import com.pbn.org.news.model.common.NewsBean;

public class NewsOneVH extends BaseVH {
    private TextView mTitle;
    private ImageView mIconRight;
    private TextView mNewsSource;
    private TextView mNewsComment;
    public NewsOneVH(View itemView) {
        super(itemView);
        mTitle = itemView.findViewById(R.id.news_title);
        mIconRight = itemView.findViewById(R.id.icon_right);
        mNewsSource = itemView.findViewById(R.id.news_source);
        mNewsComment = itemView.findViewById(R.id.news_comment);
    }

    @Override
    public void showNews(NewsBean bean) {
        mTitle.setText(bean.getTitle());
        if(null != bean.getImages() && bean.getImages().size()>0){
            loadImage(mIconRight, bean.getImages().get(0).getUrl());
        }

        if(TextUtils.isEmpty(bean.getUpdateTime())){
            mNewsComment.setText(R.string.just_now_update);
        }else{
            mNewsComment.setText(bean.getUpdateTime());
        }
    }
}
