package com.pbn.org.news.utils.imageloader;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.module.GlideModule;

public class CustomGlideOptions implements GlideModule{
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDiskCache(new CustomGlideDiskCache(context));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
