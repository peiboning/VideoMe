package com.pbn.org.news.vh;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pbn.org.news.R;
import com.pbn.org.news.model.common.NewsBean;

public class NewsThreeIconVH extends BaseVH {
    private TextView mTitle;
    private TextView mNewsSource;
    private TextView mNewsComment;
    private ImageView[] mImages;
    public NewsThreeIconVH(View itemView) {
        super(itemView);

        mTitle = itemView.findViewById(R.id.news_title);
        mImages = new ImageView[3];
        mImages[0] = itemView.findViewById(R.id.icon_one);
        mImages[1] = itemView.findViewById(R.id.icon_two);
        mImages[2] = itemView.findViewById(R.id.icon_three);
        mNewsSource = itemView.findViewById(R.id.news_source);
        mNewsComment = itemView.findViewById(R.id.news_comment);
    }

    @Override
    public void showNews(NewsBean bean) {
        mTitle.setText(bean.getTitle());
        if(TextUtils.isEmpty(bean.getUpdateTime())){
            mNewsComment.setText(R.string.just_now_update);
        }else{
            mNewsComment.setText(bean.getUpdateTime());
        }
        if(null != bean.getImages() && bean.getImages().size()>0){
            int length = Math.min(bean.getImages().size(), mImages.length);
            for(int i = 0;i<length;i++){
                Log.e("showNews",bean.getImages().get(i).getUrl() );
                final int finalI = i;
                mImages[i].setImageResource(R.mipmap.ic_launcher);
                Glide.with(mTitle.getContext())
                        .load(bean.getImages().get(i).getUrl())
//                        .load(R.mipmap.ic_launcher)
//                        .placeholder(R.mipmap.app_icon)
                        .into(mImages[i]);
            }
        }

    }
}
