package com.lizhi.demo.observ;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.lizhi.demo.utils.LogUtil;

import java.util.Observable;
import java.util.Observer;

/**
 * 观察者  如果被观察者 改变则会 通知观察者更新
 * Created by 39157 on 2016/10/24.
 */

public class ObserverTextView extends TextView implements Observer {
    public ObserverTextView(Context context) {
        super(context);
    }


    public ObserverTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void update(Observable observable, Object data) {
        LogUtil.log("------------update----->");
        User user = (User) observable;
        setText(user.name);
    }
}
