package com.lizhi.demo.mvp.modle;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.lizhi.demo.mvp.bean.UserBean;
import com.lizhi.demo.utils.PermissionUtils;

/**
 * Created by 39157 on 2017/5/4.
 */

public class LoginModel implements IBaseModel<UserBean> {

    public UserBean login(String userName, String psw) {
        UserBean userBean = null;
        try {
            Thread.sleep(2000);
            userBean = new UserBean();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return userBean;
    }
}
