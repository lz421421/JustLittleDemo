package com.lizhi.demo.okhttp.response;

import com.lizhi.demo.okhttp.BaseResponse;
import com.lizhi.demo.okhttp.bean.UserInfo;

/**
 * Created by Administrator on 2016/3/25.
 */
public class LoginResponse extends BaseResponse {
    public UserInfo data;

    @Override
    public String toString() {
        return "LoginResponse{" +
                "data=" + data +
                '}';
    }
}
