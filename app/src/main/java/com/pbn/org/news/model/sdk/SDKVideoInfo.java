package com.pbn.org.news.model.sdk;

import com.pbn.org.news.model.zixun.VideoModel;
import com.pbn.org.news.model.common.Image;
import com.pbn.org.news.model.common.NewsBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SDKVideoInfo {
    private int statusCode;
    private String statusMsg;
    private SDKVideoList data;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public SDKVideoList getData() {
        return data;
    }

    public void setData(SDKVideoList data) {
        this.data = data;
    }

    public List<NewsBean> toNewsBeans(){
        List<SDKDataModel> videoList = data.getVideoList();
        if(null != videoList && videoList.size() > 0){
            SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
            List<NewsBean> list = new ArrayList<NewsBean>(videoList.size());

            NewsBean bean = null;
            SDKDataModel model = null;
            for(int i = 0;i<videoList.size();i++){
                model = videoList.get(i);
                if(model.getTemplateType() == 12){
                    continue;
                }
                bean = new NewsBean();

                bean.setId(model.getNewsId()+"");
                bean.setTemplate(NewsBean.TYPE_ITEM_LIST_VIDEO);
                bean.setTitle(model.getTitle());
                List<Image> imageList = new ArrayList<Image>(1);
                imageList.add(new Image(model.getTvPic()));
                bean.setImages(imageList);

                bean.setSource(model.getUserInfo().getName());
                bean.setUpdateTime(sdf.format(new Date(model.getTimestamp())));

                List<VideoModel> videos = new ArrayList<VideoModel>(1);
                VideoModel videoModel = new VideoModel();
                videoModel.setDuration(model.getPlayTime());
                videoModel.setUrl(model.getPlayUrl());
                videos.add(videoModel);
                bean.setVideos(videos);


                list.add(bean);
            }

            return list;
        }
        return null;
    }
}
