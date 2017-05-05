package com.lizhi.demo.mvp.presenter;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.lizhi.demo.mvp.bean.UserBean;
import com.lizhi.demo.mvp.modle.LoginModel;
import com.lizhi.demo.mvp.view.LoginView;

/**
 * Created by 39157 on 2017/5/4.
 */

public class LoginPresenter extends IBasePresenter<UserBean,LoginView> {
    LoginModel loginModel;
    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            t.dismissProgress();
            UserBean userBean = (UserBean) msg.obj;
            if (userBean != null) {
                t.success(userBean);
            } else {
                t.fail("登陆失败");
            }
        }
    };

    public LoginPresenter(LoginView loginView) {
        attach(loginView);
        loginModel = new LoginModel();
    }


    public void login(final String name, final String psw) {
        t.showProgress();
        new Thread(new Runnable() {
            @Override
            public void run() {
                UserBean userBean = loginModel.login(name, psw);
                Message msg = handler.obtainMessage();
                msg.obj = userBean;
                handler.sendMessage(msg);
            }
        }).start();
    }


}
