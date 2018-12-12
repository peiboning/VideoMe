package com.pbn.org.news.flutter;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.pbn.org.news.R;
import com.pbn.org.news.view.ProgressView;

import io.flutter.facade.Flutter;
import io.flutter.plugins.GeneratedPluginRegistrant;
import io.flutter.view.FlutterView;

/**
 * @author peiboning
 */
public class FlutterActivity extends AppCompatActivity {
    private FrameLayout mRootView;
    private LinearLayout mFlutterLayout;
    private LinearLayout mProgressLayout;
    private ProgressView mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flutter);
        mRootView = findViewById(R.id.root);
        mFlutterLayout = findViewById(R.id.flutter_main_layout);
        mProgressLayout = findViewById(R.id.progress_layout);
        mProgress = findViewById(R.id.progress);

        registMethondChannel();

        final long start = SystemClock.currentThreadTimeMillis();
        final FlutterView flutterView = Flutter.createView(this, getLifecycle(), "view1");
        flutterView.addFirstFrameListener(new FlutterView.FirstFrameListener() {
            @Override
            public void onFirstFrame() {
                Log.e("FlutterView", "first Frame show:" + (SystemClock.currentThreadTimeMillis() - start));
                mFlutterLayout.setVisibility(View.VISIBLE);
                flutterView.removeFirstFrameListener(this);

                mProgress.stop();
                mProgressLayout.setVisibility(View.GONE);
            }
        });
        mFlutterLayout.addView(flutterView);
        mProgress.start();

    }

    private void registMethondChannel() {
        GeneratedPluginRegistrant.registerWith();
    }
}
