package com.pbn.org.news.model;

import com.pbn.org.news.model.zixun.RequestBean;

public class ChannelRequestBean extends RequestBean {
    private int channelid;
    private String channelname;

    private int apptype = 1;

    public int getChannelid() {
        return channelid;
    }

    public void setChannelid(int channelid) {
        this.channelid = channelid;
    }

    public String getChannelname() {
        return channelname;
    }

    public void setChannelname(String channelname) {
        this.channelname = channelname;
    }

    public int getApptype() {
        return apptype;
    }

    public void setApptype(int apptype) {
        this.apptype = apptype;
    }
}
