package com.pbn.org.permission;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.lang.ref.WeakReference;

public class PermissionClient {
    private static PermissionClient sInstance = null;
    private static Context sContext;
    private static WeakReference<OnRequestPermssionListener> onRequestPermssionListener;

    public static PermissionClient getInstance() {
        if (null == sInstance) {
            synchronized (PermissionClient.class) {
                if (null == sInstance) {
                    sInstance = new PermissionClient();
                }
            }
        }
        return sInstance;
    }

    private PermissionClient() {
    }

    public static void init(Context context){
        if(context instanceof Activity){
            sContext = context.getApplicationContext();
        }else{
            sContext = context;
        }

    }

    public static boolean checkPermission(String permission){
        boolean flag ;
        int res = ContextCompat.checkSelfPermission(sContext, permission);
        flag = res == PackageManager.PERMISSION_GRANTED;
        return flag;
    }

    public static void request(Activity activity, String permission, OnRequestPermssionListener listener, PermissionRejectHandler rejectHandler){
        if(checkPermission(permission)){
            return;
        }
        onRequestPermssionListener = new WeakReference<OnRequestPermssionListener>(listener);
        boolean hasRejected = ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
        if(hasRejected){
            if(null != rejectHandler){
                if(rejectHandler.showRationaleToUser(permission)){
                    return;
                }
            }
        }

        requestPermission(activity, permission, rejectHandler);
    }

    private static void requestPermission(Context context, String permission, PermissionRejectHandler rejectHandler) {
        PermissionActivity.requestPermission((Activity) context, permission);
    }

    public static void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(permissions.length <=0){
            Toast.makeText(sContext, "onRequestPermissionsResult error", Toast.LENGTH_LONG).show();
            return;
        }
        if(checkPermission(permissions[0])){
            if(null != onRequestPermssionListener.get()){
                onRequestPermssionListener.get().onGrantPermission(permissions[0]);
            }
            Toast.makeText(sContext, "onRequestPermissionsResult success", Toast.LENGTH_LONG).show();
        }else{
            if(null != onRequestPermssionListener.get()){
                onRequestPermssionListener.get().onRejectPermission(permissions[0]);
            }
            Toast.makeText(sContext, "onRequestPermissionsResult failed", Toast.LENGTH_LONG).show();
        }

    }
}