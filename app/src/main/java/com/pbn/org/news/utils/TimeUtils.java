package com.pbn.org.news.utils;

import java.util.Calendar;

public class TimeUtils {
    public static int getNowHours(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static String getPlayTimeByInt(int duration) {
        String time = "";
        if(duration<3600){
            int min = duration/60;
            int second = duration%60;
            if(min<10){
                time="0" + min + ":";
            }else{
                time = min+":";
            }
            if(second<10){
                time = time + "0" + second;
            }else{
                time = time + second;
            }
        }
        return time;
    }

}
