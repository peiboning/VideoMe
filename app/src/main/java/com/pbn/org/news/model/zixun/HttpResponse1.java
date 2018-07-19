package com.pbn.org.news.model.zixun;

import com.pbn.org.news.model.common.Image;
import com.pbn.org.news.model.common.NewsBean;
import com.pbn.org.news.utils.SpUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**

 */
public class HttpResponse1 {
    private static final int TEMPLATE_PICINFO = 1;
    private static final int TEMPLATE_BIGPIC = 2;
    private static final int TEMPLATE_PICGROUP = 3;
    private static final int TEMPLATE_TEXT = 4;
    //概要模版
    private static final int TEMPLATE_SUMMARY = 5;
    // 标签模板
    private static final int TEMPLATE_LABEL = 6;
    //要闻模版
    public static final int TEMPLATE_FOCUSNEWS = 8;
    //视频模版
    public static final int TEMPLATE_VIDEOFEED = 9;

    private int errorCode;
    private String message;
    private String ip;
    private int totalNum;

    private List<Articles> articles;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public List<Articles> getArticles() {
        return articles;
    }

    public void setArticles(List<Articles> articles) {
        this.articles = articles;
    }

    public List<NewsBean> switchToOriginContent(boolean flag) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-DD HH:mm:ss");
        List<NewsBean> list = new ArrayList<NewsBean>();
        long viruTime = 0;
        if(null != articles && articles.size() > 0){
            NewsBean bean = null;
            Articles article = null;
            for(int i = 0;i<articles.size() ;i++){
                article = articles.get(i);
                bean = new NewsBean();
                if(flag){
                    if(i == 0){
                        viruTime = article.getVirtualTime();
                    }
                }else{
                    if(i == articles.size()-1){
                        viruTime = article.getVirtualTime();
                    }
                }
                bean.setId(article.getNewsId());
                bean.setContentSource(NewsBean.CONTENT_SOURCE_1);
                bean.setTemplate(switch2Templete(article.getTemplate()));
                bean.setTitle(article.getTitle());
                List<Pics> pics = article.getPics();
                if(null != pics && pics.size() > 0){
                    List<Image> images = new ArrayList<Image>();
                    for(Pics pic:pics){
                        Image image = new Image(pic.getWidth(), pic.getHeight(), pic.getUrl());
                        images.add(image);
                    }
                    bean.setImages(images);
                }
                bean.setSource(article.getMediaName());
                bean.setUpdateTime(sdf.format(article.getCreateTime()));
                bean.setDetailUrl(article.getArticleUrl());
                bean.setVideos(article.getVideos());
                list.add(bean);
            }

            SpUtils.putLong(RequestBean.LASTVIRTUALTIME, viruTime);
        }
        return list;
    }

    private int switch2Templete(int t){
        int r = 0;

        switch (t){
            case TEMPLATE_PICINFO:
                r = NewsBean.TYPE_ITEM_LIST_NEWS_ONE;
                break;
            case TEMPLATE_BIGPIC:
                r = NewsBean.TYPE_ITEM_LIST_NEWS_BIG;
                break;
            case TEMPLATE_PICGROUP:
                r = NewsBean.TYPE_ITEM_LIST_NEWS_THREE;
                break;
            case TEMPLATE_VIDEOFEED:
                r = NewsBean.TYPE_ITEM_LIST_VIDEO;
                break;
            case TEMPLATE_TEXT:
                r = NewsBean.TYPE_ITEM_TEXT;
                break;
            case TEMPLATE_SUMMARY:
                r = NewsBean.TYPE_ITEM_SUMMARY;
                break;
            case TEMPLATE_LABEL:
                r = NewsBean.TYPE_ITEM_LABEL;
                break;
            case TEMPLATE_FOCUSNEWS:
                r = NewsBean.TYPE_ITEM_FOCUSNEWS;
            default:
                break;
        }
        return r;
    }
}
