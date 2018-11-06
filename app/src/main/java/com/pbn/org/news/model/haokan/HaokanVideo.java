package com.pbn.org.news.model.haokan;

import com.pbn.org.news.model.common.Image;
import com.pbn.org.news.model.common.NewsBean;
import com.pbn.org.news.model.zixun.VideoModel;

import java.util.ArrayList;
import java.util.List;

public class HaokanVideo {
    private String logid;
    private Feed feed;

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    public String getLogid() {
        return logid;
    }

    public void setLogid(String logid) {
        this.logid = logid;
    }

    public List<NewsBean> toNewBean(){
        if(null != feed && null != feed.data && null != feed.data.list && feed.data.list.size()>0){
            List<videoData> list = feed.data.list;
            List<NewsBean> beans = new ArrayList<NewsBean>(list.size());
            NewsBean bean;
            for(videoData data : list){
                if(!data.getType().equals("video")){
                    continue;
                }
                bean = new NewsBean();
                bean.setId(data.content.vid);
                bean.setTitle(data.content.title);
                bean.setTemplate(NewsBean.TYPE_ITEM_LIST_VIDEO);
                List<Image> imageList = new ArrayList<Image>(1);
                imageList.add(new Image(data.content.cover_src));
                bean.setImages(imageList);

                bean.setSource(data.content.author);

                List<VideoModel> videos = new ArrayList<VideoModel>(1);
                VideoModel videoModel = new VideoModel();
                videoModel.setDuration(data.content.duration);
                videoModel.setUrl(data.content.video_src);
                videos.add(videoModel);
                bean.setVideos(videos);
                bean.setContentSource(NewsBean.CONTENT_SOURCE_Haokan);
                beans.add(bean);
            }
            return beans;
        }
        return null;
    }

    public class Feed{
        private Data data;

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }
    }

    public class Data{
        private List<videoData> list;

        public List<videoData> getData() {
            return list;
        }

        public void setData(List<videoData> data) {
            this.list = data;
        }
    }
    public class videoData{
        private Content content;
        private String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Content getContent() {
            return content;
        }

        public void setContent(Content content) {
            this.content = content;
        }
    }

    public class Content{
        private String id;
        private String title;
        private String video_src;
        private String author;
        private int duration;
        private String itemType;
        private String cover_src;
        private String vid;

        public String getCover_src() {
            return cover_src;
        }

        public void setCover_src(String cover_src) {
            this.cover_src = cover_src;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getVideo_src() {
            return video_src;
        }

        public void setVideo_src(String video_src) {
            this.video_src = video_src;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public String getItemType() {
            return itemType;
        }

        public void setItemType(String itemType) {
            this.itemType = itemType;
        }

        public String getVid() {
            return vid;
        }

        public void setVid(String vid) {
            this.vid = vid;
        }
    }
}
