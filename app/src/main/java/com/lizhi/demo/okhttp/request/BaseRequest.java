package com.lizhi.demo.okhttp.request;

import com.lizhi.demo.okhttp.parser.BaseParser;

import java.util.Map;
import java.util.Objects;

/**
 * Created by Administrator on 2016/3/24.
 */
public class BaseRequest {

    public Map<String, Object> params;
    public UrlEnum urlEnum;
    public BaseParser baseParser;

    public BaseRequest(Map<String, Object> params, UrlEnum urlEnum, BaseParser baseParser) {
        this.params = params;
        this.urlEnum = urlEnum;
        this.baseParser = baseParser;
    }


    public BaseRequest(BaseParser baseParser, UrlEnum urlEnum) {
        this.baseParser = baseParser;
        this.urlEnum = urlEnum;
    }
}
