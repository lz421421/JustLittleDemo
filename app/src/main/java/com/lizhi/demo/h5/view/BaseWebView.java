package com.lizhi.demo.h5.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * Created by Administrator on 2016/1/4.
 */
public class BaseWebView extends WebView {
    public BaseWebView(Context context) {
        super(context);
    }

    public BaseWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BaseWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

//    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
//    public BaseWebView(Context context, AttributeSet attrs, int defStyleAttr, boolean privateBrowsing) {
//        super(context, attrs, defStyleAttr, privateBrowsing);
//    }
}
