package com.pbn.org.news.model.quyue;

import com.pbn.org.news.model.common.Image;
import com.pbn.org.news.model.common.NewsBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class QueyueNewsBean implements Serializable {
    public final static int TYPE_ITEM_LIST_NEWS_ONE     = 1;
    public final static int TYPE_ITEM_LIST_NEWS_BIG     = 2;
    public final static int TYPE_ITEM_LIST_NEWS_THREE   = 3;
    public final static int TYPE_ITEM_LIST_NEWS_REFRESH = 4;
    public final static int TYPE_ITEM_LIST_VIDEO        = 5;
    public final static int TYPE_ITEM_LIST_AD           = 6;
    public final static int TYPE_ITEM_LIST_GROUP           = 7;


    private String ad_title;
    private String app_package;
    private int app_size;
    private List<String> arrDownloadTrackUrl;
    private List<String> arrDownloadedTrakUrl;
    private List<String> arrIntalledTrackUrl;
    private int author_id;
    private String brand_name;
    private int cat_id;
    private String cat_name;
    private String click_url;
    private int comment_counts;
    private int creative_type;
    private String description;
    private String detail_url;
    private int error_code;
    private String id;
    private List<String> images;
    private int info_type;
    private int interaction_type;
    private int is_top;
    private int material_height;
    private int material_width;
    private List<String> other_click_url;
    private List<String> other_notice_url;
    private int recommend;
    private String source;
    private String strLinkUrl;
    private String title;
    private String type;
    private String update_time;
    private String win_notice_url;

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public int getIs_top() {
        return this.is_top;
    }

    public void setIs_top(int i) {
        this.is_top = i;
    }

    public int getRecommend() {
        return this.recommend;
    }

    public void setRecommend(int i) {
        this.recommend = i;
    }

    public String getDetail_url() {
        return this.detail_url;
    }

    public void setDetail_url(String str) {
        this.detail_url = str;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String str) {
        this.source = str;
    }

    public String getUpdate_time() {
        return this.update_time;
    }

    public void setUpdate_time(String str) {
        this.update_time = str;
    }

    public int getCat_id() {
        return this.cat_id;
    }

    public void setCat_id(int i) {
        this.cat_id = i;
    }

    public String getCat_name() {
        return this.cat_name;
    }

    public void setCat_name(String str) {
        this.cat_name = str;
    }

    public int getAuthor_id() {
        return this.author_id;
    }

    public void setAuthor_id(int i) {
        this.author_id = i;
    }

    public int getComment_counts() {
        return this.comment_counts;
    }

    public void setComment_counts(int i) {
        this.comment_counts = i;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getAd_title() {
        return this.ad_title;
    }

    public void setAd_title(String str) {
        this.ad_title = str;
    }

    public String getBrand_name() {
        return this.brand_name;
    }

    public void setBrand_name(String str) {
        this.brand_name = str;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public int getMaterial_width() {
        return this.material_width;
    }

    public void setMaterial_width(int i) {
        this.material_width = i;
    }

    public int getMaterial_height() {
        return this.material_height;
    }

    public void setMaterial_height(int i) {
        this.material_height = i;
    }

    public int getCreative_type() {
        return this.creative_type;
    }

    public void setCreative_type(int i) {
        this.creative_type = i;
    }

    public int getInteraction_type() {
        return this.interaction_type;
    }

    public void setInteraction_type(int i) {
        this.interaction_type = i;
    }

    public String getApp_package() {
        return this.app_package;
    }

    public void setApp_package(String str) {
        this.app_package = str;
    }

    public int getApp_size() {
        return this.app_size;
    }

    public void setApp_size(int i) {
        this.app_size = i;
    }

    public String getClick_url() {
        return this.click_url;
    }

    public void setClick_url(String str) {
        this.click_url = str;
    }

    public String getWin_notice_url() {
        return this.win_notice_url;
    }

    public void setWin_notice_url(String str) {
        this.win_notice_url = str;
    }

    public String getStrLinkUrl() {
        return this.strLinkUrl;
    }

    public void setStrLinkUrl(String str) {
        this.strLinkUrl = str;
    }

    public int getInfo_type() {
        return this.info_type;
    }

    public void setInfo_type(int i) {
        this.info_type = i;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }

    public int getError_code() {
        return this.error_code;
    }

    public void setError_code(int i) {
        this.error_code = i;
    }

    public List<String> getArrDownloadTrackUrl() {
        return this.arrDownloadTrackUrl;
    }

    public void setArrDownloadTrackUrl(List<String> list) {
        this.arrDownloadTrackUrl = list;
    }

    public List<String> getArrDownloadedTrakUrl() {
        return this.arrDownloadedTrakUrl;
    }

    public void setArrDownloadedTrakUrl(List<String> list) {
        this.arrDownloadedTrakUrl = list;
    }

    public List<String> getArrIntalledTrackUrl() {
        return this.arrIntalledTrackUrl;
    }

    public void setArrIntalledTrackUrl(List<String> list) {
        this.arrIntalledTrackUrl = list;
    }

    public List<String> getOther_notice_url() {
        return this.other_notice_url;
    }

    public void setOther_notice_url(List<String> list) {
        this.other_notice_url = list;
    }

    public List<String> getOther_click_url() {
        return this.other_click_url;
    }

    public void setOther_click_url(List<String> list) {
        this.other_click_url = list;
    }

    public List<String> getImages() {
        return this.images;
    }

    public void setImages(List<String> list) {
        this.images = list;
    }

    public int getItemType() {
        if (getType().equals("video")) {
            return 5;
        }
        if (getType().equals("refresh")) {
            return 4;
        }
        if (getInfo_type() == 3) {
            return 3;
        }
        if (getType().equals("ad")) {
            return 2;
        }
        return 1;
    }

    public NewsBean switchToOriginContent() {
        NewsBean bean = new NewsBean();
        bean.setContentSource(NewsBean.CONTENT_SOURCE_2);

        bean.setId(getId());
        bean.setTemplate(getItemType());
        bean.setTitle(getTitle());
        List<String> imageUrls = getImages();
        if(null != imageUrls && imageUrls.size() > 0){
            List<Image> images = new ArrayList<Image>();
            for (String imageUrl : imageUrls){
                Image image = new Image(imageUrl);
                images.add(image);
            }
            bean.setImages(images);
        }

        bean.setSource(getSource());
        bean.setUpdateTime(getUpdate_time());
        bean.setDetailUrl(getDetail_url());

        return bean;
    }

}
