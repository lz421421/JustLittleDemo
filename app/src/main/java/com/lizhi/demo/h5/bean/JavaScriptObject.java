package com.lizhi.demo.h5.bean;

import android.app.Activity;
import android.content.Context;
import android.webkit.JavascriptInterface;



/**
 * 、
 * js调用native的方法使用的类
 * 调用javascript方法的类
 * 在js里面定义的方法都需要在这个类里面进行定义
 */
public class JavaScriptObject {
    Context mContext;

    public JavaScriptObject(Context mContext) {
        this.mContext = mContext;
    }

    @JavascriptInterface
    public void showInfoFromJs(String msg) {
      /*  DialogUtils.getNormalDialog((Activity) mContext, "提示", msg, null, "确定", new DialogUtils.OnDialogClickListener() {
            @Override
            public void onDialogClick(boolean isLeft) {
                if (!isLeft) {
                    ((Activity) mContext).finish();
                }
            }
        });*/
    }


}