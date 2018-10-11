package com.pbn.org.news.brocast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.pbn.org.news.view.NewsToast;

/**
 * function:
 *
 * @author peiboning
 * @DATE 2018/09/12
 */
public class StaticBrocastReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        NewsToast.showSystemToast(action + ": static test");
    }
}
