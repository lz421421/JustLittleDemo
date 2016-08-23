package com.lizhi.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lizhi.demo.okhttp.OkHttpManager;
import com.lizhi.demo.okhttp.request.BaseRequest;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/4/20.
 */
public abstract class BaseFragment extends Fragment {
   public BaseActivity mActivity;

    Call call;


    public abstract View setContentView(LayoutInflater inflater);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivity = (BaseActivity) getActivity();
        return setContentView(inflater);
    }

    public void getNetWorkHttp(BaseRequest baseRequest, OkHttpManager.OnHttpCallBack callBack) {
        call = OkHttpManager.getManager().httpData(baseRequest, callBack);
    }

    public void getNetWorkDownLoad(BaseRequest baseRequest, OkHttpManager.OnHttpCallBack callBack) {
        call = OkHttpManager.getManager().httpDownload(baseRequest, callBack);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (call != null && !call.isCanceled() && call.isExecuted()) {
            call.cancel();
        }
    }

    public void showToast(String msg) {
        Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
    }
}
