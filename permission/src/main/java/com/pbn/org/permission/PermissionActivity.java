package com.pbn.org.permission;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

public class PermissionActivity extends FragmentActivity {
    private static final String REQUEST_PERMISSION = "request_permission";
    private static final int REQUEST_PERMISSION_CODE = 100;
    public static void requestPermission(Activity activity, String permission){
        Intent intent = new Intent();
        intent.putExtra(REQUEST_PERMISSION, permission);
        intent.setClass(activity, PermissionActivity.class);
        activity.startActivity(intent);
    }

    private String permission;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        permission = getIntent().getStringExtra(REQUEST_PERMISSION);
        if(TextUtils.isEmpty(permission)){
            finish();
        }else{
            ActivityCompat.requestPermissions(this, new String[]{permission}, REQUEST_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(REQUEST_PERMISSION_CODE == requestCode){
            PermissionClient.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        finish();
    }
}
