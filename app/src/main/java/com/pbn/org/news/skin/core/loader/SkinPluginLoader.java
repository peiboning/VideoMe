package com.pbn.org.news.skin.core.loader;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SkinPluginLoader {
    public static String getPkg(Context context, String pluginPath){
        PackageManager manager = context.getPackageManager();
        PackageInfo info = manager.getPackageArchiveInfo(pluginPath, PackageManager.GET_ACTIVITIES);
        return info.packageName;
    }

    public static Resources getPluginResource(Context context, String plugin){
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssert = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
            addAssert.setAccessible(true);
            addAssert.invoke(assetManager, plugin);

            Resources originRes = context.getResources();
            Resources skinRes = new Resources(assetManager, originRes.getDisplayMetrics(), originRes.getConfiguration());
            return skinRes;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
