package com.lizhi.demo.okhttp.request;

import com.lizhi.demo.okhttp.activity.OkHttpActivity;

/**
 * Created by Administrator on 2016/3/24.
 */
public enum UrlEnum {

    DOWN_IMG_URL(OkHttpActivity.imgUrl, RequestMethod.GET),
    OPT_LOGIN("Login/GhsLogin"),
    REGISTER_URL(""),;


    public String url;
    public RequestMethod requestMethod = RequestMethod.POST;


    UrlEnum(String url) {
        this.url = url;
    }

    UrlEnum(String url, RequestMethod requestMethod) {
        this.url = url;
        this.requestMethod = requestMethod;
    }

    public enum RequestMethod {
        POST("POST"), GET("GET");
        private String requestMethodName;

        RequestMethod(String requestMethodName) {
            this.requestMethodName = requestMethodName;
        }

        public String getRequestMethodName() {
            return requestMethodName;
        }
    }
}
