package com.pbn.org.news.utils;

import java.util.Calendar;

public class TimeUtils {
    public static int getNowHours(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

}
