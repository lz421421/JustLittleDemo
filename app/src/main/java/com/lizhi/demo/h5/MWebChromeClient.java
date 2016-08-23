package com.lizhi.demo.h5;

import android.content.Context;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * Created by zhangdi on 2015/8/11.
 */

public class MWebChromeClient extends WebChromeClient {

    private Context context;

    public MWebChromeClient(Context context) {
        super();
        this.context = context;
    }

    // 处理Alert事件
    @Override
    public boolean onJsAlert(WebView view, String url, String message,
                             JsResult result) {
        return super.onJsAlert(view, url, message, result);
    }

    // onReceivedTitle()方法修改网页标题
    @Override
    public void onReceivedTitle(WebView view, String title) {
        //		((Activity)context).setTitle("可以用onReceivedTitle()方法修改网页标题");
        super.onReceivedTitle(view, title);
    }

    // 处理Confirm事件
    @Override
    public boolean onJsConfirm(WebView view, String url, String message,
                               JsResult result) {
        return super.onJsConfirm(view, url, message, result);
    }

    // 处理提示事件
    @Override
    public boolean onJsPrompt(WebView view, String url, String message,
                              String defaultValue, JsPromptResult result) {
        return super.onJsPrompt(view, url, message, defaultValue, result);
    }
}

