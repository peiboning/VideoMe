package com.pbn.org.permission;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

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

    public static List<String> checkPermission(String[] permissions){
        List<String> list = new ArrayList<>();
        for(String permission : permissions){
            int res = ContextCompat.checkSelfPermission(sContext, permission);
            if(res != PackageManager.PERMISSION_GRANTED){
                list.add(permission);
            }
        }
        return list;
    }

    public static void request(Activity activity, String[] permission, PermissionRejectHandler rejectHandler){
        ActivityCompat.requestPermissions(activity, permission, REQUEST_PERMISSION_CODE);
    }

}