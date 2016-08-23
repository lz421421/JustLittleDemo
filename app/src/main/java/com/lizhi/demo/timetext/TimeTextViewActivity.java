package com.lizhi.demo.timetext;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.lizhi.demo.R;

/**
 * Created by Administrator on 2016/3/16.
 */
public class TimeTextViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetext);
        final TimeTextView timeTextView = (TimeTextView) findViewById(R.id.tv_time);
        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeTextView.setTimes(100000);
                timeTextView.run();
            }
        });
    }
}
