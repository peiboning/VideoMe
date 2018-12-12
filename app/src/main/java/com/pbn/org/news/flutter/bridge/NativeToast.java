package com.pbn.org.news.flutter.bridge;

import com.pbn.org.news.view.NewsToast;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

/**
 * function:
 *
 * @author peiboning
 * @DATE 2018/12/05
 */
public class NativeToast implements MethodChannel.MethodCallHandler{
    @Override
    public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
        result.success(null);
    }
}
