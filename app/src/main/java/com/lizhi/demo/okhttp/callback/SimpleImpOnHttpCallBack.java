package com.lizhi.demo.okhttp.callback;

import android.app.Dialog;
import android.widget.Toast;

import com.lizhi.demo.MyApplication;
import com.lizhi.demo.okhttp.Constant;
import com.lizhi.demo.okhttp.OkHttpManager;

/**
 * Created by Administrator on 2016/3/29.
 */
public abstract class SimpleImpOnHttpCallBack<T> implements OkHttpManager.OnHttpCallBack<T> {
    Dialog dialog;

    public SimpleImpOnHttpCallBack(Dialog dialog) {
        this.dialog = dialog;
    }

//    @Override
//    public void onSuccess(T t, String jsonStr, int code) {
//
//    }

    //    public abstract  void onResult(T t,String msg,int code,boolean isSuccess);
    @Override
    public void onFail(String errMsg, int code) {
        if (dialog != null) {
            dialog.dismiss();
        }
        if (code == Constant.ATTESTATION_FAILURE) {
            //在别处登录
            showToast("在别处登录");
        }
        showToast(errMsg);
    }

    public void showToast(String msg) {
        Toast.makeText(MyApplication.getInstance(), msg, Toast.LENGTH_SHORT).show();
    }


}
