package com.example;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public class TestClass {

    public static void main(String[] data) {
        Flowable.just("hello rxJava").subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                System.out.println("-----main----->" + s);
            }
        });
    }
}
