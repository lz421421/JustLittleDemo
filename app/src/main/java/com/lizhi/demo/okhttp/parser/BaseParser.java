package com.lizhi.demo.okhttp.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lizhi.demo.okhttp.OkHttpManager;

import java.lang.reflect.Type;

/**
 * Created by Administrator on 2016/3/24.
 */
public class BaseParser<T> {
    Class<T> tClass;

    public BaseParser(Class<T> tClass) {
        this.tClass = tClass;
    }

    public T parser(String json) {
        T t = null;
        t = JSONObject.parseObject(json, tClass);
        return t;
    }

}
