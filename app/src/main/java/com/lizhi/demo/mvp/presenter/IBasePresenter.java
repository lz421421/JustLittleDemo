package com.lizhi.demo.mvp.presenter;

import com.lizhi.demo.mvp.view.IBaseView;

/**
 * Created by 39157 on 2017/5/4.
 */

public abstract class IBasePresenter<M,T extends IBaseView> {
    public T t;

    public  void attach(T t){
        this.t = t;
    }

    public void onDestroy() {
        t = null;
    }


}
