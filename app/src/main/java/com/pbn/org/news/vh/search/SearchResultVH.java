package com.pbn.org.news.vh.search;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pbn.org.news.R;
import com.pbn.org.news.model.haokan.SearchVideo;
import com.pbn.org.news.skin.widget.SkinTextView;
import com.pbn.org.news.utils.TimeUtils;

/**
 * function:
 *
 * @author peiboning
 * @DATE 2018/10/31
 */
public class SearchResultVH extends RecyclerView.ViewHolder {
    private ImageView imageView;
    private TextView title;
    private TextView author;
    private TextView playNums;
    private SkinTextView playTime;

    public SearchResultVH(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.search_icon);
        title = itemView.findViewById(R.id.search_title);
        author = itemView.findViewById(R.id.author);
        playNums = itemView.findViewById(R.id.play_nums);
        playTime = itemView.findViewById(R.id.play_time);
    }

    public void bindView(SearchVideo data){
        Glide.with(imageView.getContext())
                .load(data.getCover_src())
                .placeholder(R.color.black)
                .into(imageView);
        title.setText(data.getTitle());
        author.setText(data.getAuthor());
        playNums.setText(data.getPlaycntText());
        playTime.setText(TimeUtils.getPlayTimeByInt(data.getDuration()));
    }
}
