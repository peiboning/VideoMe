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
    public static final int REQUEST_PERMISSION_CODE = 100;
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

    public static void request(Activity activity, String permission, PermissionRejectHandler rejectHandler){
        if(checkPermission(permission)){
            return;
        }
        boolean hasRejected = ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
        if(hasRejected){
            if(null != rejectHandler){
                if(rejectHandler.showRationaleToUser(permission)){
                    return;
                }
            }
        }
        ActivityCompat.requestPermissions(activity, new String[]{permission}, REQUEST_PERMISSION_CODE);
    }

}