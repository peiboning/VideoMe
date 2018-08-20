package com.pbn.org.news.setting;

import android.app.AlarmManager;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.pbn.org.news.skin.SkinManager;
import com.pbn.org.news.skin.utils.SkinSp;
import com.pbn.org.news.utils.NewsHandler;
import com.pbn.org.news.utils.SpUtils;
import com.pbn.org.news.utils.ThreadChecker;
import com.pbn.org.news.utils.TimeUtils;
import com.pbn.org.news.view.NewsToast;

public class SmartTheme {
    private static final String SMART_THEME_KEY = "smart_theme_key";
    private static final String SMART_THEME_TIPS_KEY = "smart_theme_tips_key";
    private static final int SMART_THEME_OPEN = 1;
    private static final int SMART_THEME_CLOSE = 0;
    private Context context;

    public SmartTheme(Context context) {
        this.context = context;
    }

    public void startSmartTheme(){
        SpUtils.putInt(SMART_THEME_KEY, SMART_THEME_OPEN);
    }

    public void closeSmartTheme(){
        SpUtils.putInt(SMART_THEME_KEY, SMART_THEME_CLOSE);
    }

    public boolean isSmartTheme(){
        return SpUtils.getInt(SMART_THEME_KEY, SMART_THEME_CLOSE) == SMART_THEME_OPEN;
    }

    public void checkSmartTheme(){
        if(isSmartTheme() && checkTipsInternal()){
            int hour = TimeUtils.getNowHours();
            if(hour >= 19 || hour<=7){
                if(TextUtils.isEmpty(SkinSp.currentThemeName())){
                    if(ThreadChecker.isChildThread()){
                        NewsHandler.postToMainTask(new Runnable() {
                            @Override
                            public void run() {
                                showDialogTip(true);
                            }
                        });
                    }else{
                        showDialogTip(true);
                    }
                }
            }else{
                if(!TextUtils.isEmpty(SkinSp.currentThemeName())){
                    if(ThreadChecker.isChildThread()){
                        NewsHandler.postToMainTask(new Runnable() {
                            @Override
                            public void run() {
                                showDialogTip(false);
                            }
                        });
                    }else{
                        showDialogTip(false);
                    }
                }
            }
        }
    }

    private boolean checkTipsInternal() {
        long lastCheckTime = SpUtils.getLong(SMART_THEME_TIPS_KEY, 0L);
        return System.currentTimeMillis() - lastCheckTime > AlarmManager.INTERVAL_DAY;
    }

    private void showDialogTip(final boolean b) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        String str = "";
        if(b){
            str = "现在是晚上时间，是否切换为夜间模式";
        }else {
            str = "现在是白天时间，是否切换为日间模式";
        }
        builder.setMessage(str);
        builder.setPositiveButton("切换", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(b){
                    changeTheme("skin_night.skin");
                }else{
                    changeTheme("");
                }
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                NewsToast.showSystemToast("后续需要切换，请到设置界面操作");
            }
        });

        builder.setTitle("提示");
        builder.create().show();
        SpUtils.putLong(SMART_THEME_TIPS_KEY, System.currentTimeMillis());
    }


    private void changeTheme(String s) {
        SkinManager.with().startChangeSkin(s);
    }
}
