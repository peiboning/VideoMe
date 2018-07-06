package com.pbn.org.news.model.xigua;

import java.util.HashMap;

public class QueryMap {
    public static HashMap<String, String> getQueryMap(){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("category","video_new");
        map.put("refer","1");
        map.put("category","video_new");
        map.put("count","20");
        map.put("list_entrance","main_tab");
        map.put("min_behot_time",System.currentTimeMillis()/1000 + "");
        map.put("loc_mode","7");
        map.put("tt_from","load_more");
        map.put("play_param","codec_type:1");
        map.put("ac","wifi");
        map.put("channel","xiaomi");
        map.put("app_name","video_article");
        map.put("device_platform","android");
        map.put("device_type","MI_6");
        map.put("device_brand","Xiaomi");
        map.put("language","zh");
        map.put("os_api","26");
        map.put("os_version","8.0.0");
        map.put("resolution","1080*1920");
        map.put("dpi","480");
        map.put("_rticket",System.currentTimeMillis()+"");
        map.put("rom_version","miui_V9_8.5.8");
        map.put("fp","2rT_FlmbFrPMFlTMJ2U1FYGeFzKI");
        map.put("iid","35203550419");
        map.put("device_id","50828234331");

        return map;
    }
}
