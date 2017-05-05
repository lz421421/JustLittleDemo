package com.lizhi.demo.mvp.view;

import com.lizhi.demo.mvp.bean.UserBean;

/**
 * Created by 39157 on 2017/5/4.
 */

public interface  LoginView extends IBaseView<UserBean> {

    public  void showProgress();
    public   void dismissProgress();
}
