package com.pbn.org.news.model.zixun;

import android.content.Context;
import android.os.Build;

import com.pbn.org.news.NewsApplication;
import com.pbn.org.news.utils.DeviceUtil;
import com.pbn.org.news.utils.NetUtil;
import com.pbn.org.news.utils.SpUtils;

import java.util.List;

public class RequestBean {
    private String ip;
    private int reqtype;
    private String tokenId;
    private String nets;
    private String carrier;
    private String region;
    private int channel;
    private String channelName;
    private long requestTime;
    private int count;
    private String deviceBrand;
    private String deviceType;
    private float dpi;
    private String mac;
    private String osVersion;
    private String resolution;
    private String did;
    private String imei;
    private String imsi;
    private String idfa;
    private String idfv;
    private String openUDID;
    private int imageMode;
    private String version;
    private String refresh;
    private int page;
    private String keyword;
    private String newsid;
    private String ssid;
    private int debug;
    private String lbs;
    private int reqcount;
    private long lastreqtime;

    private int feedstpl;
    private int contenttpl;

    private String session;
    private long lastvirtualtime;
    private List<Integer> taglist;

    public RequestBean() {
        Context context = NewsApplication.getContext();
        imei = DeviceUtil.getInstance().getIMEI();
        imsi = DeviceUtil.getInstance().getImsi();
        openUDID = DeviceUtil.getUUID();
        contenttpl = 215;
        count = 20;
        deviceBrand = Build.BRAND;
        debug = 0;
        deviceType = Build.MODEL;
        did = DeviceUtil.getInstance().getDid();
        feedstpl = 125;

        lastreqtime = SpUtils.getLong("lastreqtime", 0);
        SpUtils.putLong("lastreqtime", requestTime);
        lbs = "";
        nets = NetUtil.getNetworkType(context);
        carrier = DeviceUtil.getInstance().getSimOperatorName();

        session = NewsApplication.session;

    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getReqtype() {
        return reqtype;
    }

    public void setReqtype(int reqtype) {
        this.reqtype = reqtype;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getNets() {
        return nets;
    }

    public void setNets(String nets) {
        this.nets = nets;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public long getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(long requestTime) {
        this.requestTime = requestTime;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getDeviceBrand() {
        return deviceBrand;
    }

    public void setDeviceBrand(String deviceBrand) {
        this.deviceBrand = deviceBrand;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public float getDpi() {
        return dpi;
    }

    public void setDpi(float dpi) {
        this.dpi = dpi;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getIdfa() {
        return idfa;
    }

    public void setIdfa(String idfa) {
        this.idfa = idfa;
    }

    public String getIdfv() {
        return idfv;
    }

    public void setIdfv(String idfv) {
        this.idfv = idfv;
    }

    public String getOpenUDID() {
        return openUDID;
    }

    public void setOpenUDID(String openUDID) {
        this.openUDID = openUDID;
    }

    public int getImageMode() {
        return imageMode;
    }

    public void setImageMode(int imageMode) {
        this.imageMode = imageMode;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRefresh() {
        return refresh;
    }

    public void setRefresh(String refresh) {
        this.refresh = refresh;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getNewsid() {
        return newsid;
    }

    public void setNewsid(String newsid) {
        this.newsid = newsid;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public int getDebug() {
        return debug;
    }

    public void setDebug(int debug) {
        this.debug = debug;
    }

    public String getLbs() {
        return lbs;
    }

    public void setLbs(String lbs) {
        this.lbs = lbs;
    }

    public int getReqcount() {
        return reqcount;
    }

    public void setReqcount(int reqcount) {
        this.reqcount = reqcount;
    }

    public long getLastreqtime() {
        return lastreqtime;
    }

    public void setLastreqtime(long lastreqtime) {
        this.lastreqtime = lastreqtime;
    }

    public int getFeedstpl() {
        return feedstpl;
    }

    public void setFeedstpl(int feedstpl) {
        this.feedstpl = feedstpl;
    }

    public int getContenttpl() {
        return contenttpl;
    }

    public void setContenttpl(int contenttpl) {
        this.contenttpl = contenttpl;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public long getLastvirtualtime() {
        return lastvirtualtime;
    }

    public void setLastvirtualtime(long lastvirtualtime) {
        this.lastvirtualtime = lastvirtualtime;
    }

    public List<Integer> getTaglist() {
        return taglist;
    }

    public void setTaglist(List<Integer> taglist) {
        this.taglist = taglist;
    }
}
