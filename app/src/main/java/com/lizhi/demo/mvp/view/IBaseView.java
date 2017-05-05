package com.lizhi.demo.mvp.view;

import com.lizhi.demo.mvp.bean.UserBean;

/**
 * Created by 39157 on 2017/5/4.
 */

public interface  IBaseView<T> {


    public   void success(T t);
    public   void fail(String errorMsg);


}
