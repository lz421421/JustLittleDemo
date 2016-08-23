package com.lizhi.demo.h5.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


import com.lizhi.demo.R;
import com.lizhi.demo.h5.BaseH5Activity;
import com.lizhi.demo.h5.bean.WebBean;
import com.lizhi.demo.h5.view.BaseWebView;
import com.lizhi.demo.utils.LogUtil;
import com.lizhi.demo.view.ProgressDialog_GrayBG;

import java.io.File;
import java.io.Serializable;

/**
 * 普通的 webview  只需要传WebBean过来 通过调用
 */
public class NormalH5Activity extends /*Activity*/BaseH5Activity {



    private BaseWebView mWebView;

    /*********************/
    WebBean webBean;


    @Override
    public int setContentViewID() {
        return R.layout.activity_webview;
    }

    @Override
    public void findViewById() {
        webBean = new WebBean("", "http://www.ishadowsocks.net/");
        mBaseWebView = (BaseWebView) findViewById(R.id.wv_webView);
    }

    @Override
    public void initView() {
        if (TextUtils.isEmpty(webBean.url)) {
            mBaseWebView.loadUrl("file:///android_asset/pager.html");
        } else {
//            checkWebViewUrl(mBaseWebView,webBean.url);
            load(mBaseWebView, webBean.url);
        }
    }

}
