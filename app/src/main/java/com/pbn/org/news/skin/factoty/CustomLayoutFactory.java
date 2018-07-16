package com.pbn.org.news.skin.factoty;

import android.content.Context;
import android.support.v4.view.LayoutInflaterFactory;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.pbn.org.news.skin.inter.ISkinChangeView;
import com.pbn.org.news.skin.inter.SkinObserver;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class CustomLayoutFactory implements LayoutInflaterFactory, SkinObserver{
    private Context context;
    private String owerName;
    private String owerSimpleName;
    private List<WeakReference<ISkinChangeView>> views = new ArrayList<>();

    public static CustomLayoutFactory instance(Context context){
        return new CustomLayoutFactory(context);
    }
    private CustomLayoutFactory(Context context) {
        this.context = context;
        owerName = context.getClass().getName();
        owerSimpleName = context.getClass().getSimpleName();
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        View view = create(parent, name, context, attrs);
        if(null != view && (view instanceof ISkinChangeView)){
            Log.e("CustomLayoutFactory", "name is : " +name + "\n" + owerSimpleName );
            WeakReference<ISkinChangeView> weakReference = new WeakReference<ISkinChangeView>((ISkinChangeView) view);
            views.add(weakReference);
            return view;
        }
        return null;
    }

    private View create(View parent, String name, Context context, AttributeSet attrs){
        View view = null;
        try {
            if (-1 == name.indexOf('.')){
                if ("View".equals(name)) {
                    view = LayoutInflater.from(context).createView(name, "android.view.", attrs);
                }
                if (view == null) {
                    view = LayoutInflater.from(context).createView(name, "android.widget.", attrs);
                }
                if (view == null) {
                    view = LayoutInflater.from(context).createView(name, "android.webkit.", attrs);
                }
            }else {
                view = LayoutInflater.from(context).createView(name, null, attrs);
            }


        } catch (Exception e) {
            view = null;
        }
        return view;
    }

    @Override
    public void applySkin() {
        for(WeakReference<ISkinChangeView> reference : views){
            if(null != reference.get()){
                reference.get().applySkin();
            }
        }
    }
}
