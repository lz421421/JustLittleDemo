package com.lizhi.demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.lizhi.demo.okhttp.OkHttpManager;
import com.lizhi.demo.okhttp.request.BaseRequest;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/3/25.
 */
public abstract class BaseActivity extends AppCompatActivity {
    Call call;


    public abstract void setContentView();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
    }

    public void getNetWorkHttp(BaseRequest baseRequest, OkHttpManager.OnHttpCallBack callBack) {
        call = OkHttpManager.getManager().httpData(baseRequest, callBack);
    }

    public void getNetWorkDownLoad(BaseRequest baseRequest, OkHttpManager.OnHttpCallBack callBack) {
        call = OkHttpManager.getManager().httpDownload(baseRequest, callBack);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (call != null && !call.isCanceled() && call.isExecuted()) {
            call.cancel();
        }
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }
}
