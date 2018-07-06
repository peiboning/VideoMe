package com.pbn.org.news.model.xigua;

import android.text.TextUtils;
import android.util.Base64;

import com.google.gson.JsonObject;
import com.pbn.org.news.model.common.Image;
import com.pbn.org.news.model.common.NewsBean;
import com.pbn.org.news.model.zixun.VideoModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class XiguaModel {
    private int total_number;
    private List<Data> data;
    private String message;

    public int getTotal_number() {
        return total_number;
    }

    public void setTotal_number(int total_number) {
        this.total_number = total_number;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private class Data{
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public List<NewsBean> toNewsBeans(){
        if(null != data && data.size() > 0){
            List<NewsBean> news = new ArrayList<>(data.size());
            for(Data d : data){
                try {
                    JSONObject object = new JSONObject(d.content);
                    String tip = object.optString("abstract", "");
                    if(TextUtils.isEmpty(tip)){
                        continue;
                    }
                    NewsBean bean = new NewsBean();
                    bean.setTemplate(NewsBean.TYPE_ITEM_LIST_VIDEO);
                    String title = object.optString("title");
                    bean.setTitle(title);

                    List<Image> imageList = new ArrayList<Image>(1);
                    JSONObject middelImage = object.getJSONObject("middle_image");
                    imageList.add(new Image(middelImage.optString("url")));
                    bean.setImages(imageList);

                    bean.setSource(object.optString("source"));

                    List<VideoModel> videos = new ArrayList<VideoModel>(1);

                    JSONObject video_play_info = object.optJSONObject("video_play_info");

                    if(video_play_info == null){
                        continue;
                    }
                    VideoModel videoModel = new VideoModel();
                    videoModel.setDuration(video_play_info.optInt("video_duration"));

                    JSONObject video_list = video_play_info.getJSONObject("video_list");
                    JSONObject video_1 = video_list.getJSONObject("video_1");
                    String main_url = video_1.getString("main_url");

                    videoModel.setUrl(new String(Base64.decode(main_url, Base64.DEFAULT)));
                    videos.add(videoModel);
                    bean.setVideos(videos);

                    news.add(bean);
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }
        }
        return null;
    }
}
