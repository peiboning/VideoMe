package com.pbn.org.news.skin.factoty;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.view.LayoutInflaterCompat;
import android.util.Log;
import android.view.LayoutInflater;

import com.pbn.org.news.R;
import com.pbn.org.news.skin.core.res.ResourceManager;
import com.pbn.org.news.skin.inter.ISkinChange;
import com.pbn.org.news.skin.utils.SkinUtils;

import java.lang.reflect.Field;
import java.util.WeakHashMap;

public class SkinLayoutFactoryManager {
    private static SkinLayoutFactoryManager sInstance = null;

    public static SkinLayoutFactoryManager getInstance() {
        if (null == sInstance) {
            synchronized (SkinLayoutFactoryManager.class) {
                if (null == sInstance) {
                    sInstance = new SkinLayoutFactoryManager();
                }
            }
        }
        return sInstance;
    }

    private SkinLayoutFactoryManager() {
    }

    private WeakHashMap<Context, CustomLayoutFactory> factoryMap = new WeakHashMap<Context, CustomLayoutFactory>();

    public void installFactory(Context context){
        if(SkinUtils.isCanChangeSkin(context)){
            LayoutInflater inflater = LayoutInflater.from(context);
            Log.e("installFactory", "inflater:" + inflater.hashCode() + ",baseContext:" + context);
            try {
                Field field = LayoutInflater.class.getDeclaredField("mFactorySet");
                field.setAccessible(true);
                field.set(inflater, false);
                LayoutInflaterCompat.setFactory(inflater, getLayoutFactory(context));
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public void unInstallFactory(Context context){
        factoryMap.remove(context);
    }

    private CustomLayoutFactory getLayoutFactory(Context context){
        CustomLayoutFactory layoutFactory = factoryMap.get(context);
        if(null == layoutFactory){
            layoutFactory = CustomLayoutFactory.instance(context);
            factoryMap.put(context, layoutFactory);
        }
        return layoutFactory;
    }

    public void apply(Activity activity){
        //first activity window
        if(activity instanceof ISkinChange){
            int color = ResourceManager.getInstance().getColor(R.color.skin_status_bar);
            if(Build.VERSION.SDK_INT>=21){
                activity.getWindow().setStatusBarColor(color);
            }else{
                activity.getWindow().setBackgroundDrawable(new ColorDrawable(color));
            }
        }
        //second
        //last update views
        CustomLayoutFactory layoutFactory = factoryMap.get(activity);
        if(null != layoutFactory){
            layoutFactory.applySkin();
        }
    }
}