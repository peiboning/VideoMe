package com.pbn.org.news.utils.imageloader;

import android.content.Context;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.pbn.org.news.cache.CacheManager;

public class CustomGlideDiskCache extends DiskLruCacheFactory {
    private static final String CACHE_NAME = "_glide_cache";

    public CustomGlideDiskCache(Context context) {
        this(context, CACHE_NAME);
    }

    public CustomGlideDiskCache(final Context context, final String diskCacheName) {
        super(CacheManager.getInstance().getCacheDirGetter(diskCacheName), CacheManager.getInstance().getImageCacheSize());
    }


}
