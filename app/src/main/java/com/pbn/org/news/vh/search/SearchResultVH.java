package com.pbn.org.news.vh.search;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pbn.org.news.R;
import com.pbn.org.news.model.common.Image;
import com.pbn.org.news.model.common.NewsBean;
import com.pbn.org.news.model.haokan.SearchVideo;
import com.pbn.org.news.model.zixun.VideoModel;
import com.pbn.org.news.skin.widget.SkinTextView;
import com.pbn.org.news.status_bar.StatusBarTools;
import com.pbn.org.news.utils.ActivityUtils;
import com.pbn.org.news.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

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

    private int from;

    public SearchResultVH(View itemView, int from) {
        super(itemView);
        imageView = itemView.findViewById(R.id.search_icon);
        title = itemView.findViewById(R.id.search_title);
        author = itemView.findViewById(R.id.author);
        playNums = itemView.findViewById(R.id.play_nums);
        playTime = itemView.findViewById(R.id.play_time);
        this.from = from;
    }

    public void bindView(final SearchVideo data){
        Glide.with(imageView.getContext())
                .load(data.getCover_src())
                .placeholder(R.color.black)
                .into(imageView);
        title.setText(data.getTitle());
        author.setText(data.getAuthor());
        playNums.setText(data.getPlaycntText());
        playTime.setText(TimeUtils.getPlayTimeByInt(data.getDuration()));

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsBean bean = getBeanFromSearchVideo(data);
                ActivityUtils.startVideoDetailActivity(itemView.getContext(), 0, bean, from);
            }
        });
    }

    @NonNull
    private NewsBean getBeanFromSearchVideo(SearchVideo data) {
        NewsBean bean = new NewsBean();
        List<VideoModel> list = new ArrayList<>();
        VideoModel model = new VideoModel();
        model.setDuration(data.getDuration());
        model.setUrl(data.getVideo_src());
        list.add(model);
        bean.setVideos(list);


        List<Image> images = new ArrayList<>();
        Image image = new Image(data.getCover_src());
        images.add(image);
        bean.setImages(images);
        bean.setId(data.getMedia_id());

        return bean;
    }
}
