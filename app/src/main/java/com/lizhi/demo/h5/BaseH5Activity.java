package com.lizhi.demo.h5;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


import com.lizhi.demo.BaseActivity;
import com.lizhi.demo.h5.bean.JavaScriptObject;
import com.lizhi.demo.h5.view.BaseWebView;
import com.lizhi.demo.utils.LogUtil;
import com.lizhi.demo.view.ProgressDialog_GrayBG;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/1/4.
 */
public abstract class BaseH5Activity extends BaseActivity {

    public BaseWebView mBaseWebView;

    public abstract void findViewById();

    public abstract void initView();

    ProgressDialog_GrayBG dialog_grayBG;
    private static final String APP_CACAHE_DIRNAME = "/webcache";


    @Override
    public void setContentView() {
        if (setContentViewID() != 0) {
            setContentView(setContentViewID());
        }
        dialog_grayBG = new ProgressDialog_GrayBG(this);
        findViewById();
        if (mBaseWebView != null) {
            WebSettings setting = mBaseWebView.getSettings();
            //设置编码
            setting.setDefaultTextEncodingName("utf-8");
            //支持js
            setting.setJavaScriptEnabled(true);
            //设置背景颜色 透明
            mBaseWebView.setBackgroundColor(Color.argb(0, 0, 0, 0));
            //设置本地调用对象及其接口
            setting.setJavaScriptEnabled(true);//支持js
            mBaseWebView.setWebViewClient(new MyWebViewClient());
            mBaseWebView.setWebChromeClient(new MWebChromeClient(this));
            mBaseWebView.addJavascriptInterface(new JavaScriptObject(this), "AndroidWeb");
            //屏蔽长按
            mBaseWebView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return true;
                }
            });


//            setting.setRenderPriority(WebSettings.RenderPriority.HIGH);
            setting.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);  //设置 缓存模式
            // 开启 DOM storage API 功能
            setting.setDomStorageEnabled(true);
            //开启 database storage API 功能
            setting.setDatabaseEnabled(true);
            String cacheDirPath = getFilesDir().getAbsolutePath()+APP_CACAHE_DIRNAME;
//      String cacheDirPath = getCacheDir().getAbsolutePath()+Constant.APP_DB_DIRNAME;
            LogUtil.log("cacheDirPath="+cacheDirPath);
            //设置数据库缓存路径
//            setting.setDatabasePath(cacheDirPath);
            //设置  Application Caches 缓存目录
            setting.setAppCachePath(cacheDirPath);
            //开启 Application Caches 功能
            setting.setAppCacheEnabled(true);
        }
        initView();
    }


    public abstract int setContentViewID();

    /**
     * 检测URL
     *
     * @param webView
     * @param url
     */
    public void checkWebViewUrl(final WebView webView, final String url) {
        if (url == null || url.equals("")) {
            return;
        }
        new AsyncTask<String, Void, Integer>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog_grayBG.show("正在加载...");
            }

            @Override
            protected Integer doInBackground(String... params) {
                int responseCode = -1;
                try {
                    URL url = new URL(params[0]);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    responseCode = connection.getResponseCode();
                    LogUtil.log("--->"+responseCode);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return responseCode;
            }

            @Override
            protected void onPostExecute(Integer result) {
                if (result != 200) {
                    webView.setVisibility(View.GONE);
                    showErrorDialog(webView, url);
                } else {
                    webView.loadUrl(url);
                }
            }
        }.execute(url);
    }


    public  void load(final WebView webView, final String url){
        webView.loadUrl(url);
    }

    public void showErrorDialog(final WebView webView, final String failingUrl) {
        dialog_grayBG.dissMiss();
        showToast("加载失败");
//        DialogUtils.getNormalDialog(BaseH5Activity.this, "加载失败", "加载失败了~", "结束", "刷新", new DialogUtils.OnDialogClickListener() {
//            @Override
//            public void onDialogClick(boolean isLeft) {
//                if (isLeft) {
//                    BaseH5Activity.this.finish();
//                } else {
//                    checkWebViewUrl(webView, failingUrl);
//                }
//            }
//        });
    }

    /**
     * Native调用JS的方法 没有参数传null
     *
     * @param method
     * @param params
     */
    public void loadJavaScript(String method, String params) {
        if (mBaseWebView != null) {
            StringBuilder builder = new StringBuilder("javascript:");
            builder.append(method);
            if (params != null) {
                builder.append("('" + params + "')");
            }
            mBaseWebView.loadUrl(builder.toString());
        }
    }

    /**
     * 网页加载开始
     */
    public void loadStart() {

    }

    /**
     * 网页加载完成
     */
    public void loadFinish() {

    }

    public class MyWebViewClient extends WebViewClient {
        boolean isFail = false;

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // 使用自己的WebView组件来响应Url加载事件，而不是使用默认浏览器器加载页面
            mBaseWebView.loadUrl(url);
            // 记得消耗掉这个事件。给不知道的朋友再解释一下，Android中返回True的意思就是到此为止,
            // 事件就会不会冒泡传递了，我们称之为消耗掉
            return true;
        }

        @Override
        public void onPageStarted(final WebView view, String url, Bitmap favicon) {
            loadStart();
            isFail = false;
            dialog_grayBG.show("正在加载...");
            super.onPageStarted(view, url, favicon);
        }


        @Override
        public void onPageFinished(final WebView view, String url) {
            dialog_grayBG.dissMiss();
            loadFinish();
            if (isFail == true && view.getVisibility() == View.GONE) {
                view.setVisibility(View.GONE);
            } else if (view.getVisibility() == View.GONE) {
                view.setVisibility(View.VISIBLE);
            }
            super.onPageFinished(view, url);
        }

//        @Override
//        public void onReceivedError(final WebView view, int errorCode, String description, final String failingUrl) {
//            isFail = true;
//            dialog_grayBG.dissMiss();
//            showErrorDialog(view, failingUrl);
//        }
    }

}
