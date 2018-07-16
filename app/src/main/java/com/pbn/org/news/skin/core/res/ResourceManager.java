package com.pbn.org.news.skin.core.res;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DrawableUtils;
import android.text.TextUtils;

public class ResourceManager {
    private static ResourceManager sInstance = null;

    public static ResourceManager getInstance() {
        if (null == sInstance) {
            synchronized (ResourceManager.class) {
                if (null == sInstance) {
                    sInstance = new ResourceManager();
                }
            }
        }
        return sInstance;
    }

    private ResourceManager() {
    }

    private Context mContext;
    private Resources mSkinResource;
    private Resources mOriginResource;
    private String mSkinPkg;
    private boolean isDefault;

    public void setResource(Context context, Resources skinResource, String pkg){
        mContext = context;
        mOriginResource = context.getResources();
        mSkinResource = skinResource;
        mSkinPkg = pkg;
    }

    public boolean isReady(){
        return mContext != null;
    }

    public Drawable getDrawable(int resId){
        if(isDefault || null == mSkinResource || TextUtils.isEmpty(mSkinPkg)){
            return mOriginResource.getDrawable(resId);
        }
        String name = getResNameById(resId);
        int targetResId = getTargetResIdByName(name, "drawable");
        try{
            return mSkinResource.getDrawable(targetResId);
        }catch (Resources.NotFoundException e){
            return mOriginResource.getDrawable(resId);
        }
    }

    public int getColor(int resId){
        if(isDefault || null == mSkinResource || TextUtils.isEmpty(mSkinPkg)){
            return mOriginResource.getColor(resId);
        }
        String name = getResNameById(resId);
        int targetResId = getTargetResIdByName(name, "color");
        try{
            return mSkinResource.getColor(targetResId);
        }catch (Resources.NotFoundException e){
            return mOriginResource.getColor(resId);
        }
    }

    public int getColorStateList(int tabTextColorId) {
        return 0;
    }

    private int getTargetResIdByName(String name, String type) {
        if(!TextUtils.isEmpty(mSkinPkg) && null != mSkinResource){
            return mSkinResource.getIdentifier(name, type, mSkinPkg);
        }
        return 0;
    }

    private String getResNameById(int resId){
        return mOriginResource.getResourceEntryName(resId);
    }


}