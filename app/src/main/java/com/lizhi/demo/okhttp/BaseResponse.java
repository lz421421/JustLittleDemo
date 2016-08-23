package com.lizhi.demo.okhttp;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/3/25.
 */
public class BaseResponse implements Serializable{

    public  int businessCode;
    public  String message;



    public  int code;
    public  String msg;

    @Override
    public String toString() {
        return "BaseResponse{" +
                "businessCode=" + businessCode +
                ", message='" + message + '\'' +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
