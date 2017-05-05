package com.lizhi.demo.mvp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.lizhi.demo.R;
import com.lizhi.demo.mvp.bean.UserBean;
import com.lizhi.demo.mvp.presenter.IBasePresenter;
import com.lizhi.demo.mvp.presenter.LoginPresenter;
import com.lizhi.demo.mvp.view.LoginView;

public class LoginActivity extends BaseMvpActivity<LoginPresenter> implements LoginView{
    ProgressDialog progressDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public LoginPresenter initPresenter() {
        progressDialog = new ProgressDialog(this);
        return new LoginPresenter(this);
    }

    public void login(View view){
        presenter.login("张三","23456");
    }


    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void dismissProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void success(UserBean userBean) {
        Toast.makeText(this,"登录成功了",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void fail(String errorMsg) {
        Toast.makeText(this,"登录失败了",Toast.LENGTH_SHORT).show();
    }

}
