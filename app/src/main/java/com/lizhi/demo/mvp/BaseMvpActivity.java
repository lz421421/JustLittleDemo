package com.lizhi.demo.mvp;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.lizhi.demo.mvp.presenter.IBasePresenter;
import com.lizhi.demo.mvp.view.IBaseView;

/**
 * Created by 39157 on 2017/5/4.
 */

public abstract class BaseMvpActivity<T extends IBasePresenter> extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        presenter = initPresenter();
    }

    public abstract int getLayoutId();

    T presenter;

    public abstract T initPresenter();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onDestroy();
        }
    }
}
