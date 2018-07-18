package com.pbn.org.news.utils;

import java.io.File;

public class FileUtils {
    public static long caculteFileSize(File[] file){
        long size = 0;
        if(null != file && file.length>0){
            for (File temp : file){
                if(temp.isDirectory()){
                    caculteFileSize(temp.listFiles());
                }else {
                    size += temp.length();
                }
            }
        }
        return size;
    }
}
