package com.lizhi.demo.observ;

import java.util.Observable;

/**
 * 被观察者 反生改变 调用notifyObservers 通知观察者改变状态
 * Created by 39157 on 2016/10/24.
 */

public class User extends Observable {
    public String name;


    public User(String name) {
        this.name = name;
    }

    @Override
    protected void setChanged() {
        super.setChanged();
    }

    @Override
    public void notifyObservers() {
        super.setChanged();
        super.notifyObservers();
    }
}
