package com.lizhi.demo.observ;

import android.view.View;

import com.lizhi.demo.BaseActivity;
import com.lizhi.demo.R;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by 39157 on 2016/10/24.
 */

public class ObservActivity extends BaseActivity implements View.OnClickListener, Observer {

    ObserverTextView observerTextView;
    User user;
    int i = 0;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_observ);
        observerTextView = (ObserverTextView) findViewById(R.id.observerTextView);
        findViewById(R.id.btn_change).setOnClickListener(this);
        user = new User("张三");
        user.addObserver(observerTextView);
        user.addObserver(this);

    }

    @Override
    public void onClick(View v) {
        i++;
        showToast("点击了");
        user.name = "李四被点击了" + i + "次";
        user.notifyObservers();
    }

    @Override
    public void update(Observable observable, Object data) {
        showToast("update");
    }

    @Override
    protected void onStop() {
        super.onStop();
        user.deleteObserver(this);
        user.deleteObserver(observerTextView);
    }
}
