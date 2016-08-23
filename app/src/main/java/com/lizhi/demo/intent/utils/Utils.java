package com.lizhi.demo.intent.utils;

import android.app.Activity;
import android.widget.TextView;

import com.lizhi.demo.R;

/**
 * Created by Administrator on 2016/1/25.
 */
public class Utils {


    public static void initTextView(Activity activity, String text) {
        TextView tv_title = (TextView) activity.findViewById(R.id.tv_title);
        tv_title.setText(text);
    }
}
