package com.pbn.org.news.cache;

import android.Manifest;
import android.os.Environment;
import android.text.TextUtils;

import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.pbn.org.news.NewsApplication;
import com.pbn.org.news.utils.FileUtils;
import com.pbn.org.news.utils.ThreadChecker;
import com.pbn.org.permission.PermissionClient;

import java.io.File;
import java.text.DecimalFormat;

public class CacheManager {

    private static final int DEFAULT_DISK_CACHE_SIZE = 50 * 1024 * 1024;
    private static final int DEFAULT_INTERNAL_CACHE_SIZE = 10 * 1024 * 1024;

    private static CacheManager sInstance = null;
    private String imageCachePath;
    private int cacheSize = DEFAULT_DISK_CACHE_SIZE;
    public static CacheManager getInstance() {
        if (null == sInstance) {
            synchronized (CacheManager.class) {
                if (null == sInstance) {
                    sInstance = new CacheManager();
                }
            }
        }
        return sInstance;
    }

    private CacheManager() {
        initCacheDir();
    }

    private void initCacheDir() {
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            File externalCacheDir = NewsApplication.getContext().getExternalCacheDir();
            //fix bug
            if(null != externalCacheDir){
                imageCachePath = externalCacheDir.getPath() + File.separator + "image_cache";
                File file = new File(imageCachePath);
                if(!file.exists()){
                    file.mkdirs();
                }
                cacheSize = DEFAULT_DISK_CACHE_SIZE;
            }else{
                initInternalStorage();
            }
        }else{
            initInternalStorage();
        }
    }

    public File getExternalCacheDir(){
        File externalCacheDir = NewsApplication.getContext().getExternalCacheDir();
        if(null != externalCacheDir){
            return externalCacheDir;
        }
        return NewsApplication.getContext().getCacheDir();
    }

    private void initInternalStorage() {
        imageCachePath = NewsApplication.getContext().getCacheDir().getPath() + File.separator + "image_cache";
        File file = new File(imageCachePath);
        if(!file.exists()){
            file.mkdirs();
        }
        cacheSize = DEFAULT_INTERNAL_CACHE_SIZE;
    }

    public String getImageCacheDir(){
        return imageCachePath;
    }

    public int getImageCacheSize(){
        return cacheSize;
    }

    public  DiskLruCacheFactory.CacheDirectoryGetter getCacheDirGetter(final String diskCacheName){
        return new DiskLruCacheFactory.CacheDirectoryGetter() {
            @Override
            public File getCacheDirectory() {
                File cacheDirectory = new File(getImageCacheDir());
                if(!cacheDirectory.exists() || !cacheDirectory.isDirectory()){
                    cacheDirectory.mkdirs();
                }
                return cacheDirectory;

            }
        };
    }

    public String getCacheSize() {
        ThreadChecker.assertChildThread();
        File file = new File(getImageCacheDir());
        long size = FileUtils.caculteFileSize(file.listFiles());
        return formatSize(size);
    }

    private String formatSize(long size){
        DecimalFormat formater = new DecimalFormat("####.00");
        if (size < 1024) {
            return size + " bytes";
        } else if (size < 1024 * 1024) {
            float kbsize = size / 1024f;
            return formater.format(kbsize) + " KB";
        } else if (size < 1024 * 1024 * 1024) {
            float mbsize = size / 1024f / 1024f;
            return formater.format(mbsize) + " MB";
        } else if (size < 1024 * 1024 * 1024 * 1024) {
            float gbsize = size / 1024f / 1024f / 1024f;
            return formater.format(gbsize) + " GB";
        }
        return 0+" byte";
    }
}