package com.example;


import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class TestClass {

    public static void main(String[] data) {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                System.out.println("---------------->" + Thread.currentThread().getName());
                subscriber.onNext("1");
                subscriber.onNext("2");
                subscriber.onNext("3");
                subscriber.onError(new Throwable("--1212121---"));
                subscriber.onCompleted();
                subscriber.onNext("4");
                subscriber.onNext("5");
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("-------onCompleted--------->");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("-------onError--------->" + e);
            }

            @Override
            public void onNext(String s) {
                System.out.println("-------onNext--------->" + s);
            }
        });

    }
}
