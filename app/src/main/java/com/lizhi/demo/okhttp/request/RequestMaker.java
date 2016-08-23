package com.lizhi.demo.okhttp.request;

import com.lizhi.demo.okhttp.parser.BaseParser;
import com.lizhi.demo.okhttp.response.LoginResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Administrator on 2016/3/24.
 */
public class RequestMaker {


    private volatile static RequestMaker manager;

    public static RequestMaker getInstance() {
        if (manager == null) {
            synchronized (RequestMaker.class) {
                if (manager == null) {
                    manager = new RequestMaker();
                }

            }
        }
        return manager;
    }


    public BaseRequest getDownImgDataRequest(String url) {
        BaseRequest baseRequest = null;
        baseRequest = new BaseRequest(null, UrlEnum.DOWN_IMG_URL, null);
        return baseRequest;
    }


    /**
     * 登录
     *
     * @param userName
     * @param psw
     * @return
     */
    @SuppressWarnings({"unchecked", "varargs"})
    public BaseRequest getLoginRequest(String userName, String psw) {
        BaseRequest baseRequest = null;
        Map<String, Object> params = new HashMap<>(2);
        params.put("UserName", userName);
        params.put("Password", psw);// 设置请求的参数名和参数
        baseRequest = new BaseRequest(params, UrlEnum.OPT_LOGIN, new BaseParser(LoginResponse.class));
        return baseRequest;
    }

}
