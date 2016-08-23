package com.lizhi.demo.h5.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/4.
 */
public class WebBean implements Serializable {
    final static long serialVersionUID = 1L;

    public String title;
    public String url;
    public String msg;


    public WebBean() {
        this(null, null);
    }

    public WebBean(String title, String url) {
        this.title = title;
        this.url = url;
    }

    @Override
    public String toString() {
        return "WebBean{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
