package com.pbn.org.news.model.haokan;

import android.text.TextUtils;

import com.pbn.org.news.loclib.LocationMgr;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;

public class HKRequestParams {
    public static long sessionId = System.currentTimeMillis();
    public static HashMap<String, String> getQueryMap(){
        HashMap<String, String> map = new HashMap<>();

        map.put("log", "vhk");
        map.put("tn", "1014613h");
        map.put("ctn", "1014613h");
        map.put("stn", "");
        map.put("apiv", "3.9.9.0");
        map.put("appv", "182");
        map.put("version", "3.9.0.10");
        map.put("ut", "MI6_8.0.0_26_Xiaomi");
        map.put("network", "1");
        map.put("os", "android");
        map.put("ua", "1080_1920_480");
        map.put("oabrand", "a0");
        map.put("imei", "0");
        map.put("life", "1531209768");
        map.put("clife", "1531209768");
        map.put("cuid", "200FB58C1A23D2A88E271079BEC41495|341297430144568");
        return map;
    }

    public static HashMap<String, String> getHostWordMap(){
        HashMap<String, String> map1 = new HashMap<>();
        map1.put("hot/words", "method=get");
        return map1;
    }

    private static HashMap<String, Integer> refreshcountMap = new HashMap<>();
    public static HashMap<String, String> getBodyMap(String channel, boolean isLoadMore){
        HashMap<String, String> map1 = new HashMap<>();
        if(TextUtils.isEmpty(channel)){
            channel = "recommend";
        }
        Integer refreshcountObj = refreshcountMap.get(channel);
        if(null == refreshcountObj){
            refreshcountMap.put(channel, 1);
        }
        int refreshcount = refreshcountMap.get(channel);
        refreshcountMap.put(channel, refreshcount + 1);
        int refreshtype = isLoadMore?4:0;
        StringBuffer sb = new StringBuffer("method=get&rn=8&tag="+channel+"&refreshtype="+refreshtype+"&refreshcount=" + refreshcount);
        sb.append("&sessionid=").append(sessionId);
        JSONObject card = new JSONObject();
        JSONObject location = new JSONObject();
        try {
            card.put("refresh_count", refreshcount +1);
            card.put("card_last_showtime", 0);
            card.put("card_show_times", 0);

            location.put("prov", urlEncodeNoException(LocationMgr.getInstance().getProv()));
            location.put("city", urlEncodeNoException(LocationMgr.getInstance().getCity()));
            location.put("county", urlEncodeNoException(LocationMgr.getInstance().getCounty()));
            location.put("city-code", urlEncodeNoException(LocationMgr.getInstance().getCityCode()));
            location.put("street", urlEncodeNoException(LocationMgr.getInstance().getStreet()));
            location.put("latitude", LocationMgr.getInstance().getLatitude());
            location.put("longitude", LocationMgr.getInstance().getLongitude());

            sb.append("&card=").append(card.toString()).append("&").append("location=").append(location.toString())
                    .append("&offline_cursor=0&cb_cursor=0&hot_cursor=0&cursor_time=0")
            .append("&adparam={}");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        map1.put("feed", sb.toString());
        return map1;
    }

    private static String urlEncodeNoException(String src){
        try{
            return URLEncoder.encode(src, "utf-8");
        }catch (Exception e){

        }
        return "";
    }

    public static HashMap<String, String> getSearchBodyMap(String word, int page){
        HashMap<String, String> map1 = new HashMap<>();
        StringBuffer sb = new StringBuffer("method=get&tag=rc&cursor_time=0&cb_cursor=0&hot_cursor=0&offline_cursor=0&rn=10&pn=1");
        sb.append("&title=").append(word)
                .append("&").append("force=0&needBjh=1&long_video=1&wordseg=1&outpn=0")
                .append("&").append("innerpn=").append(page);
        map1.put("search", sb.toString());
        return map1;
    }

    public static HashMap<String, String> getRelateBodyMap(String vid){
        HashMap<String, String> map1 = new HashMap<>();
        StringBuffer sb = new StringBuffer("method=get&url_key=2209353772427868890&log_param_source=bjh");
        sb.append("&").append("vid=").append(vid);
        map1.put("video/detail", sb.toString());
        return map1;
    }

}
